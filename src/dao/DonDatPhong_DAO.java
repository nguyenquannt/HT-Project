package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.DonDatPhong;
import entity.DonDatPhong.TrangThai;
import entity.KhachHang;
import entity.NhanVien;
import entity.Phong;
import utils.Utils;

public class DonDatPhong_DAO extends DAO {
	private ChiTietDatPhong_DAO chiTietDatPhong_DAO;
	private Phong_DAO phong_DAO;

	public DonDatPhong_DAO() {
		phong_DAO = new Phong_DAO();
		chiTietDatPhong_DAO = new ChiTietDatPhong_DAO();
	}

	/**
	 * Cập nhật trong đơn đặt phòng chờ
	 * 
	 * @param maDatPhong
	 * @param gioNhanPhong
	 * @param phongMoi
	 * @param phongBanDau
	 * @return
	 */
	public boolean capNhatPhongTrongPhieuDatPhongTruoc(String maDatPhong, LocalTime gioNhanPhong, List<Phong> phongMoi,
			List<Phong> phongBanDau) {

		try {
			Connection connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement;
			String sql;
			boolean res;

//			--Xoá chi tiết đặt phòng theo mã đơn đặt phòng
			sql = "DELETE ChiTietDatPhong WHERE donDatPhong = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, maDatPhong);
			res = preparedStatement.executeUpdate() > 0;

			if (!res)
				return rollback();

//			Cập nhật danh sách phòng 
			Time gioNhanPhongTime = Time.valueOf(gioNhanPhong);
			for (Phong phong : phongMoi) {
				res = chiTietDatPhong_DAO.themChiTietDatPhong(maDatPhong, phong, gioNhanPhongTime);
				if (!res)
					return rollback();
			}
//			[Phong] - Cập nhật trạng thái phòng

			List<Phong> pBD = timPhongDangThue(phongBanDau);
			List<Phong> pM = timPhongDangThue(phongMoi);
//			Nếu phòng mới và phòng ban đầu không có phòng thuê
			if (pBD.size() <= 0 && pM.size() <= 0) {
				for (Phong phong : phongBanDau) {
					res = phong_DAO.capNhatTrangThaiPhong(phong, "Trống");
					if (!res)
						return rollback();
				}
//				+ Phòng mới: Trống -> Đã đặt
				for (Phong phong : phongMoi) {
					res = phong_DAO.capNhatTrangThaiPhong(phong, "Đã đặt");
					if (!res)
						return rollback();
				}
			}
//			Nếu có phòng mới đang thuê
			else if (pBD.size() <= 0 && pM.size() > 0) {
				for (Phong phong : phongBanDau) {
					res = phong_DAO.capNhatTrangThaiPhong(phong, "Trống");
					if (!res)
						return rollback();
				}
//				+ Phòng mới: Trống -> Đã đặt
				for (Phong phong : phongMoi) {
					if (pM.contains(phong)) {
						res = phong_DAO.capNhatTrangThaiPhong(phong, "Phòng tạm");
						continue;
					}
					res = phong_DAO.capNhatTrangThaiPhong(phong, "Đã đặt");
					if (!res)
						return rollback();
				}
			}
//			Nếu phòng ban đầu đang thuê
			else if (pBD.size() > 0 && pM.size() <= 0) {
//				+Phòng ban đầu: -> Đang thuê
				for (Phong phong : phongBanDau) {
					if (pBD.contains(phong)) {
						res = phong_DAO.capNhatTrangThaiPhong(phong, "Đang thuê");
						continue;
					}
					res = phong_DAO.capNhatTrangThaiPhong(phong, "Trống");
					if (!res)
						return rollback();
				}
//				+ Phòng mới: -> Đã đặt
				for (Phong phong : phongMoi) {
					res = phong_DAO.capNhatTrangThaiPhong(phong, "Đã đặt");
					if (!res)
						return rollback();
				}
			}
//			Nếu phòng mới và phòng ban đầu đang thuê
			else {
//				+Phòng ban đầu: -> Đang thuê
				for (Phong phong : phongBanDau) {
					if (pBD.contains(phong)) {
						res = phong_DAO.capNhatTrangThaiPhong(phong, "Đang thuê");
						continue;
					}
					res = phong_DAO.capNhatTrangThaiPhong(phong, "Trống");
					if (!res)
						return rollback();
				}
//				+ Phòng mới: -> Phòng tạm
				for (Phong phong : phongMoi) {
					if (pM.contains(phong)) {
						res = phong_DAO.capNhatTrangThaiPhong(phong, "Phòng tạm");
						continue;
					}
					res = phong_DAO.capNhatTrangThaiPhong(phong, "Đã đặt");
					if (!res)
						return rollback();
				}
			}

			return commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Chuyển phòng từ phòng cũ sang phòng mới
	 * 
	 * @param maPhongCu  mã phòng cũ
	 * @param maPhongMoi mã phòng mới
	 * @return true nếu chuyển thành công
	 */
	public boolean chuyenPhong(String maPhongCu, String maPhongMoi) {
		Phong phongCu = phong_DAO.getPhong(maPhongCu);
		Phong phongMoi = phong_DAO.getPhong(maPhongMoi);
		Time time = Time.valueOf(LocalTime.now());
		PreparedStatement preparedStatement;
		boolean res;

		try {
			Connection connection = ConnectDB.getConnection();

//			[DatPhong - ChiTietDatPhong] - Get mã đơn đặt phòng của phòng cần chuyển
			preparedStatement = connection.prepareStatement("SELECT [donDatPhong] FROM [dbo].[DonDatPhong] DP "
					+ "JOIN [dbo].[ChiTietDatPhong] CTDP ON DP.[maDonDatPhong] = CTDP.[donDatPhong] "
					+ "WHERE DP.[trangThai] = N'Đang thuê' AND [phong] = ? AND [gioRa] IS NULL");
			preparedStatement.setString(1, maPhongCu);

			ResultSet resultSet = preparedStatement.executeQuery();

			res = resultSet.next();
			if (!res)
				return false;
			connection.setAutoCommit(false);

			String donDatPhong = resultSet.getString(1);
//			Phòng cũ - Trạng thái mới
//						+ Trống --> X
//						+ Đã đặt --> X
//						+ Đang thuê --> Trống
//						+ Phòng tạm --> Đã đặt
			entity.Phong.TrangThai trangThaiCu = entity.Phong.TrangThai.DaDat;
			if (phongCu.getTrangThai().equals(entity.Phong.TrangThai.DangThue))
				trangThaiCu = entity.Phong.TrangThai.Trong;
			String trangThaiMoiPhongCu = Phong.convertTrangThaiToString(trangThaiCu);

//			[Phong] - Cập nhật trạng thái phòng của phòng cũ
			res = phong_DAO.capNhatTrangThaiPhong(phongCu, trangThaiMoiPhongCu);
			if (!res)
				return rollback();

//			Phòng mới - Trạng thái mới
//						+ Trống --> Đang thuê
//						+ Đã đặt --> Phòng tạm
//						+ Đang thuê --> X
//						+ Phòng tạm --> X
			entity.Phong.TrangThai trangThaiMoi = entity.Phong.TrangThai.PhongTam;
			if (phongMoi.getTrangThai().equals(entity.Phong.TrangThai.Trong))
				trangThaiMoi = entity.Phong.TrangThai.DangThue;
			String trangThaiMoiPhongMoi = Phong.convertTrangThaiToString(trangThaiMoi);

//			[Phong] - Cập nhật trạng thái phòng của phòng mới
			res = phong_DAO.capNhatTrangThaiPhong(phongMoi, trangThaiMoiPhongMoi);
			if (!res)
				return rollback();

//			[ChiTietDatPhong] - Cập nhật giờ ra của phòng cũ
			res = chiTietDatPhong_DAO.setGioRa(donDatPhong, maPhongCu, time);
			if (!res)
				return rollback();

//			[ChiTietDatPhong] - Thêm chi tiết đặt phòng phòng mới
			res = chiTietDatPhong_DAO.themChiTietDatPhong(donDatPhong, phongMoi, time);
			if (!res)
				return rollback();

			return commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Get tất cả đơn đặt phòng đang thuê
	 * 
	 * @return
	 */
	public List<DonDatPhong> getAllDonDatPhongDangThue() {
		List<DonDatPhong> list = new ArrayList<>();

		String sql = "SELECT * FROM [dbo].[DonDatPhong] WHERE [trangThai] = N'Đang thuê'";
		try {
			Statement statement = ConnectDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			DonDatPhong donDatPhong;
			while (resultSet.next()) {
				donDatPhong = getDatPhong(resultSet);
				list.add(donDatPhong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get tất cả đơn đặt phòng đã trả
	 * 
	 * @return
	 */
	public List<DonDatPhong> getAllDonDatPhongDaTra() {
		List<DonDatPhong> list = new ArrayList<>();

		String sql = "SELECT * FROM [dbo].[DonDatPhong] WHERE [trangThai] = N'Đã trả'";
		try {
			Statement statement = ConnectDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			DonDatPhong donDatPhong;
			while (resultSet.next()) {
				donDatPhong = getDatPhong(resultSet);
				list.add(donDatPhong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get đặt phòng từ resultSet
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private DonDatPhong getDatPhong(ResultSet resultSet) throws SQLException {
		String maDonDatPhong = resultSet.getString("maDonDatPhong");
		KhachHang khachHang = new KhachHang(resultSet.getString("khachHang"));
		NhanVien nhanVien = new NhanVien(resultSet.getString("nhanVien"));
		LocalDate ngayDatPhong = resultSet.getDate("ngayDatPhong").toLocalDate();
		LocalTime gioDatPhong = resultSet.getTime("gioDatPhong").toLocalTime();
		LocalDate ngayNhanPhong = resultSet.getDate("ngayNhanPhong").toLocalDate();
		LocalTime gioNhanPhong = resultSet.getTime("gioNhanPhong").toLocalTime();
		TrangThai trangThai = DonDatPhong.convertStringToTrangThai(resultSet.getString("trangThai"));
		return new DonDatPhong(maDonDatPhong, khachHang, nhanVien, ngayDatPhong, gioDatPhong, ngayNhanPhong,
				gioNhanPhong, trangThai);
	}

	/**
	 * Get đơn đặt phòng theo mã đơn đặt phòng
	 * 
	 * @param maDonDatPhong
	 * @return
	 */
	public DonDatPhong getDatPhong(String maDonDatPhong) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM DonDatPhong WHERE maDonDatPhong = ?");
			preparedStatement.setString(1, maDonDatPhong);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return getDatPhong(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Get mã đơn đặt phòng đang thuê theo mã phòng
	 * 
	 * @param maPhong
	 * @return
	 */
	public DonDatPhong getDonDatPhong(String maPhong) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT [donDatPhong] FROM [dbo].[ChiTietDatPhong] CTDP "
							+ "JOIN [dbo].[DonDatPhong] DDP ON CTDP.donDatPhong = DDP.maDonDatPhong "
							+ "WHERE [phong] = ? AND [gioRa] IS NULL AND [trangThai] = N'Đang thuê'");
			preparedStatement.setString(1, maPhong);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return new DonDatPhong(resultSet.getString("donDatPhong"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get đơn đặt phòng đang thuê theo số điện thoại
	 * 
	 * @param soDienThoai
	 * @return
	 */
	public DonDatPhong getDonDatPhongNgayTheoSoDienThoai(String soDienThoai) {
		String sql = "SELECT [maDonDatPhong], [khachHang], [nhanVien], [ngayDatPhong],"
				+ "	[gioDatPhong], [ngayNhanPhong], [gioNhanPhong], [trangThai] FROM [dbo].[DonDatPhong] DP"
				+ "	JOIN [dbo].[KhachHang] KH ON DP.[khachHang] = KH.[maKhachHang]"
				+ " WHERE [trangThai] = N'Đang thuê' AND soDienThoai = ?";
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, soDienThoai);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return getDatPhong(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get giờ vào phòng của phòng đang thuê hoặc phòng đặt trước gần nhất
	 * 
	 * @param phong phòng cần lấy giờ vào
	 * @return giờ vào phòng
	 */
	public LocalTime getGioVao(Phong phong) {
		try {
			entity.Phong.TrangThai trangThaiPhong = phong_DAO.getTrangThai(phong.getMaPhong());

			if (trangThaiPhong.equals(entity.Phong.TrangThai.Trong))
				return null;

			PreparedStatement preparedStatement;
			String trangThai = "Đang thuê";

			if (trangThaiPhong.equals(entity.Phong.TrangThai.DaDat))
				trangThai = "Đang chờ";

			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT [gioVao] FROM [dbo].[DonDatPhong] DP"
							+ " JOIN [dbo].[ChiTietDatPhong] CTDP ON DP.maDonDatPhong = CTDP.donDatPhong"
							+ " WHERE [trangThai] = ? AND [phong] = ? AND [gioRa] IS NULL"
							+ " ORDER BY [ngayNhanPhong], [gioNhanPhong]");
			preparedStatement.setString(1, trangThai);
			preparedStatement.setString(2, phong.getMaPhong());

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return resultSet.getTime(1).toLocalTime();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Get mã đơn đặt phòng của đơn đang thuê theo số điện thoại khách
	 * 
	 * @param soDienThoai
	 * @return
	 */
	public String getMaDatPhongDangThue(String soDienThoai) {
		DonDatPhong donDatPhong = getDonDatPhongNgayTheoSoDienThoai(soDienThoai);

		if (donDatPhong == null)
			return null;
		return donDatPhong.getMaDonDatPhong();
	}

	/**
	 * Get tất cả mã đơn đặt phòng có thể gộp, Đơn đặt phòng > 1 phòng thì có thể
	 * gộp
	 * 
	 * @return danh sách mã đơn đặt phòng có thể gộp
	 */
	public List<String> getMaDatPhongGop() {
		List<String> list = new ArrayList<>();
		String sql = "SELECT [donDatPhong] FROM [dbo].[ChiTietDatPhong] CTDP "
				+ "JOIN [dbo].[DonDatPhong] DP ON CTDP.donDatPhong = DP.maDonDatPhong "
				+ "WHERE [gioRa] IS NULL AND [trangThai] = N'Đang thuê' GROUP BY [donDatPhong] HAVING COUNT(*) > 1";
		try {
			Statement statement = ConnectDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			String maPhong;
			while (resultSet.next()) {
				maPhong = resultSet.getString(1);
				list.add(maPhong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get ngày nhận phòng của phòng đang thuê
	 * 
	 * @param maPhong
	 * @return
	 */
	public LocalDate getNgayNhanPhongCuaPhongDangThue(String maPhong) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT [ngayNhanPhong] FROM [dbo].[DonDatPhong] DP "
							+ "JOIN [dbo].[ChiTietDatPhong] CTDP ON DP.[maDonDatPhong] = CTDP.[donDatPhong] "
							+ "WHERE DP.[trangThai] = N'Đang thuê' AND [phong] = ?");
			preparedStatement.setString(1, maPhong);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getDate(1).toLocalDate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Get những phòng có thể gộp của mã đặt phòng trừ những phòng được chọn gộp
	 * 
	 * @param maDatPhong    mã đặt phòng
	 * @param dsPhongDaChon danh sách phòng được chọn
	 * @return danh sách phòng có thể gộp
	 */
	public List<Phong> getPhongCoTheGop(String maDatPhong, List<Phong> dsPhongDaChon) {
		List<Phong> list = getPhongDangThue(maDatPhong);
		list.addAll(getPhongDatNgay());

		if (dsPhongDaChon != null)
			for (Phong phong : dsPhongDaChon)
				list.remove(phong);
		return list;
	}

	/**
	 * Get tất cả phòng đang thuê theo mã đặt phòng
	 * 
	 * @param maDatPhong mã đặt phòng
	 * @return danh sách các phòng
	 */
	public List<Phong> getPhongDangThue(String maDatPhong) {
		List<Phong> list = new ArrayList<>();

		String sql = "SELECT maPhong FROM [dbo].[Phong] WHERE [maPhong] IN (SELECT [phong] FROM [dbo].[DonDatPhong] DP"
				+ "	JOIN [dbo].[ChiTietDatPhong] CTDP ON DP.[maDonDatPhong] = CTDP.[donDatPhong]"
				+ "	WHERE DP.[trangThai] = N'Đang thuê' AND [gioRa] IS NULL AND [maDonDatPhong] = ?)";
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, maDatPhong);

			ResultSet resultSet = preparedStatement.executeQuery();
			Phong phong;
			while (resultSet.next()) {
				phong = phong_DAO.getPhong(resultSet.getString(1));
				list.add(phong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get danh sách phòng đặt ngay
	 * 
	 * @return danh sách phòng đặt ngay
	 */
	public List<Phong> getPhongDatNgay() {
		return getPhongDatNgay("", "", "Số lượng khách");
	}

	/**
	 * Get danh sách các phòng có thể đặt ngay
	 * 
	 * @param maPhong   mã phòng
	 * @param loaiPhong loại phòng
	 * @param soLuong   số lượng
	 * @return danh sách các phòng
	 */
	public List<Phong> getPhongDatNgay(String maPhong, String loaiPhong, String soLuong) {
		List<Phong> list = new ArrayList<>();
		boolean isInteger = Utils.isInteger(soLuong);

		try {
			String sql = "SELECT [maPhong] FROM [dbo].[Phong] P"
					+ " WHERE maPhong NOT IN ("
					+ "	SELECT CTDP.[phong] FROM [dbo].[DonDatPhong] DP"
					+ "	JOIN [dbo].[ChiTietDatPhong] CTDP ON DP.[maDonDatPhong] = CTDP.[donDatPhong]"
					+ "	WHERE (DP.[trangThai] = N'Đang thuê' AND CTDP.[gioRa] IS NULL)"
					+ "	OR (DP.[trangThai] = N'Đang chờ' AND DP.[ngayNhanPhong] = CONVERT(DATE, GETDATE())"
					+ "	AND [dbo].[fnSubTime](DP.[gioNhanPhong], CONVERT(TIME(0), GETDATE())) < '6:00:00')"
					+ "	) AND [maPhong] LIKE ? AND tenLoai LIKE ? AND [trangThaiXoa] = 0";

			if (isInteger)
				sql += " AND soLuongKhach = ?";

			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, "%" + maPhong + "%");
			preparedStatement.setString(2, "%" + loaiPhong + "%");

			if (isInteger)
				preparedStatement.setInt(3, Integer.parseInt(soLuong));

			ResultSet resultSet = preparedStatement.executeQuery();
			Phong phong;
			while (resultSet.next()) {
				phong = phong_DAO.getPhong(resultSet.getString(1));
				list.add(phong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get tất cả các phòng có thể đặt trước theo ngày và giờ được chọn
	 * 
	 * @param ngayNhanPhong ngày nhận phòng
	 * @param gioNhanPhong  giờ nhận phòng
	 * @return tất cả các phòng có thể đặt trước
	 */
	public List<Phong> getPhongDatTruoc(LocalDate ngayNhanPhong, LocalTime gioNhanPhong) {
		return getPhongDatTruoc(ngayNhanPhong, gioNhanPhong, "", "", "Số lượng");
	}

	/**
	 * Get danh sách phòng có thể đặt trước
	 * 
	 * @param ngayNhanPhong ngày nhận phòng
	 * @param gioNhanPhong  giờ nhận phòng
	 * @param maPhong       mã phòng
	 * @param loaiPhong     loại phòng
	 * @param soLuong       số lượng khách
	 * @return danh sách phòng có thể đặt
	 */
	public List<Phong> getPhongDatTruoc(LocalDate ngayNhanPhong, LocalTime gioNhanPhong, String maPhong,
			String loaiPhong, String soLuong) {
		List<Phong> list = new ArrayList<>();
		boolean isInteger = Utils.isInteger(soLuong);

		try {
			boolean isDateNow = LocalDate.now().isEqual(ngayNhanPhong);

			String sql = String.format(
					"SELECT maPhong FROM [dbo].[Phong] P JOIN [dbo].[LoaiPhong] LP ON P.loaiPhong = LP.maLoai "
							+ "WHERE[trangThaiXoa] = 0 AND [maPhong] NOT IN (SELECT [maPhong] FROM [dbo].[Phong] P"
							+ "	JOIN [dbo].[ChiTietDatPhong] CTDP ON P.maPhong = CTDP.phong"
							+ "	JOIN [dbo].[DonDatPhong] DP ON DP.maDonDatPhong = CTDP.donDatPhong"
							+ "	WHERE (P.trangThai IN (N'Đã đặt'%s) AND [ngayNhanPhong] = ?"
							+ "	AND [dbo].[fnSubTime](?, [gioNhanPhong]) <= CONVERT(TIME(0), '6:00:00')"
							+ "	AND DP.trangThai = N'Đang chờ')%s) AND [maPhong] LIKE ? AND tenLoai LIKE ?",
					isDateNow ? "" : ", N'Phòng tạm'", isDateNow ? " OR DP.trangThai = N'Đang thuê'" : "");

			if (isInteger)
				sql += " AND soLuongKhach = ?";

			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setDate(1, Date.valueOf(ngayNhanPhong));
			preparedStatement.setTime(2, Time.valueOf(gioNhanPhong));
			preparedStatement.setString(3, "%" + maPhong + "%");
			preparedStatement.setString(4, "%" + loaiPhong + "%");

			if (isInteger)
				preparedStatement.setInt(5, Integer.parseInt(soLuong));

			ResultSet resultSet = preparedStatement.executeQuery();
			Phong phong;
			while (resultSet.next()) {
				phong = phong_DAO.getPhong(resultSet.getString(1));
				list.add(phong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Gộp phòng
	 * 
	 * @param maDatPhong    mã đơn đặt phòng
	 * @param dsPhongCanGop danh sách phòng cần gộp
	 * @param phongGop      phòng gộp thành
	 * @return true nếu gộp phòng thành công
	 */
	public boolean gopPhong(String maDatPhong, List<Phong> dsPhongCanGop, Phong phongGop) {
		Time time = Time.valueOf(LocalTime.now());
		String q = "?";
		boolean res;
		int length = dsPhongCanGop.size();
		PreparedStatement preparedStatement;

		for (int i = 1; i < length; ++i)
			q += ", ?";

		try {
			Connection connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);

//			[ChiTietDatPhong] - Set giờ ra cho phòng cần gộp
			preparedStatement = connection
					.prepareStatement(String.format("UPDATE [dbo].[ChiTietDatPhong] SET [gioRa] = ? "
							+ "WHERE [donDatPhong] = ? AND [phong] IN (%s) AND [gioRa] IS NULL", q));
			preparedStatement.setTime(1, time);
			preparedStatement.setString(2, maDatPhong);
			for (int i = 0; i < length; ++i)
				preparedStatement.setString(i + 3, dsPhongCanGop.get(i).getMaPhong());
			res = preparedStatement.executeUpdate() > 0;

			if (!res)
				return rollback();

//			[Phong] - Cập nhật trạng thái phòng của phòng cần gộp
//						+ Đang thuê -> Trống
//						+ Phòng tạm -> Đã đặt
			preparedStatement = connection.prepareStatement(String.format("UPDATE [dbo].[Phong] SET [trangThai] = (CASE"
					+ "	WHEN [trangThai] = N'Đang thuê' THEN N'Trống' ELSE N'Đã đặt' END) WHERE [maPhong] IN (%s)", q));
			for (int i = 0; i < length; ++i)
				preparedStatement.setString(i + 1, dsPhongCanGop.get(i).getMaPhong());
			res = preparedStatement.executeUpdate() > 0;

			if (!res)
				return rollback();

//			[ChiTietDatPhong] - Thêm chi tiết đặt phòng nếu là phòng trống hoặc là phòng chờ
			entity.Phong.TrangThai trangThai = phongGop.getTrangThai();
			if (trangThai.equals(entity.Phong.TrangThai.Trong) || trangThai.equals(entity.Phong.TrangThai.DaDat)) {
				res = chiTietDatPhong_DAO.themChiTietDatPhong(maDatPhong, phongGop, time);
				if (!res)
					return rollback();

//				[Phong] - Cập nhật trạng thái phòng gộp
//							+ Trống -> Đang thuê
//							+ Đã đặt -> Phòng tạm
				preparedStatement = connection
						.prepareStatement("UPDATE [dbo].[Phong] SET trangThai = (CASE WHEN [trangThai] = N'Trống' "
								+ "THEN N'Đang thuê' ELSE N'Phòng tạm' END) WHERE [maPhong] = ?");
				preparedStatement.setString(1, phongGop.getMaPhong());

				res = preparedStatement.executeUpdate() > 0;

				if (!res)
					return rollback();
			}

			return commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Kiểm tra phòng có phiếu đặt trước khác hay không?
	 * 
	 * @param maPhong      ma phòng cần kiểm tra
	 * @param gioNhanPhong giờ nhận phòng cần kiểm tra
	 * @return true nếu có phiếu đặt phòng trước khác
	 */
	public boolean hasDatPhongTruoc(String maPhong) {
		String sql = "SELECT * FROM ChiTietDatPhong CP INNER JOIN DonDatPhong DP  ON CP.donDatPhong = DP.maDonDatPhong\n"
				+ "WHERE CP.phong like ? AND ( DP.trangThai = N'Đang chờ' or DP.trangThai = N'Phòng tạm') \n";

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, maPhong);

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Kiểm tra phòng có phiếu đặt trước hợp lệ hay không? Phiếu đặt trước hợp lệ
	 * nếu không trễ quá 1h kể từ thời điểm nhận phòng
	 * 
	 * @param maPhong ma phòng cần kiểm tra
	 * @return true nếu có phiếu đặt phòng trước hợp lệ
	 */
	private boolean hasDatPhongTruocHopLe(String maPhong) {
		String sql = "SELECT * FROM [dbo].[DonDatPhong] DP JOIN [dbo].[ChiTietDatPhong] CTDP "
				+ "ON DP.maDonDatPhong = CTDP.donDatPhong WHERE DP.[trangThai] = N'Đang chờ' "
				+ "AND [phong] = ? AND ([ngayNhanPhong] > CONVERT(DATE, GETDATE()) "
				+ "OR ([ngayNhanPhong] = CONVERT(DATE, GETDATE()) AND ([gioNhanPhong] >= CONVERT(TIME(0), GETDATE()) "
				+ "OR [dbo].[fnSubTime]([gioNhanPhong], CONVERT(TIME(0), GETDATE())) < CONVERT(TIME(0), '1:00:00'))))";

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, maPhong);

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Hủy các phòng đặt trước có giờ nhận phòng - giờ hiện tại > 1h
	 * 
	 * @return true nếu hủy thành công
	 */
	public boolean huyPhongDatTre() {
		try {
			Connection connection = ConnectDB.getConnection();
			Statement statement = connection.createStatement();

//			Get danh sách phòng có đơn đặt phòng trễ
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT [phong] FROM [dbo].[DonDatPhong] DP "
							+ "JOIN [dbo].[ChiTietDatPhong] CTDP ON DP.[maDonDatPhong] = CTDP.[donDatPhong] "
							+ "WHERE ([trangThai] = N'Đang chờ' AND [ngayNhanPhong] = CONVERT(DATE, GETDATE()) "
							+ "AND [dbo].[fnSubTime]([gioNhanPhong], CONVERT(TIME(0), GETDATE())) >= CONVERT(TIME(0), '1:00:00') "
							+ "AND [gioNhanPhong] < CONVERT(TIME(0), GETDATE())) "
							+ "OR ([trangThai] = N'Đang chờ' AND [ngayNhanPhong] < CONVERT(DATE, GETDATE()))");

			List<String> maPhongList = new ArrayList<>();
			ResultSet resultSet = preparedStatement.executeQuery();
			String maPhong;
			while (resultSet.next()) {
				maPhong = resultSet.getString(1);
				maPhongList.add(maPhong);
			}

			if (maPhongList.size() == 0)
				return false;
			connection.setAutoCommit(false);

//			[Phong] - Cập nhật trạng thái phòng
			boolean res;
			boolean isDatPhongTruoc;
			entity.Phong.TrangThai trangThai;
			for (String string : maPhongList) {
				trangThai = phong_DAO.getTrangThai(string);
				preparedStatement = connection
						.prepareStatement("UPDATE [dbo].[Phong] SET [trangThai] = ? WHERE [maPhong] = ?");
				isDatPhongTruoc = hasDatPhongTruocHopLe(string);
				if (trangThai.equals(entity.Phong.TrangThai.DaDat)) {
					if (isDatPhongTruoc)
						preparedStatement.setString(1, Phong.convertTrangThaiToString(Phong.TrangThai.DaDat));
					else
						preparedStatement.setString(1, Phong.convertTrangThaiToString(Phong.TrangThai.Trong));
				} else {
					if (isDatPhongTruoc)
						preparedStatement.setString(1, Phong.convertTrangThaiToString(Phong.TrangThai.PhongTam));
					else
						preparedStatement.setString(1, Phong.convertTrangThaiToString(Phong.TrangThai.DangThue));
				}
				preparedStatement.setString(2, string);
				res = preparedStatement.executeUpdate() > 0;

				if (!res)
					return rollback();
			}

//			[DonDatPhong] - Cập nhật trạng thái đơn đặt phòng thành đã hủy
			res = statement.executeUpdate("UPDATE [dbo].[DonDatPhong] SET [trangThai] = N'Đã hủy' WHERE ("
					+ "[trangThai] = N'Đang chờ' AND [ngayNhanPhong] = CONVERT(DATE, GETDATE()) "
					+ "AND [dbo].[fnSubTime]([gioNhanPhong], CONVERT(TIME(0), GETDATE())) >= CONVERT(TIME(0), '1:00:00') "
					+ "AND [gioNhanPhong] < CONVERT(TIME(0), GETDATE())) OR ("
					+ "[trangThai] = N'Đang chờ' AND [ngayNhanPhong] < CONVERT(DATE, GETDATE()))") > 0;

			if (!res)
				return rollback();

			return commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean huyPhongTrongPhieuDatPhongTruoc(DonDatPhong donDatPhong, List<Phong> phongs) {
		try {
			Connection connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement;
			boolean res;

//			[DatPhong] - Cập nhật trạng thái đặt phòng thành đã hủy
			preparedStatement = connection.prepareStatement(
					"UPDATE [dbo].[DonDatPhong] SET [trangThai] = N'Đã hủy' WHERE " + "[maDonDatPhong] = ?");
			preparedStatement.setString(1, donDatPhong.getMaDonDatPhong());
			res = preparedStatement.executeUpdate() > 0;

			if (!res)
				return rollback();

//			[Phong] - Cập nhật trạng thái phòng
			boolean isDatPhongTruoc;
			for (Phong phong : phongs) {
				isDatPhongTruoc = hasDatPhongTruoc(phong.getMaPhong());
				preparedStatement = connection.prepareStatement("UPDATE [dbo].[Phong] SET [trangThai] = ("
						+ "	CASE WHEN [trangThai] = N'Đã đặt' THEN ? ELSE ? END) WHERE [maPhong] = ?");
				preparedStatement.setString(1, isDatPhongTruoc ? "Đã đặt" : "Trống");
				preparedStatement.setString(2, isDatPhongTruoc ? "Phòng tạm" : "Đang thuê");
				preparedStatement.setString(3, phong.getMaPhong());
				res = preparedStatement.executeUpdate() > 0;

				if (!res)
					return rollback();
			}

			return commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Kiểm tra có thực hiện chức năng gộp phòng được không?
	 * 
	 * @return true nếu có thể gộp phòng
	 */
	public boolean isGopPhong() {
		try {
			Statement statement = ConnectDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS SOLUONGPHONG FROM [dbo].[DonDatPhong] DP "
					+ "JOIN [dbo].[ChiTietDatPhong] CTDP ON DP.[maDonDatPhong] = CTDP.[donDatPhong] "
					+ "WHERE [gioRa] IS NULL AND [trangThai] = N'Đang thuê' GROUP BY [donDatPhong]");
			int soLuong;
			while (resultSet.next()) {
				soLuong = Integer.parseInt(resultSet.getString(1));

				if (soLuong >= 2)
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean nhanPhongTrongPhieuDatPhongTruoc(DonDatPhong donDatPhong, List<Phong> phongs) {

		try {
			Connection connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement;
			String sql;
			boolean res;
			Time time = Time.valueOf(LocalTime.now());
			Date date = Date.valueOf(LocalDate.now());

//			Cập nhật trạng thái 'Đang thuê' trong đơn đặt phòng
			sql = "UPDATE DONDATPHONG SET trangThai = N'Đang thuê', ngayNhanPhong = ?, gioNhanPhong = ? WHERE maDonDatPhong = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, date);
			preparedStatement.setTime(2, time);
			preparedStatement.setString(3, donDatPhong.getMaDonDatPhong());

			res = preparedStatement.executeUpdate() > 0;

			if (!res)
				return rollback();

//			Cập nhật giờ vào trong chi tiết phiếu đặt phòng
			sql = "UPDATE CHITIETDATPHONG SET gioVao = ? WHERE donDatPhong = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setTime(1, time);
			preparedStatement.setString(2, donDatPhong.getMaDonDatPhong());
			res = preparedStatement.executeUpdate() > 0;

			if (!res)
				return rollback();

//			Cập nhật danh sách phòng
//				+Phòng tạm -> đang thuê
			for (Phong phong : phongs) {
				res = phong_DAO.capNhatTrangThaiPhong(phong, "Đang thuê");
				if (!res)
					return rollback();
			}

			return commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Tạo mã đặt phòng
	 * 
	 * @return mã đặt phòng
	 */
	private String taoMaDatPhong() {
		Statement statement;
		try {
			statement = ConnectDB.getConnection().createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT maDonDatPhong FROM DonDatPhong ORDER BY maDonDatPhong DESC");

			if (!resultSet.next())
				return "MDP0001";
			String prevMaDatPhong = resultSet.getString(1);
			int prevSTT = Integer.parseInt(prevMaDatPhong.substring(3));

			String newMaDatPhong = (prevSTT + 1) + "";
			int length = newMaDatPhong.length();
			while (length++ < 4)
				newMaDatPhong = "0" + newMaDatPhong;

			statement.close();
			return "MDP" + newMaDatPhong;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Thanh toán đơn đặt phòng
	 * 
	 * @param maDatPhong mã đơn đặt phòng cần thanh toán
	 * @param gioRa      giờ trả phòng
	 * @return true nếu thanh toán thành công
	 */
	public boolean thanhToanDatPhong(String maDatPhong, LocalTime gioRa) {
		try {
			Connection connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement;
			String sql;
			boolean res;

//			[Phong] - Cập nhật trạng thái của phòng
//						+ Đang thuê -> Trống
//						+ Phòng tạm -> Đã đặt
			sql = "UPDATE [dbo].[Phong] SET [trangThai] = (CASE "
					+ "WHEN [trangThai] = N'Đang thuê' THEN N'Trống' ELSE N'Đã đặt' END) WHERE [maPhong] IN ("
					+ "SELECT CTDP.[phong] FROM [dbo].[ChiTietDatPhong] CTDP "
					+ "JOIN [dbo].[DonDatPhong] DDP ON CTDP.donDatPhong = DDP.maDonDatPhong "
					+ "WHERE CTDP.[gioRa] IS NULL AND DDP.[trangThai] = N'Đang thuê' AND CTDP.[donDatPhong] = ?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, maDatPhong);

			res = preparedStatement.executeUpdate() > 0;

			if (!res)
				return rollback();

//			[ChiTietDatPhong] - Cập nhật giờ ra
//						+ NULL -> gioRa
			res = chiTietDatPhong_DAO.thanhToanDatPhong(maDatPhong, gioRa);
			if (!res)
				return rollback();

//			[DonDatPhong] - Cập nhật trạng thái của đơn đặt phòng thành đã trả
			sql = "UPDATE [dbo].[DonDatPhong] SET [trangThai] = N'Đã trả' WHERE [maDonDatPhong] = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, maDatPhong);

			res = preparedStatement.executeUpdate() > 0;

			if (!res)
				return rollback();

			return commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Thêm phiếu đặt phòng ngay mới
	 * 
	 * @param khachHang khách hàng đặt phòng
	 * @param nhanVien  nhân viên tạo hóa đơn
	 * @param phongs    danh sách các phòng mà khách thuê
	 * @return true nếu thêm thành công
	 */
	public boolean themPhieuDatPhongNgay(KhachHang khachHang, NhanVien nhanVien, List<Phong> phongs) {
		try {
			String maDatPhong = taoMaDatPhong();

			if (maDatPhong == null)
				return false;

			Connection connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);
			Time time = Time.valueOf(LocalTime.now());
			Date date = Date.valueOf(LocalDate.now());

//			[DonDatPhong] - Tạo đơn đặt phòng
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT DonDatPhong VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, maDatPhong);
			preparedStatement.setString(2, khachHang.getMaKhachHang());
			preparedStatement.setString(3, nhanVien.getMaNhanVien());
			preparedStatement.setDate(4, date);
			preparedStatement.setTime(5, time);
			preparedStatement.setDate(6, date);
			preparedStatement.setTime(7, time);
			preparedStatement.setString(8, DonDatPhong.convertTrangThaiToString(TrangThai.DangThue));
			boolean res = preparedStatement.executeUpdate() > 0;

			if (!res)
				return rollback();

			res = chiTietDatPhong_DAO.themChiTietDatPhong(maDatPhong, phongs, time);
			if (!res)
				rollback();
			return commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Thêm phiếu đặt phòng trước mới
	 * 
	 * @param khachHang     khách hàng đặt phòng
	 * @param nhanVien      nhân viên tạo hóa đơn
	 * @param phongs        danh sách các phòng mà khách đặt
	 * @param ngayNhanPhong ngày nhận phòng
	 * @param gioNhanPhong  giờ nhận phòng
	 * @return true nếu thêm thành công
	 */
	public boolean themPhieuDatPhongTruoc(KhachHang khachHang, NhanVien nhanVien, List<Phong> phongs,
			LocalDate ngayNhanPhong, LocalTime gioNhanPhong) {
		try {
			String maDatPhong = taoMaDatPhong();

			if (maDatPhong == null)
				return false;

			Connection connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);
			Time time = Time.valueOf(LocalTime.now());
			Date date = Date.valueOf(LocalDate.now());

//			[DonDatPhong] - Tạo phiếu đặt phòng
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT DonDatPhong VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, maDatPhong);
			preparedStatement.setString(2, khachHang.getMaKhachHang());
			preparedStatement.setString(3, nhanVien.getMaNhanVien());
			preparedStatement.setDate(4, date);
			preparedStatement.setTime(5, time);
			preparedStatement.setDate(6, Date.valueOf(ngayNhanPhong));
			preparedStatement.setTime(7, Time.valueOf(gioNhanPhong));
			preparedStatement.setString(8, DonDatPhong.convertTrangThaiToString(TrangThai.DangCho));
			boolean result = preparedStatement.executeUpdate() > 0;

			if (!result)
				return rollback();

			entity.Phong.TrangThai trangThai, trangThaiNew;
			Time gioNhanPhongTime = Time.valueOf(gioNhanPhong);
			for (Phong phong : phongs) {
//				[ChiTietDatPhong] - Tạo chi tiết phiếu đặt phòng
				result = chiTietDatPhong_DAO.themChiTietDatPhong(maDatPhong, phong, gioNhanPhongTime);
				if (!result)
					return rollback();

//				[Phong] - Cập nhật trạng thái phòng
//							+ Trống -> Đã đặt
//							+ Đang thuê -> Phòng tạm
//							+ Đã đặt -> Đã đặt
//							+ Phòng tạm -> Phòng tạm
				trangThai = phong_DAO.getTrangThai(phong.getMaPhong());
				trangThaiNew = entity.Phong.TrangThai.DaDat;

				if (trangThai.equals(entity.Phong.TrangThai.DangThue)
						|| trangThai.equals(entity.Phong.TrangThai.PhongTam))
					trangThaiNew = entity.Phong.TrangThai.PhongTam;
				result = phong_DAO.capNhatTrangThaiPhong(phong, Phong.convertTrangThaiToString(trangThaiNew));
				if (!result)
					return rollback();
			}

			return commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// Tìm phòng có đơn đặt phòng trước khác
	public List<Phong> timPhongCoDonDatTruocKhac(List<Phong> phongs, DonDatPhong donDatPhong) {
		List<Phong> listPhongCoDonDatTruocKhac = new ArrayList<>();
		String sql;

		sql = "SELECT TOP(1) * FROM ChiTietDatPhong CP INNER JOIN DonDatPhong DP  ON CP.donDatPhong = DP.maDonDatPhong\n"
				+ "where CP.phong = ?  and DP.ngayNhanPhong = ? and DP.gioNhanPhong < ?   and DP.trangThai = N'Đang chờ'";
		try {
			for (Phong phong : phongs) {
				PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
				preparedStatement.setString(1, phong.getMaPhong());
				preparedStatement.setString(2, donDatPhong.getNgayNhanPhong().toString());
				preparedStatement.setString(3, donDatPhong.getGioNhanPhong().toString());

				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next())
					listPhongCoDonDatTruocKhac.add(phong);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listPhongCoDonDatTruocKhac;
	}

	// Tìm phòng có trạng thái đang thuê
	public List<Phong> timPhongDangThue(List<Phong> phongs) {
		List<Phong> listPhongDangThue = new ArrayList<>();
		String sql = "SELECT * FROM ChiTietDatPhong CP INNER JOIN DonDatPhong DP  ON CP.donDatPhong = DP.maDonDatPhong\n"
				+ "WHERE CP.phong = ? and DP.trangThai = N'Đang thuê'";
		try {
			for (Phong phong : phongs) {
				PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
				preparedStatement.setString(1, phong.getMaPhong());

				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next())
					listPhongDangThue.add(phong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listPhongDangThue;
	}

}
