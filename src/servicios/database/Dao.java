package servicios.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class Dao{ 
    protected Connection connection = null;
	protected PreparedStatement ptmt = null;
	protected Statement stmt = null;
	protected ResultSet resultSet = null;

    protected Connection getConnection() throws SQLException {
		Connection conn;
		conn = Conexion.getInstance().getConnection();
		return conn;
	}
}