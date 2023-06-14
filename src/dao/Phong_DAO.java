package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Phong;
import entity.Phong.TrangThai;
import utils.Utils;

public class Phong_DAO extends DAO {

	public boolean capNhatTrangThaiPhong(Phong phong, String trangThai) {
		PreparedStatement preparedStatement = null;
		boolean res = false;
		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("Update Phong SET trangThai = ? WHERE maPhong = ?");
			preparedStatement.setString(1, trangThai);
			preparedStatement.setString(2, phong.getMaPhong());

			res = preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}

		return res;
	}

	/**
	 * Get tất cả các phòng
	 * 
	 * @return
	 */
	public List<Phong> getAllPhong() {
		List<Phong> list = new ArrayList<>();

		Statement statement;
		try {
			statement = ConnectDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Phong WHERE trangThaiXoa = 0");
			Phong phong;
			while (resultSet.next()) {
				phong = getPhong(resultSet);
				list.add(phong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get tất cả các phòng theo loại phòng
	 * 
	 * @param loaiPhong
	 * @return
	 */
	public List<Phong> getAllPhong(String loaiPhong) {
		List<Phong> list = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM Phong WHERE tenLoai LIKE ? AND trangThaiXoa = 0");
			preparedStatement.setString(1, "%" + loaiPhong + "%");

			ResultSet resultSet = preparedStatement.executeQuery();
			Phong phong;
			while (resultSet.next()) {
				phong = getPhong(resultSet);
				list.add(phong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get phòng theo trạng thái, loại phòng và số lượng khách
	 * 
	 * @param trangThai
	 * @param loaiPhong
	 * @param soLuong
	 * @return
	 */
	public List<Phong> getAllPhong(String trangThai, String loaiPhong, String soLuong) {
		System.out.println(trangThai);
		System.out.println(loaiPhong);
		System.out.println(soLuong);
		List<Phong> list = new ArrayList<>();
		boolean isInteger = Utils.isInteger(soLuong);
		String sql = "SELECT * FROM Phong WHERE trangThai like ? and tenLoai like ? AND trangThaiXoa = 0";

		if (isInteger)
			sql += " AND soLuongKhach = ?";

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, "%" + trangThai + "%");
			preparedStatement.setString(2, "%" + loaiPhong + "%");

			if (isInteger)
				preparedStatement.setInt(3, Integer.parseInt(soLuong));

			ResultSet resultSet = preparedStatement.executeQuery();
			Phong phong;
			while (resultSet.next()) {
				phong = getPhong(resultSet);
				list.add(phong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Phong> getAllPhongDangThue() {
		List<Phong> list = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM Phong WHERE trangThai IN (N'Đang thuê', N'Phòng tạm') ");
			ResultSet resultSet = preparedStatement.executeQuery();
			Phong phong;
			while (resultSet.next()) {
				phong = getPhong(resultSet);
				list.add(phong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Get tất cả các phòng ĐÃ XÓA
	 * 
	 * @return
	 */
	public List<Phong> getAllPhongDaXoa() {
		List<Phong> list = new ArrayList<>();

		Statement statement;
		try {
			statement = ConnectDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Phong WHERE trangThaiXoa = 1");
			Phong phong;
			while (resultSet.next()) {
				phong = getPhong(resultSet);
				list.add(phong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Phong> getAllPhongTheoMa(List<Phong> list) {
		List<Phong> phongs = new ArrayList<>();

		int length = list.size();
		if (length <= 0)
			return getAllPhong();
		String q = "?";
		for (int i = 1; i < length; ++i)
			q += ", ?";

		String sql = String.format("SELECT * FROM Phong WHERE maPhong IN (%s) AND trangThaiXoa = 0", q);

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			for (int i = 0; i < length; ++i)
				preparedStatement.setString(i + 1, list.get(i).getMaPhong());

			ResultSet resultSet = preparedStatement.executeQuery();
			Phong phong;
			while (resultSet.next()) {
				phong = getPhong(resultSet);
				phongs.add(phong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return phongs;
	}

	/**
	 * Get tất cả các phòng theo trạng thái
	 * 
	 * @param trangThai
	 * @return
	 */
	public List<Phong> getAllPhongTheoTrangThai(String trangThai) {
		List<Phong> list = new ArrayList<>();

		try {
			String sql = "SELECT * FROM Phong WHERE trangThai LIKE ? AND trangThaiXoa = 0";
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, "%" + trangThai + "%");

			ResultSet resultSet = preparedStatement.executeQuery();
			Phong phong;
			while (resultSet.next()) {
				phong = getPhong(resultSet);
				list.add(phong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	private Phong getPhong(ResultSet resultSet) throws SQLException {
		String maPhong = resultSet.getString(1);
		int soLuongKhach = resultSet.getInt(2);
		String trangThai = resultSet.getString(3);
		boolean b = resultSet.getBoolean(4);
		String tenLoai = resultSet.getString(5);
		return new Phong(maPhong, soLuongKhach, Phong.convertStringToTrangThai(trangThai), b, tenLoai);
	}

	/**
	 * Get phòng theo mã phòng
	 * 
	 * @param maPhong
	 * @return
	 */
	public Phong getPhong(String maPhong) {
		try {
			String sql = "SELECT * FROM Phong WHERE maPhong = ?";
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, maPhong);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return getPhong(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Phong> getPhongTheoLoaiVaSoLuongKhach(String maPhong, String tenLoaiPhong, String soLuongKhach) {
		List<Phong> list = new ArrayList<>();

		try {
			if (soLuongKhach.equals("")) {
				PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
						"SELECT * FROM  Phong WHERE maPhong like ? and tenLoai like ? AND trangThaiXoa = 0 ");

				preparedStatement.setString(1, "%" + maPhong + "%");
				preparedStatement.setString(2, "%" + tenLoaiPhong + "%");
				ResultSet resultSet = preparedStatement.executeQuery();
				Phong phong;
				while (resultSet.next()) {
					phong = getPhong(resultSet);
					list.add(phong);
				}

				resultSet.close();
			} else {
				PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
						"SELECT * FROM   Phong WHERE maPhong like ? and tenLoai like ? and soLuongKhach = ? AND trangThaiXoa = 0");

				preparedStatement.setString(1, "%" + maPhong + "%");
				preparedStatement.setString(2, "%" + tenLoaiPhong + "%");
				preparedStatement.setInt(3, Integer.parseInt(soLuongKhach));
				ResultSet resultSet = preparedStatement.executeQuery();
				Phong phong;
				while (resultSet.next()) {
					phong = getPhong(resultSet);
					list.add(phong);
				}

				resultSet.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<Phong> getPhongTheoLoaiVaSoLuongKhachDaXoa(String maPhong, String tenLoaiPhong, String soLuongKhach) {
		List<Phong> list = new ArrayList<>();

		try {
			if (soLuongKhach.equals("")) {
				PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
						"SELECT * FROM   Phong WHERE maPhong like ? and tenLoai like ? AND trangThaiXoa = 1 ");

				preparedStatement.setString(1, "%" + maPhong + "%");
				preparedStatement.setString(2, "%" + tenLoaiPhong + "%");
				ResultSet resultSet = preparedStatement.executeQuery();
				Phong phong;
				while (resultSet.next()) {
					phong = getPhong(resultSet);
					list.add(phong);
				}

				resultSet.close();
			} else {
				PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
						"SELECT * FROM   Phong WHERE maPhong like ? and tenLoai like ? and soLuongKhach = ? AND trangThaiXoa = 1");

				preparedStatement.setString(1, "%" + maPhong + "%");
				preparedStatement.setString(2, "%" + tenLoaiPhong + "%");
				preparedStatement.setInt(3, Integer.parseInt(soLuongKhach));
				ResultSet resultSet = preparedStatement.executeQuery();
				Phong phong;
				while (resultSet.next()) {
					phong = getPhong(resultSet);
					list.add(phong);
				}

				resultSet.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public TrangThai getTrangThai(String maPhong) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT trangThai FROM Phong WHERE maPhong = ?");

			preparedStatement.setString(1, maPhong);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return Phong.convertStringToTrangThai(resultSet.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean isMaPhongTonTai(String maPhong) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT maPhong FROM Phong WHERE maPhong = ?");
			preparedStatement.setString(1, maPhong);
			return preparedStatement.executeQuery().next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Khôi phục phòng
	 * 
	 * @param maPhong
	 * @return
	 */
	public boolean khoiPhucPhong(String maPhong) {
		int res = 0;
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE Phong SET trangThaiXoa = 0 WHERE maPhong  = ?");
			preparedStatement.setString(1, maPhong);
			res = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res > 0;
	}

	public boolean suaPhong(Phong phong) {

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE Phong SET loaiPhong = ?, soLuongKhach = ? WHERE maPhong = ?");
			preparedStatement.setString(1, phong.getTenLoai());
			preparedStatement.setInt(2, phong.getSoLuongKhach());
			preparedStatement.setString(3, phong.getMaPhong());
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean themPhong(Phong phong) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("INSERT Phong VALUES (?, ?, ?, ?,?)");
			preparedStatement.setString(1, phong.getMaPhong());
			preparedStatement.setString(2, phong.getTenLoai());
			preparedStatement.setInt(3, phong.getSoLuongKhach());
			preparedStatement.setString(4, Phong.convertTrangThaiToString(Phong.TrangThai.Trong));
			preparedStatement.setBoolean(5, false);

			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Xóa phòng
	 * 
	 * @param maPhong
	 * @return
	 */
	public boolean xoaPhong(String maPhong) {
		int res = 0;
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE Phong SET trangThaiXoa = 1 WHERE maPhong  = ?");
			preparedStatement.setString(1, maPhong);
			res = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res > 0;
	}
	
	public List<String> getAllLoaiPhong() {
		List<String> list = new ArrayList<String>();
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT DISTINCT [tenLoai] FROM [dbo].[Phong]");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
				list.add(resultSet.getString(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
