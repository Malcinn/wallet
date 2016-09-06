package pl.lodz.uni.math.app.model.dao;

import static org.junit.Assert.assertTrue;
import static pl.lodz.uni.math.app.server.MyHsqlDBServerUtils.DATABASE;
import static pl.lodz.uni.math.app.server.MyHsqlDBServerUtils.DB_NAME;
import static pl.lodz.uni.math.app.server.MyHsqlDBServerUtils.PASSWORD;
import static pl.lodz.uni.math.app.server.MyHsqlDBServerUtils.PORT_NUMBER;
import static pl.lodz.uni.math.app.server.MyHsqlDBServerUtils.USER;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import pl.lodz.uni.math.app.client.MyHsqlDBClient;
import pl.lodz.uni.math.app.model.domain.OperationCategory;
import pl.lodz.uni.math.app.server.MyHsqlDBServer;
import pl.lodz.uni.math.app.server.MyHsqlDBServerTest;

public class OperationCategoryDAOImplTest extends MyHsqlDBServerTest{

	private static MyHsqlDBServer myHsqlDBServer = new MyHsqlDBServer(DATABASE, DB_NAME, PORT_NUMBER);
			
	private static MyHsqlDBClient myHsqlDBClient ;
	
	private static OperationCategoryDAO operationCategoryDAO;
	
	@BeforeClass
	public static void beforeClass() {
		try {
			myHsqlDBServer.start();
			myHsqlDBClient = new MyHsqlDBClient(myHsqlDBServer.getConnection(DB_NAME, USER, PASSWORD, PORT_NUMBER));
			myHsqlDBClient.createCategoryTable();
			myHsqlDBClient.createWalletTable();
			myHsqlDBClient.createOperationTable();
			operationCategoryDAO = new OperationCategoryDAOImpl(myHsqlDBServer.getConnection(DB_NAME, USER, PASSWORD, PORT_NUMBER));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void afterClass() {
		myHsqlDBServer.stop();
	}
	
	
	@Test
	public void addOperationCategoryTest(){
		OperationCategory operationCategory = new OperationCategory(1, "lol");
		boolean result = operationCategoryDAO.addOperationCategory(operationCategory);
		assertTrue(result);
	}
}
