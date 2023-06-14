package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.Phuong;
import entity.Quan;
import entity.Tinh;

public class NhanVien_DAO extends DAO {

	public NhanVien_DAO() {
	}

	/**
	 * Cập nhật thông tin nhân viên
	 * 
	 * @param nhanVien
	 * @param taiKhoan
	 * @return
	 */
	public boolean capNhatNhanVien(NhanVien nhanVien) {
		try {

			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
					"UPDATE NhanVien SET hoTen = ?, cccd = ?, soDienThoai = ?, ngaySinh = ?, gioiTinh = ?, tinh = ?, quan = ?, phuong = ?, diaChiCuThe = ?, chucVu = ?, luong = ?, trangThai = ?, matKhau = ? WHERE maNhanVien = ?");
			preparedStatement.setString(1, nhanVien.getHoTen());
			preparedStatement.setString(2, nhanVien.getCccd());
			preparedStatement.setString(3, nhanVien.getSoDienThoai());
			preparedStatement.setDate(4, Date.valueOf(nhanVien.getNgaySinh()));
			preparedStatement.setBoolean(5, nhanVien.isGioiTinh());
			preparedStatement.setString(6, nhanVien.getTinh().getId());
			preparedStatement.setString(7, nhanVien.getQuan().getId());
			preparedStatement.setString(8, nhanVien.getPhuong().getId());
			preparedStatement.setString(9, nhanVien.getDiaChiCuThe());
			preparedStatement.setString(10, NhanVien.convertChucVuToString(nhanVien.getChucVu()));
			preparedStatement.setDouble(11, nhanVien.getLuong());
			preparedStatement.setString(12, NhanVien.convertTrangThaiToString(nhanVien.getTrangThai()));
			preparedStatement.setString(13, nhanVien.getMatKhau());
			preparedStatement.setString(14, nhanVien.getMaNhanVien());

			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Get nhân viên theo họ tên, mã nhân viên và trạng thái làm việc
	 * 
	 * @param hoTen
	 * @param maNhanVien
	 * @param trangThai
	 * @return
	 */
	public List<NhanVien> filterNhanVien(String hoTen, String maNhanVien, String trangThai) {
		List<NhanVien> list = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection().prepareStatement(
					"SELECT * FROM NhanVien WHERE hoTen LIKE ? and maNhanVien like ? and trangThai like ?");

			preparedStatement.setString(1, "%" + hoTen + "%");
			preparedStatement.setString(2, "%" + maNhanVien + "%");
			preparedStatement.setString(3, "%" + trangThai + "%");

			resultSet = preparedStatement.executeQuery();
			NhanVien nhanVien;
			while (resultSet.next()) {
				nhanVien = getNhanVien(resultSet);
				list.add(nhanVien);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return list;
	}

	/**
	 * Get tất cả nhân viên
	 * 
	 * @return danh sách nhân viên
	 */
	public List<NhanVien> getAllNhanVien() {
		List<NhanVien> list = new ArrayList<NhanVien>();
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = ConnectDB.getConnection().createStatement();
			resultSet = statement.executeQuery(("SELECT * FROM NhanVien"));
			NhanVien nhanVien;
			while (resultSet.next()) {
				nhanVien = getNhanVien(resultSet);
				list.add(nhanVien);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(statement, resultSet);
		}

		return list;
	}

	public List<NhanVien> getNhanVien(List<String> dsMaNhanVien) {
		List<NhanVien> list = new ArrayList<>();

		try {
			int length = dsMaNhanVien.size();
			if (length == 0)
				return getAllNhanVien();
			String q = "?";
			for (int i = 0; i < length - 1; i++)
				q += ", ?";
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement(String.format("SELECT * FROM NhanVien WHERE maNhanVien IN (%s)", q));

			for (int i = 0; i < length; i++) {
				preparedStatement.setString(i + 1, dsMaNhanVien.get(i));
			}

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				list.add(getNhanVien(resultSet));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get nhân viên từ resultSet
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private NhanVien getNhanVien(ResultSet resultSet) throws SQLException {
		String maNhanVien = resultSet.getString("maNhanVien");
		String hoTen = resultSet.getString("hoTen");
		String cccd = resultSet.getString("cccd");
		String soDienThoai = resultSet.getString("soDienThoai");
		LocalDate ngaySinh = resultSet.getDate("ngaySinh").toLocalDate();
		boolean gioiTinh = resultSet.getBoolean("gioiTinh");
		String tinh = resultSet.getString("tinh");
		String quan = resultSet.getString("quan");
		String phuong = resultSet.getString("phuong");
		String diaChiCuThe = resultSet.getString("diaChiCuThe");
		String chucVu = resultSet.getString("chucVu");
		double luong = resultSet.getDouble("luong");
		String trangThai = resultSet.getString("trangThai");
		String matKhau = resultSet.getString("matKhau");

		return new NhanVien(maNhanVien, hoTen, cccd, soDienThoai, ngaySinh, gioiTinh, new Tinh(tinh), new Quan(quan),
				new Phuong(phuong), diaChiCuThe, NhanVien.convertStringToChucVu(chucVu), luong,
				NhanVien.convertStringToTrangThai(trangThai), matKhau);
	}

	/**
	 * Get nhân viên theo mã nhân viên
	 * 
	 * @param maNhanVien
	 * @return
	 */
	public NhanVien getNhanVienTheoMa(String maNhanVien) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM NhanVien WHERE maNhanVien = ?");
			preparedStatement.setString(1, maNhanVien);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return getNhanVien(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return null;
	}

	/**
	 * Get tất cả nhân viên theo trạng thái làm việc
	 * 
	 * @param trangThai
	 * @return
	 */
	public List<NhanVien> getNhanVienTheoTrangThai(String trangThai) {
		List<NhanVien> list = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM NhanVien WHERE trangThai = ?");
			preparedStatement.setString(1, trangThai);

			ResultSet resultSet = preparedStatement.executeQuery();
			NhanVien nhanVien;
			while (resultSet.next()) {
				nhanVien = getNhanVien(resultSet);
				list.add(nhanVien);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void importNhanVien(List<NhanVien> list) {
		Connection con = ConnectDB.getConnection();
		String sql = "INSERT NhanVien VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(sql);
			for (NhanVien nhanVien : list) {
				try {
					preparedStatement.setString(1, nhanVien.getMaNhanVien());
					preparedStatement.setString(2, nhanVien.getHoTen());
					preparedStatement.setString(3, nhanVien.getCccd());
					preparedStatement.setString(4, nhanVien.getSoDienThoai());
					preparedStatement.setDate(5, Date.valueOf(nhanVien.getNgaySinh()));
					preparedStatement.setBoolean(6, nhanVien.isGioiTinh());
					preparedStatement.setString(7, nhanVien.getTinh().getId());
					preparedStatement.setString(8, nhanVien.getQuan().getId());
					preparedStatement.setString(9, nhanVien.getPhuong().getId());
					preparedStatement.setString(10, nhanVien.getDiaChiCuThe());
					preparedStatement.setString(11, NhanVien.convertChucVuToString(nhanVien.getChucVu()));
					preparedStatement.setDouble(12, nhanVien.getLuong());
					preparedStatement.setString(13, NhanVien.convertTrangThaiToString(nhanVien.getTrangThai()));
					preparedStatement.setString(14, nhanVien.getMatKhau());
					preparedStatement.executeUpdate();
				} catch (Exception e) {
					System.out.println("sai");
					e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public boolean isCCCDDaTonTai(NhanVien nhanVien, String cccd) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM [dbo].[NhanVien] WHERE [cccd] = ? AND maNhanVien <> ?");
			preparedStatement.setString(1, cccd);
			preparedStatement.setString(2, nhanVien.getMaNhanVien());

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean isCCCDDaTonTai(String cccd) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM [dbo].[NhanVien] WHERE [cccd] = ?");
			preparedStatement.setString(1, cccd);

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean isSoDienThoaiDaTonTai(NhanVien nhanVien, String soDienThoai) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM [dbo].[NhanVien] WHERE [soDienThoai] = ? AND maNhanVien <> ?");
			preparedStatement.setString(1, soDienThoai);
			preparedStatement.setString(2, nhanVien.getMaNhanVien());

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean isSoDienThoaiDaTonTai(String soDienThoai) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM [dbo].[NhanVien] WHERE [soDienThoai] = ?");
			preparedStatement.setString(1, soDienThoai);

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Chuyển trạng thái của nhân viên có mã maNhanVien sang nghỉ làm
	 * 
	 * @param maNhanVien
	 * @return
	 */
	public boolean setNghiLam(String maNhanVien) {
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE NhanVien SET trangThai = N'Nghỉ làm' WHERE maNhanVien = ?");
			preparedStatement.setString(1, maNhanVien);

			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}

		return false;
	}

	public String taoMaNhanVien() {
		try {
			String sql = "SELECT TOP 1 [maNhanVien] FROM [dbo].[NhanVien] ORDER BY [maNhanVien] DESC";
			Statement statement = ConnectDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				String maNhanVien = resultSet.getString(1);
				int number = Integer.parseInt(maNhanVien.substring(2));
				number++;
				String maNhanVienNew = number + "";

				while (maNhanVienNew.length() < 3)
					maNhanVienNew = "0" + maNhanVienNew;

				return "NV" + maNhanVienNew;
			} else
				return "NV001";
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean themNhanVien(NhanVien nhanVien) {
		try {
			Connection con = ConnectDB.getConnection();
			String sql = "INSERT NhanVien VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, nhanVien.getMaNhanVien());
			preparedStatement.setString(2, nhanVien.getHoTen());
			preparedStatement.setString(3, nhanVien.getCccd());
			preparedStatement.setString(4, nhanVien.getSoDienThoai());
			preparedStatement.setDate(5, Date.valueOf(nhanVien.getNgaySinh()));
			preparedStatement.setBoolean(6, nhanVien.isGioiTinh());
			preparedStatement.setString(7, nhanVien.getTinh().getId());
			preparedStatement.setString(8, nhanVien.getQuan().getId());
			preparedStatement.setString(9, nhanVien.getPhuong().getId());
			preparedStatement.setString(10, nhanVien.getDiaChiCuThe());
			preparedStatement.setString(11, NhanVien.convertChucVuToString(nhanVien.getChucVu()));
			preparedStatement.setDouble(12, nhanVien.getLuong());
			preparedStatement.setString(13, NhanVien.convertTrangThaiToString(nhanVien.getTrangThai()));
			preparedStatement.setString(14, nhanVien.getMatKhau());
			preparedStatement.execute();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean isTaiKhoan(String maNhanVien, String matKhau) {
//		SELECT * FROM [maNhanVien] LIKE ? AND [matKhau] LIKE ?
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM NHANVIEN WHERE [maNhanVien] LIKE ? AND [matKhau] LIKE ?");
			preparedStatement.setString(1, maNhanVien);
			preparedStatement.setString(2, matKhau);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
