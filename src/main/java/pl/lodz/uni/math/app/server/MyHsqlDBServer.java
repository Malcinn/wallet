package pl.lodz.uni.math.app.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl.AclFormatException;

public class MyHsqlDBServer extends Server{

	public MyHsqlDBServer(final String database, final String dbname, final int portNumber) {
		super();
		HsqlProperties properties = new HsqlProperties();
		properties.setProperty("server.database.0", database);
		properties.setProperty("server.dbname.0", dbname);
		properties.setProperty("server.silent", false);
		properties.setProperty("server.trace", true);
		properties.setProperty("server.port", portNumber);
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
	
	public Connection getConnection(String dbname, String user, String password, int portNumber) throws SQLException {
		return  DriverManager.getConnection("jdbc:hsqldb:hsql://127.0.0.1:" + portNumber + "/" + dbname, user, password);
	}
	
}
