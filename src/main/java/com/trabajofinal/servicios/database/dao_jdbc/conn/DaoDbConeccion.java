package com.trabajofinal.servicios.database.dao_jdbc.conn;

import com.trabajofinal.servicios.database.conexion.Conector;
import com.trabajofinal.servicios.database.dao_jdbc.IDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DaoDbConeccion<T, E> implements IDao<T,E> {

    protected Connection connection =   null;
    protected PreparedStatement ptmt = null;
    protected ResultSet resultSet = null;

    protected Connection getConnection() throws SQLException {
        Connection conn;
        conn = Conector.getInstance().getConnection();
        return conn;
    }

}
