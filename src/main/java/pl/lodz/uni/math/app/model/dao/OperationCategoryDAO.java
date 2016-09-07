package pl.lodz.uni.math.app.model.dao;

import pl.lodz.uni.math.app.model.domain.OperationCategory;

public interface OperationCategoryDAO {

	/*
	 * method adds new Operation category to database, based on operationCategory parameter.
	 */
	public boolean addOperationCategory(OperationCategory operationCategory);
	/*
	 * method gets the operationCategory, based on id parameter.
	 */
	public OperationCategory getOperationCategory(int id);
	/*
	 * method gets the operationCategory, based on name parameter.
	 */
	public OperationCategory getOperationCategory(String name);
	/*
	 * method removes operationCategory from database, based on operationCategory parameter.
	 */
	public boolean removeOperationCategory(OperationCategory operationCategory);
	/*
	 * method Update row in database, with id equivalent to operationCategory parameter.
	 */
	public boolean updateOperationCategory(OperationCategory operationCategory);
}
