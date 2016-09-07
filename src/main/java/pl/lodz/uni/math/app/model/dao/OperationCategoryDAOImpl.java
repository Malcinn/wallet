package pl.lodz.uni.math.app.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.StatementEvent;

import pl.lodz.uni.math.app.model.domain.OperationCategory;

public class OperationCategoryDAOImpl implements OperationCategoryDAO {

	private static final String OPERATION_CATEGORY = "Category";

	private Connection connection = null;

	public OperationCategoryDAOImpl(Connection connection) {
		this.connection = connection;
		setAutoCommitModeOnConnection(this.connection);
	}

	private void setAutoCommitModeOnConnection(Connection connection) {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println(
					"Error ocurred while, setting autocomit mode to false on the connection property. Message: "
							+ e.getMessage());
		}
	}

	@Override
	public boolean addOperationCategory(OperationCategory operationCategory) {
		if (operationCategory != null) {
			if (!checkIfOperationCategoryExistInDatabase(operationCategory.getName())) {
				String sql = "INSERT INTO " + OPERATION_CATEGORY + " VALUES(NULL, ?)";
				PreparedStatement statement = null;
				try {
					statement = connection.prepareStatement(sql);
					statement.setString(1, operationCategory.getName());
					statement.executeUpdate();
					connection.commit();
					statement.close();
					return true;
				} catch (SQLException e) {
					System.out.println("Error occured in addOperationCategory method. Message: " + e.getMessage());
				}
			}
		}
		return false;
	}

	@Override
	public OperationCategory getOperationCategory(int id) {
		String sql = "SELECT * FROM " + OPERATION_CATEGORY + " WHERE id=" + id;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			OperationCategory operationCategory = new OperationCategory(resultSet.getInt(1), resultSet.getString(2));
			resultSet.close();
			connection.commit();
			statement.close();
			return operationCategory;
		} catch (SQLException e) {
			System.out.println("Error ocurred in getOperationCategory method. Message: " + e.getMessage());
		}

		return null;
	}

	@Override
	public OperationCategory getOperationCategory(String name) {
		if (name != null) {
			String sql = "SELECT * FROM " + OPERATION_CATEGORY + " WHERE name LIKE ('" + name + "')";
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();
				resultSet.next();
				OperationCategory operationCategory = new OperationCategory(resultSet.getInt(1),
						resultSet.getString(2));
				resultSet.close();
				connection.commit();
				statement.close();
				return operationCategory;
			} catch (SQLException e) {
				System.out.println("Error ocurred in getOperationCategory method. Message: " + e.getMessage());
			}
		}
		return null;
	}

	public boolean checkIfOperationCategoryExistInDatabase(String name) {
		String sql = "SELECT * FROM " + OPERATION_CATEGORY;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getString(2).equalsIgnoreCase(name))
					return true;
			}
		} catch (SQLException e) {
			System.out.println(
					"Error ocurred in checkIfOperationCategoryExistInDatabase method. Message: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean removeOperationCategory(OperationCategory operationCategory) {
		if (operationCategory != null) {
			String sql = "DELETE FROM " + OPERATION_CATEGORY + " WHERE id=" + operationCategory.getId();
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(sql);
				int result = statement.executeUpdate();
				statement.close();
				connection.commit();
				return (result == 0) ? false : true;
			} catch (SQLException e) {
				System.out.println("Error ocurred in removeOperationCategory method. Message: " + e.getMessage());
			}
		}
		return false;
	}

	@Override
	public boolean updateOperationCategory(OperationCategory operationCategory) {
		if (operationCategory != null) {
			String sql = "UPDATE " + OPERATION_CATEGORY + " SET name=? WHERE id=?";
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, operationCategory.getName());
				statement.setInt(2, operationCategory.getId());
				int result = statement.executeUpdate();
				connection.commit();
				statement.close();
				return (result == 0) ? false : true;
			} catch (SQLException e) {
				System.out.println("Error ocurred in updateOperationCategory method. Message" + e.getMessage());
			}
		}
		return false;
	}

}
