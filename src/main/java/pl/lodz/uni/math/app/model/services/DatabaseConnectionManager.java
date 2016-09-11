package pl.lodz.uni.math.app.model.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
	
	private static String url = "jdbc:hsqldb:hsql://localhost:9001/walletdb";
	
	private static String user = "SA";
	
	private static String password = "";
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		if (connection == null) {
			setConnection(url, user, password);
		}
		return connection;
	}
	
	public static void setConnection(String url, String user, String password) {
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("Error ocured while making a conneciton with database. Message: " + e.getMessage());
		}
	}
	
}
