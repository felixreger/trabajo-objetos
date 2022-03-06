package com.example.servicios.database.dao_impl.elementos;

import com.example.modelo.Elemento;
import com.example.servicios.database.Dao;
import com.example.servicios.database.IDao;

import java.util.List;


import java.sql.Date;
import java.sql.SQLException;


public abstract class ElementoDao extends Dao implements IDao<Elemento, String> {

    @Override
    public abstract List<Elemento> getAll() throws SQLException;

    @Override
    public abstract Elemento get(String id) throws SQLException;

    @Override
    public void update(Elemento elem) throws SQLException {

		String queryString = "UPDATE elementos SET(eltamanio, eltipo, elfechacreacion, elfechamodificacion) VALUES=(?,?,?,?) WHERE elnombre=?";

		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setInt(1, elem.getTamanio());
		ptmt.setString(2, elem.getTipo());
		ptmt.setDate(3, Date.valueOf(elem.getFechaCreacion()));
		ptmt.setDate(4, Date.valueOf(elem.getFechaModificacion()));
		ptmt.setString(5, elem.getNombre());

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
    }

    @Override
    public void delete(String id) throws SQLException {

		String queryString = "DELETE FROM elementos WHERE elnombre=?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, id);
		ptmt.executeUpdate();
		System.out.println("Data deleted Successfully");

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
	}
    
    @Override
    public void add(Elemento elem) throws SQLException {
		String queryString = "INSERT INTO elementos (elnombre, eltamanio, eltipo, elfechamodificacion, elfechacreacion, elcaid, elpropietario, elelempadre)"
							+ "VALUES(?,?,?,?,?,?,?,?)";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);

		ptmt.setString(1, elem.getNombre());
		ptmt.setInt(2, elem.getTamanio());
		ptmt.setString(3, elem.getTipo());
		ptmt.setDate(4, Date.valueOf(elem.getFechaModificacion()));
		ptmt.setDate(5, Date.valueOf(elem.getFechaCreacion()));
		ptmt.setString(6, elem.getCatedra().getNombre());
		ptmt.setString(7, elem.getPropietario());
		ptmt.setString(8, elem.getPadre());

		ptmt.executeUpdate();

		System.out.println("Data Added Successfully");

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
    }

	@Override
	public boolean exist(String id) throws SQLException {
		String queryString = "SELECT elcaid FROM elementos WHERE elnombre = ?";
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
