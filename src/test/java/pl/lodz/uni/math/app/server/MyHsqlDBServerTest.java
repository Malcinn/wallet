package pl.lodz.uni.math.app.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class MyHsqlDBServerTest {

	private static final String DATABASE = "mem:testdb";

	private static final String DB_NAME = "testdb";

	private static final int PORT_NUMBER = 9001;

	private static final String USER = "SA";

	private static final String PASSWORD = "";

	private static MyHsqlDBServer myHsqlDBServer = new MyHsqlDBServer(DATABASE, DB_NAME, PORT_NUMBER);

	private static final String CREATE_CATEGORY_TABLE_STRING = "CREATE TABLE Category (" + "id INT NOT NULL,"
			+ "name VARCHAR(255) NOT NULL," + "PRIMARY KEY(id))";

	private static final String CREATE_WALLET_TABLE_STRING = "CREATE TABLE Wallet (" + "id INT NOT NULL,"
			+ "name VARCHAR(255) NOT NULL," + "PRIMARY KEY(id))";

	private static final String CREATE_OPERATION_TABLE_STRING = "CREATE TABLE Operation (" + "id INT NOT NULL,"
			+ "type VARCHAR(3) NOT NULL," + "date DATE NOT NULL, " + "description VARCHAR(2048),"
			+ "amount DECIMAL NOT NULL," + "category_id INT," + "wallet_id INT NOT NULL," + "PRIMARY KEY(id),"
			+ "FOREIGN KEY(category_id)REFERENCES Category(id)," + "FOREIGN KEY(wallet_id)REFERENCES Wallet(id))";

	private Connection connection;

	@BeforeClass
	public static void beforeClass() {
		myHsqlDBServer.start();
	}

	@AfterClass
	public static void AfterClass() {
		myHsqlDBServer.stop();
	}

	@Test
	public void getConnectionTestTrue() {
		try {
			connection = myHsqlDBServer.getConnection(DB_NAME, USER, PASSWORD, PORT_NUMBER);
			assertFalse(connection.isClosed());
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error ocurred while making conneciton. Message: " + e.getMessage());
		}
	}
	
	@Test 
	public void createCategoryTableTest() {
		PreparedStatement statement = null;
		try {
			connection = myHsqlDBServer.getConnection(DB_NAME, USER, PASSWORD, PORT_NUMBER);
			statement = connection.prepareStatement(CREATE_CATEGORY_TABLE_STRING);
			int rowCount = statement.executeUpdate();
			assertEquals(0, rowCount);
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error ocurred while making conneciton. Message: " + e.getMessage());
		}
	}
	
	@Test 
	public void createWalletTableTest() {
		PreparedStatement statement = null;
		try {
			connection = myHsqlDBServer.getConnection(DB_NAME, USER, PASSWORD, PORT_NUMBER);
			statement = connection.prepareStatement(CREATE_WALLET_TABLE_STRING);
			int rowCount = statement.executeUpdate();
			assertEquals(0, rowCount);
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error ocurred while making conneciton. Message: " + e.getMessage());
		}
	}
	
	@Test 
	public void createOperationTableTest() {
		PreparedStatement statement = null;
		try {
			connection = myHsqlDBServer.getConnection(DB_NAME, USER, PASSWORD, PORT_NUMBER);
			statement = connection.prepareStatement(CREATE_WALLET_TABLE_STRING);
			int rowCount = statement.executeUpdate();
			assertEquals(0, rowCount);
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error ocurred while making conneciton. Message: " + e.getMessage());
		}
	}
}
