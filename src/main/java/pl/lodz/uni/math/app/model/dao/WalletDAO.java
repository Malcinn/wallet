package pl.lodz.uni.math.app.model.dao;

import pl.lodz.uni.math.app.model.domain.Wallet;

public interface WalletDAO {

	/*
	 * method add new wallet to database, base on wallet object fields.
	 */
	public boolean addWallet(Wallet wallet);
	/*
	 * methods returns wallet, base on id parameter.
	 */
	public Wallet getWallet(int id);
	/*
	 * methods return wallet, base on name parameter.
	 */
	public Wallet getWallet(String name);
	/*
	 * method remove wallet from database, base on wallet object fields.
	 */
	public boolean removeWallet(Wallet wallet);
	/*
	 * method update row in database, base on wallet object fields.
	 */
	public boolean updateWallet(Wallet wallet);
}
