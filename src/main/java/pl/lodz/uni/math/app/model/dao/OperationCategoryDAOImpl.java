package pl.lodz.uni.math.app.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import pl.lodz.uni.math.app.model.domain.OperationCategory;

public class OperationCategoryDAOImpl implements OperationCategoryDAO{

	private static final String OPERATION_CATEGORY = "Category";
	
	private Connection connection = null;
	
	public OperationCategoryDAOImpl(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public boolean addOperationCategory(OperationCategory operationCategory) {
		String sql = "INSERT INTO "+ OPERATION_CATEGORY + " VALUES(?, ?)";
		PreparedStatement statement;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, operationCategory.getId());
			statement.setString(2, operationCategory.getName());
			statement.executeUpdate();
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public OperationCategory getOperationCategory(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeOperationCategory(OperationCategory operationCategory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OperationCategory updateOperationCategory(OperationCategory operationCategory) {
		// TODO Auto-generated method stub
		return null;
	}

}
