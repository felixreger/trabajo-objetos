package com.trabajofinal.servicios.database.conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
    protected Connection connection = null;
	protected PreparedStatement ptmt = null;
	protected ResultSet resultSet = null;

    protected Connection getConnection() throws SQLException {
		Connection conn;
		conn = Conector.getInstance().getConnection();
		return conn;
	}
}