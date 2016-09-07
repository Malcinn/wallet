package pl.lodz.uni.math.app.model.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.AfterClass;
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
			TestUtils.createCategoryTable(connection);
			TestUtils.createWalletTable(connection);
			TestUtils.createOperationTable(connection);
		} catch (SQLException e) {
			System.out.println("Error ocurred while getting a connection to database. Message: " + e.getMessage());
		}
	}

	@AfterClass
	public static void afterClass() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error ocurred while closing the connection with database. Message" + e.getMessage());
		}
		myHsqlDBServer.stop();
	}
}
