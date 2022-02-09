package servicios.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    String driverClassName = "org.postgresql.Driver";
	String connectionUrl = "jdbc:postgresql://localhost:5432/postgres"; 
	String dbUser = "postgres";
	String dbPwd = "root";

	private static Conexion connectionFactory = null;

	private Conexion() {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		return conn;
	}
    
	public static Conexion getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new Conexion();
		}
		return connectionFactory;
	}


}
