package pl.lodz.uni.math.app.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl.AclFormatException;

public class MyHsqlDBServer extends Server{

	public static final String DATABASE = "mem:testdb";

	public static final String DB_NAME = "testdb";

	public static final String USER = "SA";

	public static final String PASSWORD = "";
	
	public static final int PORT_NUMBER = 9001;
	
	public MyHsqlDBServer() {
		super();
		HsqlProperties properties = new HsqlProperties();
		properties.setProperty("server.database.0", DATABASE);
		properties.setProperty("server.dbname.0", DB_NAME);
		properties.setProperty("server.silent", false);
		properties.setProperty("server.trace", true);
		try {
			this.setProperties(properties);
		} catch (IOException e) {
			System.out.println("Error ocurred while reading properties. message: " + e.getMessage());
		} catch (AclFormatException e) {
			System.out.println("Error ocurred while setting up properties. message: " + e.getMessage());
		}
	}
	
	@Override
	public int start() {
		return super.start();
	}
	
	@Override
	public int stop() {
		return super.stop();
	}
	
	public Connection getConnection() throws SQLException {
		return  DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:"+PORT_NUMBER+"/"+DB_NAME, USER, PASSWORD);
	}
	
}
