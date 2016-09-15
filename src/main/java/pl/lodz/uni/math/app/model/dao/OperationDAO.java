package pl.lodz.uni.math.app.model.dao;

import java.util.List;

import pl.lodz.uni.math.app.model.domain.Operation;
import pl.lodz.uni.math.app.model.domain.QueryParameters;

public interface OperationDAO {
	/*
	 * method adds new Operation to database, base on category object fields.
	 */
	public boolean addOperation(Operation operation);
	/*
	 * method return Operation, base on id parameter.
	 */
	public Operation getOperation(int id);
	/*
	 * method removes Operation from data base, base on operation object parameters.
	 */
	public boolean removeOperation(Operation operation);
	/*
	 * method update row in database, base on operation object fields.
	 */
	public boolean updateOperation(Operation operation);
	/*
	 * method return list of Operations.
	 */
	public List<Operation> getOperations();
	/*
	 * method return list of Operations base on parameters i queryParameters object.
	 */
	public List<Operation> getOperations(QueryParameters queryParameters);
}
