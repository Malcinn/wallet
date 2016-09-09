package pl.lodz.uni.math.app.model.dao;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.lodz.uni.math.app.model.domain.Wallet;

public class WalletDAOImplTest extends DAOTest{

	private static final String WALLET = "Wallet";
	
	private WalletDAO walletDAO = new WalletDAOImpl(connection);
	
	@Before
	public void beforeTest() {
		super.beforeTest();
	}
	
	@After
	public void afterTest() {
		super.afterTest();
	};
	
	@Test
	public void addWalletMethodAddingSingleWallet() {
		Wallet wallet = new Wallet("testWallet");
		boolean result = walletDAO.addWallet(wallet);
		assertTrue(result);
	}
	
	@Test
	public void addWalletMethodAddingTheSameWallet() {
		Wallet wallet = new Wallet("testWallet");
		walletDAO.addWallet(wallet);
		boolean result = walletDAO.addWallet(wallet);
		assertFalse(result);
	}
	
	@Test
	public void getWalletMethodGettingExistingWalletTest() {
		Wallet wallet = new Wallet("testWallet");
		walletDAO.addWallet(wallet);
		Wallet result = walletDAO.getWallet("testWallet");
		assertTrue((result instanceof Wallet) ? true : false);
	}
	
	@Test
	public void getWalletMethodWhenWalletIsNUll() {
		Wallet result = walletDAO.getWallet(null);
		assertFalse((result instanceof Wallet) ? true : false);
	}
	
	@Test
	public void removeWalletMethodRemoveExistingWalletTest() {
		Wallet wallet = new Wallet("testWallet");
		walletDAO.addWallet(wallet);
		Wallet w =  walletDAO.getWallet("testWallet");
		boolean result = walletDAO.removeWallet(w);
		assertTrue(result);
	}

	@Test
	public void removeWalletMethodRemoveNotExistingWalletTest() {
		Wallet wallet = new Wallet("testWallet");
		boolean result = walletDAO.removeWallet(wallet);
		assertFalse(result);
	}
	
	@Test
	public void removeWalletMethodRemoveWhenWalletIsNullTest() {
		boolean result = walletDAO.removeWallet(null);
		assertFalse(result);
	}
	
	@Test
	public void updateWalletMethodTest() {
		walletDAO.addWallet(new Wallet("testWallet"));
		Wallet wallet = walletDAO.getWallet("testWallet");
		wallet.setName("testWalletUpdated");
		walletDAO.updateWallet(wallet);
		Wallet result = walletDAO.getWallet(wallet.getId());
		assertTrue(wallet.getName().equals(result.getName()));
	}
	
	@Test
	public void updateCategoryMethodWhenCategoryIsNullTest () {
		boolean result = walletDAO.updateWallet(null);
		assertFalse(result);
	}
}
