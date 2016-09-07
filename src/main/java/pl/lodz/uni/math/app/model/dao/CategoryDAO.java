package pl.lodz.uni.math.app.model.dao;

import pl.lodz.uni.math.app.model.domain.Category;

public interface CategoryDAO {

	/*
	 * method adds new Category to database, based on Category parameter.
	 */
	public boolean addCategory(Category category);
	/*
	 * method gets the Category, based on id parameter.
	 */
	public Category getCategory(int id);
	/*
	 * method gets the Category, based on name parameter.
	 */
	public Category getCategory(String name);
	/*
	 * method removes Category from database, based on Category parameter.
	 */
	public boolean removeCategory(Category category);
	/*
	 * method Update row in database, with id equivalent to Category parameter.
	 */
	public boolean updateCategory(Category category);
}
