package pl.lodz.uni.math.app.model.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.lodz.uni.math.app.model.domain.Category;

public class CategoryDAOImplTest extends DAOTest{
	
	private CategoryDAO categoryDAO = new CategoryDAODatabaseImpl(connection);

	@Before
	public void beforeTest() {
		super.beforeTest();
	}
	
	@After
	public void afterTest() {
		super.afterTest();
	}
	
	@Test
	public void addCategoryMethodAddSingleCategoryTest() {
		Category category = new Category("testCategory");
		boolean result = categoryDAO.addCategory(category);
		assertTrue(result);
	}

	@Test
	public void addCategoryMethodAddingTheSameCategoriesTest() {
		Category category = new Category("testCategory");
		categoryDAO.addCategory(category);
		boolean result = categoryDAO.addCategory(category);
		assertFalse(result);
	}

	@Test
	public void getCategoryMethodGetExistingCategoryTest() {
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
		Category result = categoryDAO.getCategory(0);
		assertFalse((result instanceof Category) ? true : false);
	}

	@Test
	public void removeCategoryMethodRemoveExistingCategoryTest() {
		Category category = new Category("testCategory");
		categoryDAO.addCategory(category);
		Category o = categoryDAO.getCategory("testCategory");
		boolean result = categoryDAO.removeCategory(o);
		assertTrue(result);
	}

	@Test
	public void removeCategoryMethodRemoveNotExistingCategoryTest() {
		Category o = new Category(0, "testCategory");
		boolean result = categoryDAO.removeCategory(o);
		assertFalse(result);
	}
	
	@Test
	public void removeCategoryMethodRemoveWhenCategoryIsNullTest() {
		boolean result = categoryDAO.removeCategory(null);
		assertFalse(result);
	}

	@Test
	public void updateCategoryMethodTest() {
		categoryDAO.addCategory(new Category("testCategory"));
		Category category = categoryDAO.getCategory("testCategory");
		category.setName("testCategoryUpdated");
		categoryDAO.updateCategory(category);
		Category result = categoryDAO.getCategory(category.getId());
		assertTrue(category.getName().equals(result.getName()));
	}
	
	@Test
	public void updateCategoryMethodWhenCategoryIsNullTest () {
		boolean result = categoryDAO.updateCategory(null);
		assertFalse(result);
	}
	
}
