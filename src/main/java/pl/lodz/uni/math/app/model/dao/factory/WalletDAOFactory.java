package pl.lodz.uni.math.app.model.dao.factory;

import pl.lodz.uni.math.app.model.dao.DataSourceType;
import pl.lodz.uni.math.app.model.dao.WalletDAO;
import pl.lodz.uni.math.app.model.dao.WalletDAODatabaseImpl;
import pl.lodz.uni.math.app.model.services.DatabaseConnectionManager;

public class WalletDAOFactory {

	public WalletDAO getWalletDAO(DataSourceType dataSourceType) {
		if (dataSourceType.equals(DataSourceType.DATABASE))
			return new WalletDAODatabaseImpl(DatabaseConnectionManager.getConnection());
		return null;
	}
}
