package com.trabajofinal.servicios.database.dao_jdbc.elementos;

import com.trabajofinal.modelo.Elemento;
import com.trabajofinal.servicios.database.conexion.Conexion;
import com.trabajofinal.servicios.database.dao_jdbc.IDao;

import java.sql.ResultSet;
import java.util.List;
import java.sql.SQLException;


public abstract class ElementoDao extends Conexion implements IDao<Elemento, String> {

    @Override
    public abstract List<Elemento> getAll() throws SQLException;

    @Override
    public abstract Elemento get(String id) throws SQLException;

    @Override
    public abstract void update(Elemento elem) throws SQLException;

    @Override
    public void delete(String pathElemento) throws SQLException {
		String queryString = "DELETE FROM elementos WHERE elpath=?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, pathElemento);
		ptmt.executeUpdate();
		System.out.println("Elemento eliminado exitosamente");

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
	}

	@Override
	public boolean exist(String path) throws SQLException {
		String queryString = "SELECT elnombre, elpath FROM elementos WHERE elpath = ?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, path);
		resultSet = ptmt.executeQuery();

		boolean resultado = resultSet.next();

		if (resultSet != null)
			resultSet.close();
		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

		return resultado;
	}

	protected String getPath(ResultSet resultSet) throws SQLException {
		String tmp = resultSet.getString("elpath");
		String[] path = tmp.split(":");

		StringBuilder resultado = new StringBuilder();
		for (int i = 1; i < path.length -  1; i++) {
			resultado.append(":");
			resultado.append(path[i]);
		}

		return resultado.toString();
	}
}
