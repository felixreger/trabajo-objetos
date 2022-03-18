package com.trabajofinal.servicios.database.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conector {

    String driverClassName = "org.postgresql.Driver";

	String connectionUrl = "jdbc:postgresql://localhost:5432/postgres";
	String dbUser = "postgres";
	String dbPwd = "root";

	/*
	String connectionUrl = "jdbc:postgresql://ec2-18-210-191-5.compute-1.amazonaws.com:5432/d79sq5cldkpff1?sslmode=require";
	String dbUser = "bdycrwycanybkz";
	String dbPwd = "a68578572591fb6eccd90f4c711aabd61caf1b936c8941db3390ea52e4d3ec70";
	*/

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
