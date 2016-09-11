package pl.lodz.uni.math.app.model.dao.factory;

import pl.lodz.uni.math.app.model.dao.CategoryDAO;
import pl.lodz.uni.math.app.model.dao.CategoryDAODatabaseImpl;
import pl.lodz.uni.math.app.model.dao.DataSourceType;
import pl.lodz.uni.math.app.model.services.DatabaseConnectionManager;

public class CategoryDAOFactory {

	public CategoryDAO getCategoryDAO(DataSourceType dataSourceType) {
		if (dataSourceType.equals(DataSourceType.DATABASE))
			return new CategoryDAODatabaseImpl(DatabaseConnectionManager.getConnection());
		return null;
	}
}
