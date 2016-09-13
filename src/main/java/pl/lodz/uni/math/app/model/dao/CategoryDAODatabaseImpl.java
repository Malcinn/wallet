package pl.lodz.uni.math.app.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.lodz.uni.math.app.model.domain.Category;

public class CategoryDAODatabaseImpl implements CategoryDAO {

	private static final Logger log = LogManager.getLogger(CategoryDAODatabaseImpl.class);

	private static final String CATEGORY = "Category";

	private static final String CATEGORY_ID = "category_id";

	private static final String OPERATION = "Operation";

	private Connection connection = null;

	public CategoryDAODatabaseImpl(Connection connection) {
		this.connection = connection;
		DAOUtils.setValueOfAutoCommitModeOnConnection(this.connection, false);
	}

	@Override
	public boolean addCategory(Category category) {
		if (category != null) {
			if (!checkIfCategoryExistInDatabase(category.getName())) {
				String sql = "INSERT INTO " + CATEGORY + " VALUES (NULL, ?)";
				PreparedStatement statement = null;
				try {
					statement = connection.prepareStatement(sql);
					statement.setString(1, category.getName());
					statement.executeUpdate();
					connection.commit();
					statement.close();
					return true;
				} catch (SQLException e) {
					log.error("Error occured in addCategory method. Message: " + e.getMessage());
				}
			}
		}
		return false;
	}

	@Override
	public Category getCategory(int id) {
		String sql = "SELECT * FROM " + CATEGORY + " WHERE id=" + id;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			Category category = new Category(resultSet.getInt(1), resultSet.getString(2));
			resultSet.close();
			connection.commit();
			statement.close();
			return category;
		} catch (SQLException e) {
			log.error("Error ocurred in getCategory method. Message: " + e.getMessage());
		}

		return null;
	}

	@Override
	public Category getCategory(String name) {
		if (name != null) {
			String sql = "SELECT * FROM " + CATEGORY + " WHERE name LIKE (?)";
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, name);
				ResultSet resultSet = statement.executeQuery();
				resultSet.next();
				Category category = new Category(resultSet.getInt(1), resultSet.getString(2));
				resultSet.close();
				connection.commit();
				statement.close();
				return category;
			} catch (SQLException e) {
				log.error("Error ocurred in getCategory method. Message: " + e.getMessage());
			}
		}
		return null;
	}

	public boolean checkIfCategoryExistInDatabase(String name) {
		String sql = "SELECT * FROM " + CATEGORY;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getString(2).equalsIgnoreCase(name))
					return true;
			}
		} catch (SQLException e) {
			log.error("Error ocurred in checkIfCategoryExistInDatabase method. Message: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean removeCategory(Category category) {
		if (category != null) {
			String sql = "DELETE FROM " + CATEGORY + " WHERE id=?";
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(sql);
				statement.setInt(1, category.getId());
				int result = statement.executeUpdate();
				statement.close();
				connection.commit();
				return (result == 0) ? false : true;
			} catch (SQLException e) {
				log.error("Error ocurred in removeCategory method. Message: " + e.getMessage());
			}
		}
		return false;
	}

	@Override
	public boolean updateCategory(Category category) {
		if (category != null) {
			if (removeOperationFromOperationTableWhereCategoryIs(category)) {
				String sql = "UPDATE " + CATEGORY + " SET name=? WHERE id=?";
				PreparedStatement statement = null;
				try {
					statement = connection.prepareStatement(sql);
					statement.setString(1, category.getName());
					statement.setInt(2, category.getId());
					int result = statement.executeUpdate();
					connection.commit();
					statement.close();
					return (result == 0) ? false : true;
				} catch (SQLException e) {
					log.error("Error ocurred in updateCategory method. Message" + e.getMessage());
				}
			}
		}
		return false;
	}

	@Override
	public List<Category> getCategories() {
		List<Category> returnList = new ArrayList<>();
		String sql = "SELECT * FROM " + CATEGORY;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				returnList.add(new Category(resultSet.getInt(1), resultSet.getString(2)));
			}
		} catch (SQLException e) {
			log.error("Error ocurred in getCategories method. Message: " + e.getMessage());
		}
		return returnList;
	}

	/*
	 * this method removes dependencies from Operation table.
	 */
	public boolean removeOperationFromOperationTableWhereCategoryIs(Category category) {
		if (category != null) {
			String sql = "DELETE FROM " + OPERATION + " WHERE " + CATEGORY_ID + "=?";
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(sql);
				statement.setInt(1, category.getId());
				statement.executeUpdate();
				statement.close();
				connection.commit();
				return true;
			} catch (SQLException e) {
				log.error("Error ocurred while removing operations from Operation table, base on category_id. Message: "
						+ e.getMessage());
			}
		}
		return false;
	}
}
