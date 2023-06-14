package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietDatPhong;
import entity.DonDatPhong;
import entity.KhachHang;
import entity.NhanVien;
import entity.Phong;
import entity.Phong.TrangThai;

public class ChiTietDatPhong_DAO extends DAO {
	private Phong_DAO phong_DAO;

	/**
	 * Constructor
	 */
	public ChiTietDatPhong_DAO() {
		phong_DAO = new Phong_DAO();
	}

	/**
	 * Get tất cả các chi tiết đặt phòng theo đơn đặt phòng
	 * 
	 * @param datPhong
	 * @return
	 */
	public List<ChiTietDatPhong> getAllChiTietDatPhong(DonDatPhong datPhong) {
		List<ChiTietDatPhong> list = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM ChiTietDatPhong WHERE donDatPhong = ?");
			preparedStatement.setString(1, datPhong.getMaDonDatPhong());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
				list.add(getChiTietDatPhong(resultSet));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return list;
	}

	public List<ChiTietDatPhong> getAllChiTietDatPhongThanhToan() {
		List<ChiTietDatPhong> list = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM ChiTietDatPhong WHERE gioRa is not NULL");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
				list.add(getChiTietDatPhong(resultSet));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return list;
	}

	/**
	 * Get chi tiết đặt phòng theo ngày, tháng năm và nhân viên
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @param maNhanVien
	 * @return
	 */
	public List<ChiTietDatPhong> getChiTietDatPhong(int day, int month, int year, String maNhanVien) {
		return getChiTietDatPhong(day, month, year, maNhanVien, "");
	}


	public List<ChiTietDatPhong> getChiTietDatPhong(int day, int month, int year, String maNhanVien, String maKH) {
		return getChiTietDatPhong(day, month, year, maNhanVien, maKH, "");
	}
	
	/**
	 * Get chi tiết đặt phòng theo ngày, tháng năm, nhân viên và khách hàng
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @param maNhanVien
	 * @return
	 */
	public List<ChiTietDatPhong> getChiTietDatPhong(int day, int month, int year, String maNhanVien,
			String maKhachHang, String filterNV) {
		List<ChiTietDatPhong> list = new ArrayList<>();
		String sql = "SELECT CTDP.*, P.*, ngayNhanPhong, [maKhachHang], KH.[hoTen] AS HOTENKHACHHANG, "
				+ "[maNhanVien], NV.[hoTen] AS HOTENNHANVIEN FROM [dbo].[DonDatPhong] DDP "
				+ "JOIN [dbo].[ChiTietDatPhong] CTDP ON DDP.maDonDatPhong = CTDP.donDatPhong "
				+ "JOIN [dbo].[Phong] P ON CTDP.phong = P.maPhong "
				+ "JOIN [dbo].[KhachHang] KH ON DDP.[khachHang] = KH.[maKhachHang] "
				+ "JOIN [dbo].[NhanVien] NV ON DDP.NHANVIEN = NV.MANHANVIEN "
				+ "WHERE YEAR([ngayNhanPhong]) = ? AND DDP.[trangThai] = N'Đã trả' AND nhanVien LIKE ?"
				+ " AND KH.maKhachHang like ? AND NV.MANHANVIEN LIKE ?";

		if (month > 0)
			sql += " AND MONTH([ngayNhanPhong]) = ?";
		if (day > 0)
			sql += " AND DAY([ngayNhanPhong]) = ?";

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setInt(1, year);
			preparedStatement.setString(2, "%" + maNhanVien + "%");
			preparedStatement.setString(3, "%" + maKhachHang + "%");
			preparedStatement.setString(4, "%" + filterNV + "%");
			if (month > 0)
				preparedStatement.setInt(5, month);
			if (day > 0)
				preparedStatement.setInt(6, day);

			ResultSet resultSet = preparedStatement.executeQuery();
			ChiTietDatPhong chiTietDatPhong;
			Phong phong;
			String maPhong;
			String tenLoai;
			NhanVien nhanVien;
			KhachHang khachHang;
			int soLuongKhach;
			LocalDate ngayNhanPhong;
			while (resultSet.next()) {
				chiTietDatPhong = getChiTietDatPhong(resultSet);

				maPhong = resultSet.getString("maPhong");
				tenLoai = resultSet.getString("tenLoai");
				soLuongKhach = resultSet.getInt("soLuongKhach");
				phong = new Phong(maPhong, soLuongKhach, TrangThai.DangThue, false, tenLoai);
				chiTietDatPhong.setPhong(phong);

				ngayNhanPhong = resultSet.getDate("ngayNhanPhong").toLocalDate();
				chiTietDatPhong.getDonDatPhong().setNgayNhanPhong(ngayNhanPhong);
				nhanVien = new NhanVien(resultSet.getString("maNhanVien"));
				nhanVien.setHoTen(resultSet.getString("HOTENNHANVIEN"));
				chiTietDatPhong.getDonDatPhong().setNhanVien(nhanVien);
				khachHang = new KhachHang(resultSet.getString("maKhachHang"));
				khachHang.setHoTen(resultSet.getString("HOTENKHACHHANG"));
				chiTietDatPhong.getDonDatPhong().setKhachHang(khachHang);

				list.add(chiTietDatPhong);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get chi tiết đặt phòng resultSet
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private ChiTietDatPhong getChiTietDatPhong(ResultSet resultSet) throws SQLException {
		DonDatPhong donDatPhong = new DonDatPhong(resultSet.getString("donDatPhong"));
		Phong phong = new Phong(resultSet.getString("phong"));
		LocalTime gioVao = resultSet.getTime("gioVao").toLocalTime();
		Time time = resultSet.getTime("gioRa");
		LocalTime gioRa = time == null ? null : resultSet.getTime("gioRa").toLocalTime();
		return new ChiTietDatPhong(donDatPhong, phong, gioVao, gioRa);
	}

	/**
	 * Get chi tiết đặt phòng đang thuê
	 * 
	 * @param maDonDatPhong
	 * @param phong
	 * @return
	 */
	public ChiTietDatPhong getChiTietDatPhongDangThue(String maDonDatPhong, String phong) {
		try {
			String sql = "SELECT * FROM [dbo].[ChiTietDatPhong] "
					+ "WHERE [donDatPhong] = ? AND [phong] = ? AND [gioRa] IS NULL";
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, maDonDatPhong);
			preparedStatement.setString(2, phong);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return getChiTietDatPhong(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get chi tiết đặt phòng của phòng đang thuê từ mã đặt phòng
	 * 
	 * @param phong
	 * @return
	 */
	public ChiTietDatPhong getChiTietDatPhongTheoMaDatPhong(String maDonDatPhong) {
		ChiTietDatPhong chiTietDatPhong = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM ChiTietDatPhong WHERE donDatPhong = ? and gioRa is null");
			preparedStatement.setString(1, maDonDatPhong);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				chiTietDatPhong = getChiTietDatPhong(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return chiTietDatPhong;
	}

	/**
	 * Get giờ vào phòng của danh sách phòng các phòng chờ
	 * 
	 * @param dsPhong danh sách phòng
	 * @return
	 */
	public List<ChiTietDatPhong> getGioVaoPhongCho(List<Phong> dsPhong) {
		List<ChiTietDatPhong> list = new ArrayList<>();
		String q = "?";
		int length = dsPhong.size();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		for (int i = 1; i < length; ++i)
			q += ", ?";

		String sql = String.format("SELECT CTDP.* FROM [dbo].[DonDatPhong] DP "
				+ "JOIN [dbo].[ChiTietDatPhong] CTDP ON DP.maDonDatPhong = CTDP.donDatPhong "
				+ "WHERE [ngayNhanPhong] = CONVERT(DATE, GETDATE()) "
				+ "AND [trangThai] = N'Đang chờ' AND [phong] in (%s)", q);

		try {
			preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			for (int i = 0; i < length; ++i)
				preparedStatement.setString(i + 1, dsPhong.get(i).getMaPhong());

			resultSet = preparedStatement.executeQuery();
			ChiTietDatPhong chiTietDatPhong;
			while (resultSet.next()) {
				chiTietDatPhong = getChiTietDatPhong(resultSet);
				list.add(chiTietDatPhong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return list;
	}

	/**
	 * Set giờ ra của phòng
	 * 
	 * @param maDonDatPhong
	 * @param maPhong
	 * @param gioRa
	 * @return
	 */
	public boolean setGioRa(String maDonDatPhong, String maPhong, Time gioRa) {
		PreparedStatement preparedStatement;
		String sql = "UPDATE [dbo].[ChiTietDatPhong] SET [gioRa] = ? WHERE [phong] IN "
				+ "(SELECT CTDP.[phong] FROM [dbo].[DonDatPhong] DP JOIN [dbo].[ChiTietDatPhong] CTDP "
				+ "ON DP.[maDonDatPhong] = CTDP.[donDatPhong] WHERE DP.[trangThai] = N'Đang thuê' "
				+ "AND CTDP.[phong] = ? AND DP.maDonDatPhong = ?) AND donDatPhong = ?";
		try {
			preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setTime(1, gioRa);
			preparedStatement.setString(2, maPhong);
			preparedStatement.setString(3, maDonDatPhong);
			preparedStatement.setString(4, maDonDatPhong);
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Thanh toán đơn đặt phòng
	 * 
	 * @param maDatPhong
	 * @param gioRa
	 * @return
	 */
	public boolean thanhToanDatPhong(String maDatPhong, LocalTime gioRa) {
		boolean res = false;
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE ChiTietDatPhong SET gioRa = ? WHERE donDatPhong = ? AND gioRa is null";

		try {
			preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setTime(1, Time.valueOf(gioRa));
			preparedStatement.setString(2, maDatPhong);

			res = preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}

		return res;
	}

	/**
	 * Thêm chi tiết đặt phòng
	 * 
	 * @param maDatPhong
	 * @param dsPhong
	 * @param gioVao
	 * @return
	 */
	public boolean themChiTietDatPhong(String maDatPhong, List<Phong> dsPhong, Time gioVao) {
		try {
			ConnectDB.getConnection().setAutoCommit(false);
			boolean res;

			for (Phong phong : dsPhong) {
//				[ChiTietDatPhong] - Tạo chi tiết phiếu đặt phòng
				res = themChiTietDatPhong(maDatPhong, phong, gioVao);
				if (!res)
					return rollback();

//				[Phong] - Cập nhật trạng thái phòng
//							+ Trống --> Đang thuê
//							+ Đang thuê --> X
//							+ Phòng tạm --> X
//							+ Phòng chờ --> Phòng tạm
				TrangThai trangThai = phong_DAO.getTrangThai(phong.getMaPhong());
				if (trangThai == null)
					return rollback();
				TrangThai trangThaiNew = Phong.TrangThai.DangThue;

				if (trangThai.equals(entity.Phong.TrangThai.DaDat))
					trangThaiNew = Phong.TrangThai.PhongTam;

				res = phong_DAO.capNhatTrangThaiPhong(phong, Phong.convertTrangThaiToString(trangThaiNew));
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
	 * Thêm chi tiết đặt phòng
	 * 
	 * @param maDatPhong
	 * @param phong
	 * @param gioVao
	 * @return
	 */
	public boolean themChiTietDatPhong(String maDatPhong, Phong phong, Time gioVao) {
		PreparedStatement preparedStatement = null;
		boolean res = false;
		String sql = "INSERT ChiTietDatPhong(donDatPhong, phong, gioVao) VALUES(?, ?, ?)";
		try {
			preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, maDatPhong);
			preparedStatement.setString(2, phong.getMaPhong());
			preparedStatement.setTime(3, gioVao);

			res = preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}

		return res;
	}

}
