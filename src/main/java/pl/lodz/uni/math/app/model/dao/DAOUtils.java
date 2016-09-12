package pl.lodz.uni.math.app.model.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DAOUtils {

	private static final Logger log = LogManager.getLogger(DAOUtils.class);

	public static void setValueOfAutoCommitModeOnConnection(Connection connection, boolean value) {
		try {
			connection.setAutoCommit(value);
		} catch (SQLException e) {
			log.error("Error ocurred while, setting autocomit mode to false on the connection property. Message: "
					+ e.getMessage());
		}
	}
}
