package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietDatPhong;
import entity.DonDatPhong;
import entity.DonDatPhong.TrangThai;
import entity.KhachHang;
import entity.NhanVien;
import entity.Phong;

public class PhieuDatPhongTruoc_DAO {
	/**
	 * Get chi tiết phiếu đặt phòng theo mã phiếu đặt, trạng thái và số điện thoại
	 * 
	 * @param maPhieuDat
	 * @param soDienThoai
	 * @param trangThai
	 * @return
	 */
	public List<DonDatPhong> filterDonDatPhong(String ngayNhanPhong, String soDienThoai, String trangThai) {
		List<DonDatPhong> list = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
					"SELECT DISTINCT maDonDatPhong, khachHang, nhanVien, ngayDatPhong, gioDatPhong, ngayNhanPhong, gioNhanPhong, trangThai FROM DonDatPhong \r\n"
							+ "INNER JOIN KhachHang ON DonDatPhong.khachHang = KhachHang.maKhachHang\r\n"
							+ "WHERE DonDatPhong.ngayNhanPhong like ? and DonDatPhong.trangThai like ? and KhachHang.soDienThoai like ? \r\n"
							+ "EXCEPT SELECT * FROM DonDatPhong\r\n"
							+ "WHERE trangThai like N'Đang thuê' or trangThai like N'Đã trả' "
							+ "ORDER BY trangThai DESC, ngayNhanPhong ASC, gioNhanPhong ASC");

			preparedStatement.setString(1, "%" + ngayNhanPhong + "%");
			preparedStatement.setString(2, "%" + trangThai + "%");
			preparedStatement.setString(3, soDienThoai);

			ResultSet resultSet = preparedStatement.executeQuery();
			DonDatPhong donDatPhong;
			while (resultSet.next()) {
				donDatPhong = getDonDatPhong(resultSet);
				list.add(donDatPhong);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get tất cả các đơn đặt phòng
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public List<DonDatPhong> getAllDonDatPhong() {
		List<DonDatPhong> list = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
					"SELECT DISTINCT * FROM DonDatPhong where trangThai like N'Đang chờ' or trangThai like N'Đã hủy' "
							+ "ORDER BY trangThai DESC, ngayNhanPhong ASC, gioNhanPhong ASC ");

			ResultSet resultSet = preparedStatement.executeQuery();
			DonDatPhong donDatPhong;
			while (resultSet.next()) {
				donDatPhong = getDonDatPhong(resultSet);
				list.add(donDatPhong);
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
		return new ChiTietDatPhong(donDatPhong, phong, gioVao);
	}

	/**
	 * Get chi tiết đặt phòng của phòng đang chờ
	 * 
	 * @param phong
	 * @return
	 */
	public ChiTietDatPhong getChiTietDatPhongTheoMa(DonDatPhong datPhong) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM ChiTietDatPhong WHERE donDatPhong = ? ");
			preparedStatement.setString(1, datPhong.getMaDonDatPhong());

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return getChiTietDatPhong(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get đơn đặt phòng resultSet
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private DonDatPhong getDonDatPhong(ResultSet resultSet) throws SQLException {
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
}
