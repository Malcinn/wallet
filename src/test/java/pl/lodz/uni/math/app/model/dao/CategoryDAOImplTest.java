package pl.lodz.uni.math.app.model.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import pl.lodz.uni.math.app.model.domain.Category;
import pl.lodz.uni.math.app.server.MyHsqlDBServer;
import pl.lodz.uni.math.app.server.TestUtils;

public class CategoryDAOImplTest {

	private static MyHsqlDBServer myHsqlDBServer = new MyHsqlDBServer();

	private static CategoryDAO categoryDAO;

	private static Connection connection = null;

	@BeforeClass
	public static void beforeClass() {
		try {
			myHsqlDBServer.start();
			connection = myHsqlDBServer.getConnection();
			TestUtils.createCategoryTable(connection);
			TestUtils.createWalletTable(connection);
			TestUtils.createOperationTable(connection);
			categoryDAO = new CategoryDAOImpl(connection);
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
	public void addCategoryMethodAddSingleCategoryTest() {
		clearCategoryTable();
		Category category = new Category("testCategory");
		boolean result = categoryDAO.addCategory(category);
		assertTrue(result);
	}

	@Test
	public void addCategoryMethodAddingTheSameCategoriesTest() {
		clearCategoryTable();
		Category category = new Category("testCategory");
		categoryDAO.addCategory(category);
		boolean result = categoryDAO.addCategory(category);
		assertFalse(result);
	}

	/*
	 * Attention if you want to change the sequence of tests of method
	 * getCategory..., you have to change also an id of
	 * Category. This happens because the id column has an IDENTITY, it
	 * means that there is an sequence on database which sets, and increment id
	 * number for that column. If you delete an row the sequence is not back
	 * with the counter. So if you add an row it's id will be incremented
	 * normally.
	 */
	@Test
	public void getCategoryMethodGetExistingCategoryTest() {
		clearCategoryTable();
		Category category = new Category("testCategory");
		categoryDAO.addCategory(category);
		Category result = categoryDAO.getCategory("testCategory");
		assertTrue((result instanceof Category) ? true : false);
	}
	
	@Test
	public void getCategoryMethodWhenCategoryNameIsNullTest() {
		Category result = categoryDAO.getCategory(null);
		assertFalse((result instanceof Category) ? true : false);
	}

	@Test
	public void getCategoryMethodGetNotExistingCategoryTest() {
		clearCategoryTable();
		Category result = categoryDAO.getCategory(0);
		assertFalse((result instanceof Category) ? true : false);
	}

	@Test
	public void removeCategoryMethodRemoveExistingCategoryTest() {
		clearCategoryTable();
		Category category = new Category("testCategory");
		categoryDAO.addCategory(category);
		Category o = categoryDAO.getCategory("testCategory");
		boolean result = categoryDAO.removeCategory(o);
		assertTrue(result);
	}

	@Test
	public void removeCategoryMethodRemoveNotExistingCategoryTest() {
		clearCategoryTable();
		Category o = new Category(0, "testCategory");
		boolean result = categoryDAO.removeCategory(o);
		assertFalse(result);
	}
	
	@Test
	public void removeCategoryMethodRemoveWhenCategoryIsNullTest() {
		clearCategoryTable();
		boolean result = categoryDAO.removeCategory(null);
		assertFalse(result);
	}

	@Test
	public void updateCategoryMethodTest() {
		clearCategoryTable();
		categoryDAO.addCategory(new Category("testCategory"));
		Category category = categoryDAO.getCategory("testCategory");
		category.setName("testCategoryUpdated");
		categoryDAO.updateCategory(category);
		Category result = categoryDAO.getCategory(category.getId());
		assertTrue(category.getName().equals(result.getName()));
	}
	
	@Test
	public void updateCategoryMethodWhenCategoryIsNullTest () {
		clearCategoryTable();
		boolean result = categoryDAO.updateCategory(null);
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
