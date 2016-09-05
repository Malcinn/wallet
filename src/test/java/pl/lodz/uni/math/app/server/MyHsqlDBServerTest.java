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
	
	private Connection connection;
	
	
	@BeforeClass
	public static void beforeClass(){
		myHsqlDBServer.start();
	}
	
	@AfterClass
	public static void AfterClass() {
		myHsqlDBServer.stop();
	}
	
	@Test
	public void getConnectionTestTrue() {
		try {
			//connection = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "sa", "");
			connection = myHsqlDBServer.getConnection(DB_NAME, USER, PASSWORD, PORT_NUMBER);
			assertFalse(connection.isClosed());
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error ocurred while making conneciton. Message: " + e.getMessage());
		}
	}
	
	@Test
	public void createTableTestTrue() {
		String createTable = "CREATE TABLE test (id INT NOT NULL);";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = myHsqlDBServer.getConnection(DB_NAME, USER, PASSWORD, PORT_NUMBER);
			preparedStatement = connection.prepareStatement(createTable);
			int rowNUmber = preparedStatement.executeUpdate();
			assertEquals(0, rowNUmber);
		} catch (SQLException e) {
			System.out.println("Error ocurred while creatbin connection or preparing statement. Message: " + e.getMessage());
		}
		
	}
	
	@Test
	public void createTableWithDataTrue() {
		String insertIntoTable = "INSERT INTO test VALUES(1), (2), (3);";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = myHsqlDBServer.getConnection(DB_NAME, USER, PASSWORD, PORT_NUMBER);
			preparedStatement = connection.prepareStatement(insertIntoTable);
			int rowNumbers = preparedStatement.executeUpdate();
			assertEquals(3, rowNumbers);
		} catch (SQLException e) {
			System.out.println("Error ocurred while creatbin connection or preparing statement. Message: " + e.getMessage());
		}
		
	}
}
