package pl.lodz.uni.math.app.server;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class MyHsqlDBServerTest {

	protected static MyHsqlDBServer myHsqlDBServer = new MyHsqlDBServer();

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
			connection = myHsqlDBServer.getConnection();
			assertFalse(connection.isClosed());
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error ocurred while making conneciton. Message: " + e.getMessage());
		}
	}
	
}
