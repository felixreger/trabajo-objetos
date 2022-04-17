package com.trabajofinal.servicios.database.dao_jdbc;

import com.trabajofinal.modelo.Catedra;
import com.trabajofinal.servicios.database.dao_jdbc.conn.DaoDbConeccion;

import java.util.ArrayList;
import java.util.List;


import java.sql.SQLException;

public class CatedraDao extends DaoDbConeccion<Catedra, String> {

    private static CatedraDao catedraDao = null;

	private CatedraDao(){}

	public static CatedraDao getInstance(){
		if (catedraDao == null)
			catedraDao = new CatedraDao();
		return catedraDao;
	}

    @Override
    public List<Catedra> getAll() throws SQLException {
        
        List<Catedra> catedras = new ArrayList<>();

		String queryString = "SELECT * FROM catedras";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		resultSet = ptmt.executeQuery();

		while (resultSet.next()) {
			catedras.add(new Catedra(resultSet.getString("caid"), resultSet.getString("caurl")));
		}

		if (resultSet != null)
			resultSet.close();
		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

		return catedras;
    }

	@Override
	public Catedra get(String id) throws SQLException {

		Catedra catedra = new Catedra();

		String queryString = "SELECT * FROM catedras WHERE caid = ?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, id);
		resultSet = ptmt.executeQuery();

		if (resultSet.next()) {
			catedra.setNombre(resultSet.getString("caid"));
			catedra.setPaginaWeb(resultSet.getString("caurl"));
		}

		if (resultSet != null)
			resultSet.close();
		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

		return catedra;
	}

	@Override
	public void update(Catedra elem) throws SQLException {

		String queryString = "UPDATE catedras SET caid=?, caurl=? WHERE caid=?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, elem.getNombre());
		ptmt.setString(2, elem.getUrlPaginaWeb());
		ptmt.setString(3, elem.getNombre());
		ptmt.executeUpdate();

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
	}

	@Override
	public void delete(String id) throws SQLException {

		String queryString = "DELETE FROM catedras WHERE caid=?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, id);
		ptmt.executeUpdate();

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
	}

	@Override
	public void add(Catedra elem) throws SQLException {

		String queryString = "INSERT INTO catedras(caid, caurl) VALUES(?,?)";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);

		ptmt.setString(1, elem.getNombre());
		ptmt.setString(2, elem.getUrlPaginaWeb());
		ptmt.executeUpdate();

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

	}

	@Override
	public boolean exist(String id) throws SQLException {

		String queryString = "SELECT caid FROM catedras WHERE caid = ?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, id);
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
}
