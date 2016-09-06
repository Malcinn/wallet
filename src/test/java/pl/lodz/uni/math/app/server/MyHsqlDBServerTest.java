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

import static pl.lodz.uni.math.app.server.MyHsqlDBServerUtils.*;

public class MyHsqlDBServerTest {

	protected static MyHsqlDBServer myHsqlDBServer = new MyHsqlDBServer(DATABASE, DB_NAME, PORT_NUMBER);

	protected Connection connection;

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
	
}
