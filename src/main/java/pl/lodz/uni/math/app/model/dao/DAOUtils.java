package pl.lodz.uni.math.app.model.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOUtils {
	
	public static void setValueOfAutoCommitModeOnConnection(Connection connection, boolean value) {
		try {
			connection.setAutoCommit(value);
		} catch (SQLException e) {
			System.out.println(
					"Error ocurred while, setting autocomit mode to false on the connection property. Message: "
							+ e.getMessage());
		}
	}
}
