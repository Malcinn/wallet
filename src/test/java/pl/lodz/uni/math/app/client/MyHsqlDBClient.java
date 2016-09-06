package pl.lodz.uni.math.app.client;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MyHsqlDBClient {

	protected static final String CREATE_CATEGORY_TABLE_STRING = "CREATE TABLE Category (" + "id INT NOT NULL,"
			+ "name VARCHAR(255) UNIQUE NOT NULL," + "PRIMARY KEY(id))";

	protected static final String CREATE_WALLET_TABLE_STRING = "CREATE TABLE Wallet (" + "id INT NOT NULL,"
			+ "name VARCHAR(255) UNIQUE NOT NULL," + "PRIMARY KEY(id))";

	protected static final String CREATE_OPERATION_TABLE_STRING = "CREATE TABLE Operation (" + "id INT NOT NULL,"
			+ "type VARCHAR(3) NOT NULL," + "date DATE NOT NULL, " + "description VARCHAR(2048),"
			+ "amount DECIMAL NOT NULL," + "category_id INT," + "wallet_id INT NOT NULL," + "PRIMARY KEY(id),"
			+ "FOREIGN KEY(category_id)REFERENCES Category(id)," + "FOREIGN KEY(wallet_id)REFERENCES Wallet(id))";

	private Connection connection = null;
	
	public MyHsqlDBClient(Connection connection) {
		this.connection = connection;
	}
	 
	public void createCategoryTable() {
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
	
	public void createWalletTable() {
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
	
	public void createOperationTable() {
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
