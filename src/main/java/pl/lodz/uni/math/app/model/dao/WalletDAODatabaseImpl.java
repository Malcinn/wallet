package pl.lodz.uni.math.app.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.lodz.uni.math.app.model.domain.Category;
import pl.lodz.uni.math.app.model.domain.Wallet;

public class WalletDAODatabaseImpl implements WalletDAO {

	private static final Logger log = LogManager.getLogger(WalletDAODatabaseImpl.class);

	private static final String WALLET = "Wallet";

	private static final String WALLET_ID = "wallet_id";

	private Connection connection = null;

	public WalletDAODatabaseImpl(Connection connection) {
		this.connection = connection;
		DAOUtils.setValueOfAutoCommitModeOnConnection(connection, false);
	}

	@Override
	public boolean addWallet(Wallet wallet) {
		if (wallet != null) {
			if (!(checkIfWalletExistInDatabase(wallet.getName()))) {
				String sql = "INSERT INTO " + WALLET + " VALUES (NULL, ?)";
				PreparedStatement statement = null;
				try {
					statement = connection.prepareStatement(sql);
					statement.setString(1, wallet.getName());
					int result = statement.executeUpdate();
					statement.close();
					connection.commit();
					return (result == 0) ? false : true;
				} catch (SQLException e) {
					log.error("Error ocurred in addWallet Method. Message: " + e.getMessage());
				}
			}
		}
		return false;
	}

	@Override
	public Wallet getWallet(int id) {
		String sql = "SELECT * FROM " + WALLET + " WHERE id=" + id;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			Wallet wallet = new Wallet(resultSet.getInt(1), resultSet.getString(2));
			resultSet.close();
			statement.close();
			connection.commit();
			return wallet;
		} catch (SQLException e) {
			log.error("Error ocurred in getCategory method. Message: " + e.getMessage());
		}
		return null;
	}

	@Override
	public Wallet getWallet(String name) {
		String sql = "SELECT * FROM " + WALLET + " WHERE name LIKE(?)";
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			Wallet wallet = new Wallet(resultSet.getInt(1), resultSet.getString(2));
			resultSet.close();
			statement.close();
			connection.commit();
			return wallet;
		} catch (SQLException e) {
			log.error("Error ocurred in getWallet method. Message: " + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean removeWallet(Wallet wallet) {
		if (wallet != null) {
			if (removeOperationFromOperationTableWhereWalletIs(wallet)) {
				String sql = "DELETE FROM " + WALLET + " WHERE id=?";
				PreparedStatement statement = null;
				try {
					statement = connection.prepareStatement(sql);
					statement.setInt(1, wallet.getId());
					int result = statement.executeUpdate();
					statement.close();
					connection.commit();
					return (result == 0) ? false : true;
				} catch (SQLException e) {
					System.out.println("Error ocurred in removeWallet method. Message: " + e.getMessage());
				}
			}
		}
		return false;
	}

	@Override
	public boolean updateWallet(Wallet wallet) {
		if (wallet != null) {
			String sql = "UPDATE " + WALLET + " SET name=? WHERE id=?";
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, wallet.getName());
				statement.setInt(2, wallet.getId());
				int result = statement.executeUpdate();
				connection.commit();
				statement.close();
				return (result == 0) ? false : true;
			} catch (SQLException e) {
				log.error("Error ocurred in updateCategory method. Message" + e.getMessage());
			}
		}
		return false;
	}

	private boolean checkIfWalletExistInDatabase(String name) {
		String sql = "SELECT * FROM " + WALLET;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getString(2).equalsIgnoreCase(name))
					return true;
			}
		} catch (SQLException e) {
			log.error("Error ocurred in checkIfWalletExistInDatabase method. Message: " + e.getMessage());
		}
		return false;
	}

	@Override
	public List<Wallet> getWallets() {
		List<Wallet> returnList = new ArrayList<>();
		String sql = "SELECT * FROM " + WALLET;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				returnList.add(new Wallet(resultSet.getInt(1), resultSet.getString(2)));
			}
		} catch (SQLException e) {
			log.error("Error ocurred in getWallets method. Message: " + e.getMessage());
		}
		return returnList;
	}

	/*
	 * this method removes dependencies from Operation table.
	 */
	private boolean removeOperationFromOperationTableWhereWalletIs(Wallet wallet) {
		if (wallet != null) {
			String sql = "DELETE FROM " + WALLET + " WHERE " + WALLET_ID + "=?";
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(sql);
				statement.setInt(1, wallet.getId());
				statement.executeUpdate();
				statement.close();
				connection.commit();
				return true;
			} catch (SQLException e) {
				log.error("Error ocurred while removing operations from Operation table, base on wallet_id. Message: "
						+ e.getMessage());
			}
		}
		return false;
	}

}
