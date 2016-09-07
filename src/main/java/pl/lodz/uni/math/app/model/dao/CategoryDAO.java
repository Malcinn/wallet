package pl.lodz.uni.math.app.model.dao;

import pl.lodz.uni.math.app.model.domain.Category;

public interface CategoryDAO {

	/*
	 * method adds new Category to database, base on category object fields.
	 */
	public boolean addCategory(Category category);
	/*
	 * method returns the Category, base on id parameter.
	 */
	public Category getCategory(int id);
	/*
	 * method returns the Category, base on name parameter.
	 */
	public Category getCategory(String name);
	/*
	 * method removes Category from database, base on category object fields.
	 */
	public boolean removeCategory(Category category);
	/*
	 * method Update row in database, base on category object fields.
	 */
	public boolean updateCategory(Category category);
}
