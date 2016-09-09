package pl.lodz.uni.math.app.model.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import pl.lodz.uni.math.app.server.MyHsqlDBServer;
import pl.lodz.uni.math.app.server.TestUtils;

public abstract class DAOTest {

	protected static MyHsqlDBServer myHsqlDBServer = new MyHsqlDBServer();

	protected static Connection connection = null;
	
	@BeforeClass
	public static void beforeClass() {
		try {
			myHsqlDBServer.start();
			connection = myHsqlDBServer.getConnection();
		} catch (SQLException e) {
			System.out.println("Error ocurred while getting a connection to database. Message: " + e.getMessage());
		}
	}

	@Before
	public void beforeTest() {
		createTables();
	}

	/*
	 * the sequence of method invocation in this method is very important.
	 */
	private void createTables() {
		TestUtils.executeSQLQuerry(connection, TestUtils.CREATE_CATEGORY_TABLE_STRING);
		TestUtils.executeSQLQuerry(connection, TestUtils.CREATE_WALLET_TABLE_STRING);
		TestUtils.executeSQLQuerry(connection, TestUtils.CREATE_OPERATION_TABLE_STRING);
	}
	
	@After
	public void afterTest() {
		dropTables();
	}

	private void dropTables() {
		TestUtils.executeSQLQuerry(connection, TestUtils.DROP_TABLE + TestUtils.OPERATION);
		TestUtils.executeSQLQuerry(connection, TestUtils.DROP_TABLE + TestUtils.CATEGORY);
		TestUtils.executeSQLQuerry(connection, TestUtils.DROP_TABLE + TestUtils.WALLET);
	}
	
	@AfterClass
	public static void afterClass() {
		try {
			connection.close();
			myHsqlDBServer.stop();
		} catch (SQLException e) {
			System.out.println("Error ocurred while closing the connection with database. Message" + e.getMessage());
		}
		myHsqlDBServer.stop();
	}
}
