package com.trabajofinal.servicios.database.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conector {

    String driverClassName = "org.postgresql.Driver";

	String connectionUrl = "";
	String dbUser = "";
	String dbPwd = "";

	private static Conector connectionFactory = null;

	private Conector() {
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
    
	public static Conector getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new Conector();
		}
		return connectionFactory;
	}
}
