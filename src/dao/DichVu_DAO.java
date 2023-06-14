package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.DichVu;

public class DichVu_DAO {

	public DichVu_DAO() {
	}

	public boolean capNhatSoLuongDichVuTang(String maDV, int soLuong) {
		boolean res = false;
		PreparedStatement preparedStatement;
		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE DichVu SET soLuong += ? WHERE maDichVu = ?");
			preparedStatement.setInt(1, soLuong);
			preparedStatement.setString(2, maDV);
			res = preparedStatement.executeUpdate() > 0;
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Filter dịch vụ theo tên, loại và số lượng
	 * 
	 * @param tenDichVu
	 * @param tenLoaiDichVu
	 * @param soLuong
	 * @return danh sách dịch vụ
	 */
	public List<DichVu> filterDichVu(String tenDichVu, String tenLoaiDichVu, String soLuong,
			boolean isDaNgungKinhDoanh) {
		List<DichVu> list = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		String sql = "SELECT * FROM  DichVu WHERE tenDichVu like ? and loaiDichVu like ? and daNgungKinhDoanh = ?";
		try {
			if (soLuong.equals("<50"))
				sql += " and soLuong <= 50";
			else if (soLuong.equals("50-100"))
				sql += " and soLuong > 50 and soLuong <= 100";
			else if (soLuong.equals("100-200"))
				sql += " and soLuong > 100 and soLuong <= 200";
			else if (soLuong.equals(">200"))
				sql += " and soLuong > 200";

			preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, "%" + tenDichVu + "%");
			preparedStatement.setString(2, "%" + tenLoaiDichVu + "%");
			preparedStatement.setBoolean(3, isDaNgungKinhDoanh);
			ResultSet resultSet = preparedStatement.executeQuery();
			DichVu dichVu;
			while (resultSet.next()) {
				dichVu = getDichVu(resultSet);
				list.add(dichVu);
			}

			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<DichVu> getAllDichVu() {
		List<DichVu> list = new ArrayList<>();
		Statement statement;

		try {
			statement = ConnectDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM DichVu WHERE daNgungKinhDoanh = 0");
			DichVu dichVu;
			while (resultSet.next()) {
				dichVu = getDichVu(resultSet);
				list.add(dichVu);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<DichVu> getAllDichVuCoSoLuongLonHon0() {
		List<DichVu> list = new ArrayList<>();

		Statement statement;
		try {
			statement = ConnectDB.getConnection().createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM DichVu  WHERE soLuong > 0 and daNgungKinhDoanh = 0");
			DichVu dichVu;
			while (resultSet.next()) {
				dichVu = getDichVu(resultSet);
				list.add(dichVu);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<DichVu> getDanhSachDichVuTheoMa(List<DichVu> dsDichVu) {
		List<DichVu> list = new ArrayList<>();
		int length = dsDichVu.size();

		if (dsDichVu == null || length == 0)
			return list;
		try {
			String q = "''";
			for (int i = 0; i < length; ++i) {
				q += ", ?";
			}
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement(String.format("SELECT * FROM [dbo].[DichVu] WHERE [maDichVu] IN (%s)", q));
			DichVu dichVu;
			for (int i = 0; i < length; i++) {
				dichVu = dsDichVu.get(i);
				preparedStatement.setString(i + 1, dichVu.getMaDichVu());
			}
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				dichVu = getDichVu(resultSet);
				list.add(dichVu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	private DichVu getDichVu(ResultSet resultSet) throws SQLException {
		String maDV = resultSet.getString(1);
		String tenDV = resultSet.getString(2);
		String soLuong = resultSet.getString(3);
		String donViTinh = resultSet.getString(4);
		String giaMua = resultSet.getString(5);
		String loaiDichVu = resultSet.getString(7);

		return new DichVu(maDV, tenDV, Integer.valueOf(soLuong), donViTinh, Double.valueOf(giaMua), false, loaiDichVu);
	}

	public DichVu getDichVuTheoMa(String maDichVu) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM DichVu WHERE maDichVu = ? ");
			preparedStatement.setString(1, maDichVu);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return getDichVu(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<DichVu> getDichVuTheoMaVaLoai(String maDichVu, String loaiDichVu) {
		List<DichVu> list = new ArrayList<>();

		try {

			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * " + "FROM   DichVu WHERE tenDichVu like ? and loaiDichVu like ? and soLuong > 0 ");

			preparedStatement.setString(1, "%" + maDichVu + "%");
			preparedStatement.setString(2, "%" + loaiDichVu + "%");

			ResultSet resultSet = preparedStatement.executeQuery();
			DichVu dichVu;
			while (resultSet.next()) {
				dichVu = getDichVu(resultSet);
				list.add(dichVu);
			}

			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public String getMaDichVu() {
		Statement statement;
		try {
			statement = ConnectDB.getConnection().createStatement();

			ResultSet resultSet = statement
					.executeQuery("SELECT TOP 1 [maDichVu] FROM [dbo].[DichVu]" + " ORDER BY [maDichVu] DESC");

			if (resultSet.next()) {
				String maDichVu = resultSet.getString(1);
				int soDichVu = Integer.parseInt(maDichVu.substring(2));
				soDichVu++;
				String maDichVuNew = soDichVu + "";

				while (maDichVuNew.length() < 3)
					maDichVuNew = "0" + maDichVuNew;

				return "DV" + maDichVuNew;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "DV001";
	}

	/**
	 * Khôi phục dịch vụ
	 * 
	 * @param maDichVu
	 * @return
	 */
	public boolean khoiPhucDichVu(String maDichVu) {
		int res = 0;
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE DichVu SET daNgungKinhDoanh = 0 WHERE maDichVu  = ?");
			preparedStatement.setString(1, maDichVu);
			res = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res > 0;
	}

	public boolean suaDichVu(DichVu dichVu) {

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
					"UPDATE DichVu SET tenDichVu = ?, soLuong = ?, donViTinh = ?, loaiDichVu = ?, giaMua= ? WHERE maDichVu = ? ");
			preparedStatement.setString(1, dichVu.getTenDichVu());
			preparedStatement.setInt(2, dichVu.getSoLuong());
			preparedStatement.setString(3, dichVu.getDonViTinh());
			preparedStatement.setString(4, dichVu.getLoaiDichVu());
			preparedStatement.setDouble(5, dichVu.getGiaMua());
			preparedStatement.setString(6, dichVu.getMaDichVu());
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean themDichVu(DichVu dichVu) {
		int res = 0;
		PreparedStatement preparedStatement;
		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("INSERT DichVu VALUES (?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, dichVu.getMaDichVu());
			preparedStatement.setString(2, dichVu.getTenDichVu());
			preparedStatement.setInt(3, dichVu.getSoLuong());
			preparedStatement.setString(4, dichVu.getDonViTinh());
			preparedStatement.setString(5, dichVu.getLoaiDichVu());
			preparedStatement.setDouble(6, dichVu.getGiaMua());
			preparedStatement.setBoolean(7, dichVu.getDaNgungKinhDoanh());
			res = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res > 0;
	}

	/**
	 * Xóa dịch vụ
	 * 
	 * @param maDichVu
	 * @return
	 */
	public boolean xoaDichVu(String maDichVu) {
		int res = 0;
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE DichVu SET daNgungKinhDoanh = 1 WHERE maDichVu  = ?");
			preparedStatement.setString(1, maDichVu);
			res = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res > 0;
	}

	public List<String> getAllLoaiDichVu() {
//		SELECT DISTINCT [loaiDichVu] FROM [dbo].[DichVu]
		List<String> list = new ArrayList<String>();
		
		try {
			ResultSet resultSet = ConnectDB.getConnection().createStatement()
				.executeQuery("SELECT DISTINCT [loaiDichVu] FROM [dbo].[DichVu]");
			
			while (resultSet.next())
				list.add(resultSet.getString(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
