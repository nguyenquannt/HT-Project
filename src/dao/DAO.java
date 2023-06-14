package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connectDB.ConnectDB;

public class DAO {
	protected void close(PreparedStatement preparedStatement) {
		try {
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void close(PreparedStatement preparedStatement, ResultSet resultSet) {
		try {
			resultSet.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void close(Statement statement) {
		try {
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void close(Statement statement, ResultSet resultSet) {
		try {
			resultSet.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean commit() throws SQLException {
		Connection connection = ConnectDB.getConnection();
		if (connection.getAutoCommit())
			connection.setAutoCommit(false);
		connection.commit();
		connection.setAutoCommit(true);
		return true;
	}

	protected boolean rollback() throws SQLException {
		Connection connection = ConnectDB.getConnection();
		if (connection.getAutoCommit())
			connection.setAutoCommit(false);
		connection.rollback();
		connection.setAutoCommit(true);
		return false;
	}
}
