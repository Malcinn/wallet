package pl.lodz.uni.math.app.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.StatementEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.lodz.uni.math.app.model.domain.Operation;
import pl.lodz.uni.math.app.model.domain.OperationType;
import pl.lodz.uni.math.app.model.domain.QueryParameters;

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
						categoryDAO.getCategory(resultSet.getInt(7)), walletDAO.getWallet(resultSet.getInt(6)));
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

	@Override
	public List<Operation> getOperations() {
		String sql = "SELECT * FROM " + OPERATION;
		List<Operation> resultList = new ArrayList<>();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				resultList.add(new Operation(resultSet.getInt(1),
						(resultSet.getString(2).equals(OperationType.IN.toString()) ? OperationType.IN
								: OperationType.OUT),
						resultSet.getDate(3), resultSet.getString(4), resultSet.getBigDecimal(5),
						categoryDAO.getCategory(resultSet.getInt(6)), walletDAO.getWallet(resultSet.getInt(7))));
			}
		} catch (SQLException e) {
			log.error("Error ocured in getOperaitons method. Message: " + e.getMessage());
		}
		return resultList;
	}

	@Override
	public List<Operation> getOperations(QueryParameters queryParameters) {
		String sql = getSqlQuery(queryParameters).toString();
		List<Operation> resultList = new ArrayList<>();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				resultList.add(new Operation(resultSet.getInt(1),
						(resultSet.getString(2).equals(OperationType.IN.toString()) ? OperationType.IN
								: OperationType.OUT),
						resultSet.getDate(3), resultSet.getString(4), resultSet.getBigDecimal(5),
						categoryDAO.getCategory(resultSet.getInt(6)), walletDAO.getWallet(resultSet.getInt(7))));
			}
		} catch (SQLException e) {
			log.error("Error ocured in getOperaitons method. Message: " + e.getMessage());
		}
		return resultList;
	}

	private StringBuilder getSqlQuery(QueryParameters queryParameters) {
		ArrayList<StringBuilder> sqlWhereClausure = new ArrayList<>();
		sqlWhereClausure.add(new StringBuilder("SELECT * FROM " + OPERATION));
		processDateQueryParameter(queryParameters, sqlWhereClausure);
		processTypeQueryParameter(queryParameters, sqlWhereClausure);
		processAmountQueryParameter(queryParameters, sqlWhereClausure);
		processCategoryQueryParameter(queryParameters, sqlWhereClausure);
		processWalletQueryParameter(queryParameters, sqlWhereClausure);

		appendWhereClausure(sqlWhereClausure);
		appendAndConnector(sqlWhereClausure);
		StringBuilder stringBuilder = new StringBuilder();
		for (StringBuilder string : sqlWhereClausure) {
			stringBuilder.append(string);
		}
		return stringBuilder;
	}

	private void appendAndConnector(ArrayList<StringBuilder> sqlWhereClausure) {
		for (int i = 1; i < sqlWhereClausure.size() - 1; i++) {
			sqlWhereClausure.get(i).append(" AND");
		}
	}

	private void appendWhereClausure(ArrayList<StringBuilder> sqlWhereClausure) {
		if (sqlWhereClausure.size() >= 2) {
			sqlWhereClausure.get(0).append(" WHERE");
		}
	}

	private void processWalletQueryParameter(QueryParameters queryParameters,
			ArrayList<StringBuilder> sqlWhereClausure) {
		if (queryParameters.getWallet() != null)
			sqlWhereClausure.add(new StringBuilder(" wallet_id =" + queryParameters.getWallet().getId()));
	}

	private void processCategoryQueryParameter(QueryParameters queryParameters,
			ArrayList<StringBuilder> sqlWhereClausure) {
		if (queryParameters.getCategory() != null)
			sqlWhereClausure.add(new StringBuilder(" category_id =" + queryParameters.getCategory().getId()));
	}

	private void processAmountQueryParameter(QueryParameters queryParameters,
			ArrayList<StringBuilder> sqlWhereClausure) {
		if (queryParameters.getAmountFrom() != null && queryParameters.getAmountTo() != null) {
			if (queryParameters.getAmountTo().compareTo(queryParameters.getAmountFrom()) == 1) {
				sqlWhereClausure.add(new StringBuilder(" amount >=" + queryParameters.getAmountFrom() + " AND amount <="
						+ queryParameters.getAmountTo()));
			} else if (queryParameters.getAmountTo().compareTo(queryParameters.getAmountFrom()) == 0) {
				sqlWhereClausure.add(new StringBuilder(" amount =" + queryParameters.getAmountFrom()));
			}

		} else {
			if (queryParameters.getAmountFrom() != null)
				sqlWhereClausure.add(new StringBuilder(" amount >=" + queryParameters.getAmountFrom()));
			if (queryParameters.getAmountTo() != null)
				sqlWhereClausure.add(new StringBuilder(" amount <=" + queryParameters.getAmountTo()));
		}
	}

	private void processTypeQueryParameter(QueryParameters queryParameters,
			ArrayList<StringBuilder> sqlWhereClausure) {
		if (queryParameters.getType() != null)
			sqlWhereClausure.add(new StringBuilder(" type='" + queryParameters.getType().toString() + "'"));
	}

	private void processDateQueryParameter(QueryParameters queryParameters, ArrayList<StringBuilder> sqlWhereClausure) {
		if (queryParameters.getFrom() != null && queryParameters.getTo() != null) {
			if (queryParameters.getFrom().before(queryParameters.getTo())) {
				sqlWhereClausure.add(new StringBuilder(
						" date >='" + queryParameters.getFrom() + "' AND date <='" + queryParameters.getTo() + "'"));
			} else if (queryParameters.getFrom().equals(queryParameters.getTo())) {
				sqlWhereClausure.add(new StringBuilder(" date ='" + queryParameters.getFrom()));
			}
		} else {
			if (queryParameters.getFrom() != null)
				sqlWhereClausure.add(new StringBuilder(" date >='" + queryParameters.getFrom() + "'"));
			if (queryParameters.getTo() != null)
				sqlWhereClausure.add(new StringBuilder(" date <='" + queryParameters.getTo() + "'"));
		}
	}

}
