package pl.lodz.uni.math.app.model.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.lodz.uni.math.app.model.domain.Category;
import pl.lodz.uni.math.app.model.domain.Operation;
import pl.lodz.uni.math.app.model.domain.OperationType;
import pl.lodz.uni.math.app.model.domain.Wallet;

public class OperationDAOImplTest extends DAOTest {

	private CategoryDAO categoryDAO = new CategoryDAODatabaseImpl(connection);

	private WalletDAO walletDAO = new WalletDAODatabaseImpl(connection);

	private OperationDAO operationDAO = new OperationDAODatabaseImpl(connection, categoryDAO, walletDAO);

	@Before
	public void beforeTest() {
		super.beforeTest();
	}

	@After
	public void afterTest() {
		super.afterTest();
	}

	@Test
	public void addOperationMedthodTest() {
		Category category = new Category("testCategory");
		categoryDAO.addCategory(category);
		category = categoryDAO.getCategory(category.getName());
		Wallet wallet = new Wallet("testWallet");
		walletDAO.addWallet(wallet);
		wallet = walletDAO.getWallet(wallet.getName());
		Operation operation = new Operation(OperationType.IN, new Date(new java.util.Date().getTime()),
				new String("testDescription"), new BigDecimal("54.2"), category, wallet);
		boolean result = operationDAO.addOperation(operation);
		assertTrue(result);
	}

	@Test
	public void addOperationMethodAddNullValue() {
		Operation operation = null;
		boolean result = operationDAO.addOperation(operation);
		assertFalse(result);
	}

	@Test
	public void getOperationMethodGetExistingOperationTest() {
		Category category = new Category("testCategory");
		categoryDAO.addCategory(category);
		category = categoryDAO.getCategory(category.getName());
		Wallet wallet = new Wallet("testWallet");
		walletDAO.addWallet(wallet);
		wallet = walletDAO.getWallet(wallet.getName());
		Operation operation = new Operation(OperationType.IN, new Date(new java.util.Date().getTime()),
				new String("testDescription"), new BigDecimal("54.2"), category, wallet);
		operationDAO.addOperation(operation);

		operation = operationDAO.getOperation(0);
		assertTrue((operation != null) && (operation instanceof Operation));
	}

	@Test
	public void getOperationMethodGetNotExistingOperationTest() {
		Operation operation = operationDAO.getOperation(0);
		assertFalse(((operation != null) && (operation instanceof Operation)) ? true : false);
	}

	@Test
	public void removeOperationMethodRemoveExistingOperationTest() {
		Category category = new Category("testCategory");
		categoryDAO.addCategory(category);
		category = categoryDAO.getCategory(category.getName());
		Wallet wallet = new Wallet("testWallet");
		walletDAO.addWallet(wallet);
		wallet = walletDAO.getWallet(wallet.getName());
		Operation operation = new Operation(OperationType.IN, new Date(new java.util.Date().getTime()),
				new String("testDescription"), new BigDecimal("54.2"), category, wallet);
		operationDAO.addOperation(operation);

		operation = operationDAO.getOperation(0);
		boolean result = operationDAO.removeOperation(operation);
		assertTrue(result);
	}

	@Test
	public void removeOperationMethodRemoveNotExistingOperationTest() {
		boolean result = operationDAO.removeOperation(null);
		assertFalse(result);
	}

	@Test
	public void updateOperationMethodUpdateExistingOperationTest() {
		Category category = new Category("testCategory");
		categoryDAO.addCategory(category);
		category = categoryDAO.getCategory(category.getName());
		Wallet wallet = new Wallet("testWallet");
		walletDAO.addWallet(wallet);
		wallet = walletDAO.getWallet(wallet.getName());
		Operation operation = new Operation(OperationType.IN, new Date(new java.util.Date().getTime()),
				new String("testDescription"), new BigDecimal("54.2"), category, wallet);
		operationDAO.addOperation(operation);
		operation.setDescription("testDescription2");

		boolean result = operationDAO.updateOperation(operation);
		assertTrue(result);
	}

	@Test
	public void updateOperationMethodWhenArgumentIsNull() {
		boolean result = operationDAO.updateOperation(null);
		assertFalse(result);
	}

	@Test
	public void getOperationsMethodGetNotEmptyOperationsList() {
		Category category = new Category("testCategory");
		categoryDAO.addCategory(category);
		category = categoryDAO.getCategory(category.getName());
		Wallet wallet = new Wallet("testWallet");
		walletDAO.addWallet(wallet);
		wallet = walletDAO.getWallet(wallet.getName());
		Operation operation = new Operation(OperationType.IN, new Date(new java.util.Date().getTime()),
				new String("testDescription"), new BigDecimal("54.2"), category, wallet);
		operationDAO.addOperation(operation);
		operation = new Operation(OperationType.IN, new Date(new java.util.Date().getTime()),
				new String("testDescription2"), new BigDecimal("2354"), category, wallet);
		operationDAO.addOperation(operation);
		List<Operation> operations = operationDAO.getOperations();
		assertEquals(2, operations.size());
	}

	@Test
	public void getOperationsMethodGetEmptyOperationsList() {
		List<Operation> operations = operationDAO.getOperations();
		assertEquals(0, operations.size());
	}
}
