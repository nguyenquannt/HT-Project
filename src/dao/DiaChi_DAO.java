package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Phuong;
import entity.Quan;
import entity.Tinh;

public class DiaChi_DAO extends DAO {
	/**
	 * Get phường theo quận
	 * 
	 * @param quan
	 * @return
	 */
	public List<Phuong> getPhuong(Quan quan) {
		List<Phuong> list = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection().prepareStatement("SELECT * FROM Phuong WHERE quan = ?");
			preparedStatement.setString(1, quan.getId());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
				list.add(new Phuong(resultSet.getString(1), resultSet.getString(2), quan));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return list;
	}

	/**
	 * Get phường
	 * 
	 * @param quan
	 * @param phuong
	 * @return
	 */
	public Phuong getPhuong(Quan quan, Phuong phuong) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM Phuong WHERE id = ? and quan = ?");
			preparedStatement.setString(1, phuong.getId());
			preparedStatement.setString(2, quan.getId());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return new Phuong(phuong.getId(), resultSet.getString(2), quan);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return null;
	}

	/**
	 * Get phường theo tên phường
	 * 
	 * @param quan
	 * @param phuong
	 * @return
	 */
	public Phuong getPhuong(Quan quan, String phuong) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM Phuong WHERE phuong = ? and quan = ?");
			preparedStatement.setString(1, phuong);
			preparedStatement.setString(2, quan.getId());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return new Phuong(resultSet.getString(1), resultSet.getString(2), quan);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return null;
	}

	/**
	 * Get quận theo tỉnh
	 * 
	 * @param tinh
	 * @return
	 */
	public List<Quan> getQuan(Tinh tinh) {
		List<Quan> list = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection().prepareStatement("SELECT * FROM Quan WHERE tinh = ?");
			preparedStatement.setString(1, tinh.getId());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
				list.add(new Quan(resultSet.getString(1), resultSet.getString(2), tinh));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return list;
	}

	/**
	 * Get quận
	 * 
	 * @param tinh
	 * @param quan
	 * @return
	 */
	public Quan getQuan(Tinh tinh, Quan quan) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM Quan WHERE id = ? and tinh = ?");
			preparedStatement.setString(1, quan.getId());
			preparedStatement.setString(2, tinh.getId());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return new Quan(quan.getId(), resultSet.getString(2), tinh);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return null;
	}

	/**
	 * Get quận theo tên quận
	 * 
	 * @param tinh
	 * @param quan
	 * @return
	 */
	public Quan getQuan(Tinh tinh, String quan) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM Quan WHERE quan = ? and tinh = ?");
			preparedStatement.setString(1, quan);
			preparedStatement.setString(2, tinh.getId());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return new Quan(resultSet.getString(1), resultSet.getString(2), tinh);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return null;
	}

	/**
	 * Get tất cả các tỉnh
	 * 
	 * @return
	 */
	public List<Tinh> getTinh() {
		List<Tinh> list = new ArrayList<>();
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = ConnectDB.getConnection().createStatement();
			resultSet = statement.executeQuery("SELECT * FROM Tinh");

			while (resultSet.next())
				list.add(new Tinh(resultSet.getString(1), resultSet.getString(2)));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(statement, resultSet);
		}

		return list;
	}

	/**
	 * Get tỉnh theo tên tỉnh
	 * 
	 * @param tinh
	 * @return
	 */
	public Tinh getTinh(String tinh) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection().prepareStatement("SELECT * FROM Tinh WHERE tinh = ?");
			preparedStatement.setString(1, tinh);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return new Tinh(resultSet.getString(1), resultSet.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return null;
	}

	/**
	 * Get tỉnh
	 * 
	 * @param tinh
	 * @return
	 */
	public Tinh getTinh(Tinh tinh) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = ConnectDB.getConnection().prepareStatement("SELECT * FROM Tinh WHERE id = ?");
			preparedStatement.setString(1, tinh.getId());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				return new Tinh(tinh.getId(), resultSet.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement, resultSet);
		}

		return null;
	}
}
