package pl.lodz.uni.math.app.model.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import pl.lodz.uni.math.app.model.domain.OperationCategory;
import pl.lodz.uni.math.app.server.MyHsqlDBServer;
import pl.lodz.uni.math.app.server.TestUtils;

public class OperationCategoryDAOImplTest {

	private static MyHsqlDBServer myHsqlDBServer = new MyHsqlDBServer();

	private static OperationCategoryDAO operationCategoryDAO;

	private static Connection connection = null;

	@BeforeClass
	public static void beforeClass() {
		try {
			myHsqlDBServer.start();
			connection = myHsqlDBServer.getConnection();
			TestUtils.createCategoryTable(connection);
			TestUtils.createWalletTable(connection);
			TestUtils.createOperationTable(connection);
			operationCategoryDAO = new OperationCategoryDAOImpl(connection);
		} catch (SQLException e) {
			e.printStackTrace();
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

	@Test
	public void addOperationCategoryMethodAddSingleCategoryTest() {
		clearCategoryTable();
		OperationCategory operationCategory = new OperationCategory("testCategory");
		boolean result = operationCategoryDAO.addOperationCategory(operationCategory);
		assertTrue(result);
	}

	@Test
	public void addOperationCategoryMethodAddingTheSameCategoriesTest() {
		clearCategoryTable();
		OperationCategory operationCategory = new OperationCategory("testCategory");
		operationCategoryDAO.addOperationCategory(operationCategory);
		boolean result = operationCategoryDAO.addOperationCategory(operationCategory);
		assertFalse(result);
	}

	/*
	 * Attention if you want to change the sequence of tests of method
	 * getOperationCategor..., you have to change also an id of
	 * OperationCategory. This happens because the id column has an IDENTITY, it
	 * means that there is an sequence on database which sets, and increment id
	 * number for that column. If you delete an row the sequence is not back
	 * with the counter. So if you add an row it's id will be incremented
	 * normally.
	 */
	@Test
	public void getOperationCategoryMethodGetExistingCategoryTest() {
		clearCategoryTable();
		OperationCategory operationCategory = new OperationCategory("testCategory");
		operationCategoryDAO.addOperationCategory(operationCategory);
		OperationCategory result = operationCategoryDAO.getOperationCategory("testCategory");
		assertTrue((result instanceof OperationCategory) ? true : false);
	}
	
	@Test
	public void getOperationCategoryMethodWhenCategoryNameIsNullTest() {
		OperationCategory result = operationCategoryDAO.getOperationCategory(null);
		assertFalse((result instanceof OperationCategory) ? true : false);
	}

	@Test
	public void getOperationCategoryMethodGetNotExistingCategoryTest() {
		clearCategoryTable();
		OperationCategory result = operationCategoryDAO.getOperationCategory(0);
		assertFalse((result instanceof OperationCategory) ? true : false);
	}

	@Test
	public void removeOperationCategoryMethodRemoveExistingCategoryTest() {
		clearCategoryTable();
		OperationCategory operationCategory = new OperationCategory("testCategory");
		operationCategoryDAO.addOperationCategory(operationCategory);
		OperationCategory o = operationCategoryDAO.getOperationCategory("testCategory");
		boolean result = operationCategoryDAO.removeOperationCategory(o);
		assertTrue(result);
	}

	@Test
	public void removeOperationCategoryMethodRemoveNotExistingCategoryTest() {
		clearCategoryTable();
		OperationCategory o = new OperationCategory(0, "testCategory");
		boolean result = operationCategoryDAO.removeOperationCategory(o);
		assertFalse(result);
	}
	
	@Test
	public void removeOperationCategoryMethodRemoveWhenOperationCategoryIsNullTest() {
		clearCategoryTable();
		boolean result = operationCategoryDAO.removeOperationCategory(null);
		assertFalse(result);
	}

	@Test
	public void updateOperationCategoryMethodTest() {
		clearCategoryTable();
		operationCategoryDAO.addOperationCategory(new OperationCategory("testCategory"));
		OperationCategory operationCategory = operationCategoryDAO.getOperationCategory("testCategory");
		operationCategory.setName("testCategoryUpdated");
		operationCategoryDAO.updateOperationCategory(operationCategory);
		OperationCategory result = operationCategoryDAO.getOperationCategory(operationCategory.getId());
		assertTrue(operationCategory.getName().equals(result.getName()));
	}
	
	@Test
	public void updateOperationCategoryMethodWhenOperationCategoryIsNullTest () {
		clearCategoryTable();
		boolean result = operationCategoryDAO.updateOperationCategory(null);
		assertFalse(result);
	}
	
	public void clearCategoryTable() {
		String deleteAllCategoryRows = "DELETE FROM Category";
		try {
			PreparedStatement statement = connection.prepareStatement(deleteAllCategoryRows);
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Error ocurred in clearCategoryTable() method. Message: " + e.getMessage());
		}
	}
}
