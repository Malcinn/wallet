package pl.lodz.uni.math.app.model.dao;

import pl.lodz.uni.math.app.model.domain.OperationCategory;

public interface OperationCategoryDAO {

	public boolean addOperationCategory(OperationCategory operationCategory);

	public OperationCategory getOperationCategory(int id);

	public boolean removeOperationCategory(OperationCategory operationCategory);

	public OperationCategory updateOperationCategory(OperationCategory operationCategory);
}
