package pl.lodz.uni.math.app.model.services;

import pl.lodz.uni.math.app.model.dao.CategoryDAO;
import pl.lodz.uni.math.app.model.dao.DataSourceType;
import pl.lodz.uni.math.app.model.dao.OperationDAO;
import pl.lodz.uni.math.app.model.dao.WalletDAO;
import pl.lodz.uni.math.app.model.dao.factory.CategoryDAOFactory;
import pl.lodz.uni.math.app.model.dao.factory.OperationDAOFactory;
import pl.lodz.uni.math.app.model.dao.factory.WalletDAOFactory;

public class DAOFacade {

	public static final DataSourceType DATA_SOURCE_TYPE = DataSourceType.DATABASE;
	
	private CategoryDAOFactory categoryDAOFactory = null;
	
	private WalletDAOFactory walletDAOFactory = null;
	
	private OperationDAOFactory operationDAOFactory = null;
	
	public DAOFacade() {
		this.categoryDAOFactory = new CategoryDAOFactory();
		this.walletDAOFactory  = new WalletDAOFactory();
		this.operationDAOFactory = new OperationDAOFactory(this.categoryDAOFactory, this.walletDAOFactory);
	}
	
	public CategoryDAO getCategoryDAO(DataSourceType dataSourceType){
		return categoryDAOFactory.getCategoryDAO(dataSourceType);
	}
	
	public WalletDAO getWalletDAO(DataSourceType dataSourceType) {
		return walletDAOFactory.getWalletDAO(dataSourceType);
	}
	
	public OperationDAO getOperationDAO(DataSourceType dataSourceType) {
		return operationDAOFactory.getOperatioDAO(dataSourceType);
	}
}
