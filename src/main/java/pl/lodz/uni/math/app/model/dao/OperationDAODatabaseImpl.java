package pl.lodz.uni.math.app.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.lodz.uni.math.app.model.domain.Operation;
import pl.lodz.uni.math.app.model.domain.OperationType;

public class OperationDAODatabaseImpl implements OperationDAO {

	private static final Logger log = LogManager.getLogger(OperationDAODatabaseImpl.class);

	private static final String OPERATION = "Operation";

	private Connection connection = null;

	private CategoryDAO categoryDAO = null;

	private WalletDAO walletDAO = null;

	public OperationDAODatabaseImpl(Connection connection, CategoryDAO categoryDAO, WalletDAO walletDAO) {
		this.connection = connection;
		this.categoryDAO = categoryDAO;
		this.walletDAO = walletDAO;
		DAOUtils.setValueOfAutoCommitModeOnConnection(this.connection, false);
	}

	@Override
	public boolean addOperation(Operation operation) {
		if (operation != null) {
			String sql = "INSERT INTO " + OPERATION + " VALUES (NULL, ?, ?, ?, ?, ?, ?)";
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, operation.getType().toString());
				statement.setDate(2, operation.getDate());
				statement.setString(3, operation.getDescription());
				statement.setBigDecimal(4, operation.getAmount());
				statement.setInt(5, ((operation.getCategory() != null) ? operation.getCategory().getId() : null));
				statement.setInt(6, ((operation.getWallet() != null) ? operation.getWallet().getId() : null));
				statement.executeUpdate();
				statement.close();
				connection.commit();
				return true;
			} catch (SQLException e) {
				log.error("Error ocurred in addOperation method. Message: " + e.getMessage());
			}
		}
		return false;
	}

	@Override
	public Operation getOperation(int id) {
		String sql = "SELECT * FROM " + OPERATION + " WHERE id=?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			Operation operation = null;
			if (resultSet.next()) {
				operation = new Operation(resultSet.getInt(1),
						(resultSet.getString(2).equals(OperationType.IN.toString()) ? OperationType.IN
								: OperationType.OUT),
						resultSet.getDate(3), resultSet.getString(4), resultSet.getBigDecimal(5),
						walletDAO.getWallet(resultSet.getInt(6)), categoryDAO.getCategory(resultSet.getInt(7)));
			}
			resultSet.close();
			statement.close();
			connection.commit();
			return operation;
		} catch (SQLException e) {
			log.error("Error ocurred in getOperation method. Message" + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean removeOperation(Operation operation) {
		if (operation != null) {
			String sql = "DELETE FROM " + OPERATION + " WHERE id=?";
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, operation.getId());
				int result = statement.executeUpdate();
				statement.close();
				connection.commit();
				return ((result == 0) ? false : true);
			} catch (SQLException e) {
				log.error("Error ocurred in removeOperation method. Message: " + e.getMessage());
			}
		}
		return false;
	}

	@Override
	public boolean updateOperation(Operation operation) {
		if (operation != null) {
			String sql = "UPDATE " + OPERATION
					+ " SET type=?, date=?, description=?, amount=?, category_id=?, wallet_id=? " + "WHERE id=?";
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, operation.getType().toString());
				statement.setDate(2, operation.getDate());
				statement.setString(3, operation.getDescription());
				statement.setBigDecimal(4, operation.getAmount());
				statement.setInt(5, operation.getCategory().getId());
				statement.setInt(6, operation.getWallet().getId());
				statement.setInt(7, operation.getId());
				int result = statement.executeUpdate();
				System.out.println(statement.toString());
				statement.close();
				connection.commit();
				return (result == 0) ? false : true;
			} catch (SQLException e) {
				log.error("Error ocurred in updateOperation method. Message: " + e.getMessage());
			}
		}
		return false;
	}

}
