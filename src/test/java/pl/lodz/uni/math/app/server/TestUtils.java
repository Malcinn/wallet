package pl.lodz.uni.math.app.server;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestUtils {
	
	public static final String CREATE_CATEGORY_TABLE_STRING = "CREATE TABLE Category (" + "id INT IDENTITY NOT NULL,"
			+ "name VARCHAR(255) NOT NULL," + "PRIMARY KEY(id))";

	public static final String CREATE_WALLET_TABLE_STRING = "CREATE TABLE Wallet (" + "id INT IDENTITY NOT NULL,"
			+ "name VARCHAR(255) NOT NULL," + "PRIMARY KEY(id))";

	public static final String CREATE_OPERATION_TABLE_STRING = "CREATE TABLE Operation (" + "id INT IDENTITY NOT NULL,"
			+ "type VARCHAR(3) NOT NULL," + "date DATE NOT NULL, " + "description VARCHAR(2048),"
			+ "amount DECIMAL NOT NULL," + "category_id INT," + "wallet_id INT NOT NULL," + "PRIMARY KEY(id),"
			+ "FOREIGN KEY(category_id)REFERENCES Category(id)," + "FOREIGN KEY(wallet_id)REFERENCES Wallet(id))";
	
	public static void createCategoryTable(Connection connection) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(CREATE_CATEGORY_TABLE_STRING);
			int rowCount = statement.executeUpdate();
			assertEquals(0, rowCount);
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Error ocurred while making conneciton. Message: " + e.getMessage());
		}
	}
	
	public static void createWalletTable(Connection connection) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(CREATE_WALLET_TABLE_STRING);
			int rowCount = statement.executeUpdate();
			assertEquals(0, rowCount);
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Error ocurred while making conneciton. Message: " + e.getMessage());
		}
	}
	
	public static void createOperationTable(Connection connection) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(CREATE_OPERATION_TABLE_STRING);
			int rowCount = statement.executeUpdate();
			assertEquals(0, rowCount);
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Error ocurred while making conneciton. Message: " + e.getMessage());
		}
	}
}
