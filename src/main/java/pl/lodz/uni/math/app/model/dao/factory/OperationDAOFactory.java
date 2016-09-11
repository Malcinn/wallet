package pl.lodz.uni.math.app.model.dao.factory;

import pl.lodz.uni.math.app.model.dao.DataSourceType;
import pl.lodz.uni.math.app.model.dao.OperationDAO;
import pl.lodz.uni.math.app.model.dao.OperationDAODatabaseImpl;
import pl.lodz.uni.math.app.model.services.DatabaseConnectionManager;

public class OperationDAOFactory {

	private CategoryDAOFactory categoryDAOFactory = null;

	private WalletDAOFactory walletDAOFactory = null;

	public OperationDAOFactory(CategoryDAOFactory categoryDAOFactory, WalletDAOFactory walletDAOFactory) {
		this.categoryDAOFactory = categoryDAOFactory;
		this.walletDAOFactory = walletDAOFactory;
	}
	public OperationDAO getOperatioDAO(DataSourceType dataSourceType) {
		if (dataSourceType.equals(DataSourceType.DATABASE))
			return new OperationDAODatabaseImpl(DatabaseConnectionManager.getConnection(),
					categoryDAOFactory.getCategoryDAO(DataSourceType.DATABASE),
					walletDAOFactory.getWalletDAO(DataSourceType.DATABASE));
		return null;
	}
}
