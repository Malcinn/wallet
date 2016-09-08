package pl.lodz.uni.math.app.model.dao;

import pl.lodz.uni.math.app.model.domain.Operation;

public interface OperationDAO {
	
	public boolean addOperation(Operation operation);
	
	public Operation getOperation(int id);
	
	public boolean removeOperation(Operation operation);
	
	public boolean updateOperation(Operation operation);
}
