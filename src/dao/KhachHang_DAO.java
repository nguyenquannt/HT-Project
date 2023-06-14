package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhachHang;
import entity.Phuong;
import entity.Quan;
import entity.Tinh;

public class KhachHang_DAO {
	/**
	 * Lọc khách hàng theo họ tên
	 * 
	 * @param hoTen
	 * @return
	 */
	public List<KhachHang> filterKhachHang(String hoTen) {
		List<KhachHang> list = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM KhachHang WHERE hoTen LIKE ? and trangThaiXoa = 0");

			preparedStatement.setString(1, "%" + hoTen + "%");

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
				list.add(getKhachHang(resultSet));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Lọc khách hàng đã xóa theo họ tên
	 * 
	 * @param hoTen
	 * @return
	 */
	public List<KhachHang> filterKhachHangDaXoa(String hoTen) {
		List<KhachHang> list = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM KhachHang WHERE hoTen LIKE ? and trangThaiXoa = 1");

			preparedStatement.setString(1, "%" + hoTen + "%");

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
				list.add(getKhachHang(resultSet));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get danh sách tất cả các khách hàng chưa xóa
	 * 
	 * @return
	 */
	public List<KhachHang> getAllKhachHang() {
		List<KhachHang> list = new ArrayList<KhachHang>();

		try {
			Statement statement = ConnectDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(("SELECT * FROM KhachHang where trangThaiXoa = 0"));

			while (resultSet.next())
				list.add(getKhachHang(resultSet));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get danh sách tất cả các khách hàng đã xóa
	 * 
	 * @return
	 */
	public List<KhachHang> getAllKhachHangDaXoa() {
		List<KhachHang> list = new ArrayList<KhachHang>();

		try {
			Statement statement = ConnectDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(("SELECT * FROM KhachHang where trangThaiXoa = 1"));

			while (resultSet.next())
				list.add(getKhachHang(resultSet));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get khách hàng từ resultSet
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private KhachHang getKhachHang(ResultSet resultSet) throws SQLException {
		String maKhachHang = resultSet.getString(1);
		String hoTen = resultSet.getString(2);
		String cccd = resultSet.getString(3);
		LocalDate ngaySinh = resultSet.getDate(4).toLocalDate();
		boolean gioiTinh = resultSet.getBoolean(5);
		String soDienThoai = resultSet.getString(6);
		Tinh tinh = new Tinh(resultSet.getString(7));
		Quan quan = new Quan(resultSet.getString(8));
		Phuong phuong = new Phuong(resultSet.getString(9));
		String diaChiCuThe = resultSet.getString(10);
		boolean trangThai = resultSet.getBoolean(11);

		return new KhachHang(maKhachHang, hoTen, cccd, ngaySinh, gioiTinh, soDienThoai, tinh, quan, phuong, diaChiCuThe,
				trangThai);
	}

	/**
	 * Get khách hàng theo số điện thoại
	 * 
	 * @param soDienThoai
	 * @return
	 */
	public KhachHang getKhachHang(String soDienThoai) {

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM KhachHang WHERE soDienThoai = ? and trangThaiXoa = 0");
			preparedStatement.setString(1, soDienThoai);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return getKhachHang(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Get khách hàng theo mã khách hàng
	 * 
	 * @param maKhachHang
	 * @return
	 */
	public KhachHang getKhachHangTheoMa(String maKhachHang) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM KhachHang WHERE maKhachHang = ? and trangThaiXoa = 0");
			preparedStatement.setString(1, maKhachHang);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return getKhachHang(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getMaKhachHang() {
		Statement statement;
		try {
			statement = ConnectDB.getConnection().createStatement();

			ResultSet resultSet = statement
					.executeQuery("SELECT TOP 1 [maKhachHang] FROM [dbo].[KhachHang]" + " ORDER BY [maKhachHang] DESC");

			if (resultSet.next()) {
				String maKhachHang = resultSet.getString(1);
				int soKhach = Integer.parseInt(maKhachHang.substring(2));
				soKhach++;
				String maKhachNew = soKhach + "";

				while (maKhachNew.length() < 3)
					maKhachNew = "0" + maKhachNew;

				return "KH" + maKhachNew;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "KH001";
	}

	public boolean isCCCDDaTonTai(String cccd) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM [dbo].[KhachHang] WHERE [cccd] = ?");
			preparedStatement.setString(1, cccd);

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
					.prepareStatement("SELECT * FROM [dbo].[KhachHang] WHERE [soDienThoai] = ?");
			preparedStatement.setString(1, soDienThoai);

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Khôi phục khách hàng
	 * 
	 * @param maKhachHang
	 * @return
	 */
	public boolean khoiPhucKhachHang(String maKhachHang) {
		int res = 0;
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE KhachHang SET trangThaiXoa = 0 WHERE maKhachHang  = ?");
			preparedStatement.setString(1, maKhachHang);
			res = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res > 0;
	}

	/**
	 * Cập nhật thông tin khách hàng
	 * 
	 * @param khachHang
	 * @return
	 */
	public boolean suaKhachHang(KhachHang khachHang) {
		int res = 0;
		PreparedStatement preparedStatement;
		try {
			preparedStatement = ConnectDB.getConnection().prepareStatement(
					"UPDATE KhachHang SET hoTen = ?, cccd = ?, ngaySinh = ?, gioiTinh = ?, soDienThoai = ?, tinh = ?, quan = ?, phuong = ?, diaChiCuThe = ? WHERE maKhachHang = ?");
			preparedStatement.setString(1, khachHang.getHoTen());
			preparedStatement.setString(2, khachHang.getCccd());
			preparedStatement.setDate(3, Date.valueOf(khachHang.getNgaySinh()));
			preparedStatement.setBoolean(4, khachHang.isGioiTinh());
			preparedStatement.setString(5, khachHang.getSoDienThoai());
			preparedStatement.setString(6, khachHang.getTinh().getId());
			preparedStatement.setString(7, khachHang.getQuan().getId());
			preparedStatement.setString(8, khachHang.getPhuong().getId());
			preparedStatement.setString(9, khachHang.getDiaChiCuThe());
			preparedStatement.setString(10, khachHang.getMaKhachHang());
			res = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res > 0;
	}

	/**
	 * Thêm khách hàng
	 * 
	 * @param khachHang
	 * @return
	 */
	public boolean themKhachHang(KhachHang khachHang) {
		int res = 0;
		PreparedStatement preparedStatement;
		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("INSERT KhachHang VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)");
			preparedStatement.setString(1, khachHang.getMaKhachHang());
			preparedStatement.setString(2, khachHang.getHoTen());
			preparedStatement.setString(3, khachHang.getCccd());
			preparedStatement.setDate(4, Date.valueOf(khachHang.getNgaySinh()));
			preparedStatement.setBoolean(5, khachHang.isGioiTinh());
			preparedStatement.setString(6, khachHang.getSoDienThoai());
			preparedStatement.setString(7, khachHang.getTinh().getId());
			preparedStatement.setString(8, khachHang.getQuan().getId());
			preparedStatement.setString(9, khachHang.getPhuong().getId());
			preparedStatement.setString(10, khachHang.getDiaChiCuThe());
			res = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res > 0;
	}

	/**
	 * Xóa khách hàng
	 * 
	 * @param maKhachHang
	 * @return
	 */
	public boolean xoaKhachHang(String maKhachHang) {
		int res = 0;
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE KhachHang SET trangThaiXoa = 1 WHERE maKhachHang  = ?");
			preparedStatement.setString(1, maKhachHang);
			res = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res > 0;
	}
}
