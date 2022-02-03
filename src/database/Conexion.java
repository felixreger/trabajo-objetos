package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    String driverClassName = "com.mysql.jdbc.Driver";
	String connectionUrl = "jdbc:mysql://localhost:3306/student"; // TODO cambiar por la url de la base de datos
	String dbUser = "root";
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
