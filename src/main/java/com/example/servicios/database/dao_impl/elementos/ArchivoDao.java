package com.example.servicios.database.dao_impl.elementos;

import com.example.modelo.Archivo;
import com.example.modelo.Catedra;
import com.example.modelo.Elemento;
import com.example.modelo.Usuario;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

public class ArchivoDao extends ElementoDao{

    private static ArchivoDao archivoDao = null;

    private ArchivoDao(){
    }

    public static ArchivoDao getInstance(){
        if (archivoDao == null)
            archivoDao = new ArchivoDao();
            
        return archivoDao;
    }

    @Override
    public List<Elemento> getAll() throws SQLException {

        List<Elemento> archivos = new ArrayList<>();

		String queryString = "SELECT * FROM elementos e JOIN catedras c on e.elcaid = c.caid JOIN usuarios u on e.elpropietario = u.usmail where e.eltipo != ?";
		connection = getConnection();

		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, "carpeta");
		resultSet = ptmt.executeQuery();

		while (resultSet.next()) {
			archivos.add(
				new Archivo(
					resultSet.getString("elnombre"),
					resultSet.getString("eltipo"),
					resultSet.getInt("eltamanio"),
					LocalDate.parse(resultSet.getString("elfechamodificacion")),
					LocalDate.parse(resultSet.getString("elfechacreacion")),
					new Catedra(
						resultSet.getString("caid"),
						resultSet.getString("caurl")
					),
					new Usuario(
						resultSet.getString("usmail"),
						resultSet.getString("usnombre"),
						resultSet.getInt("uspuntaje")
					),
					resultSet.getString("elelempadre")
				));
		}

		if (resultSet != null)
			resultSet.close();
		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

		return archivos;
    }

    @Override
    public Elemento get(String id) throws SQLException {

        Elemento archivo = new Archivo();

		String queryString = "SELECT * FROM elementos e JOIN catedras c on e.elcaid = c.caid WHERE elnombre = ?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, id);
		resultSet = ptmt.executeQuery();

		if (resultSet.next()) {
			archivo.setNombre(resultSet.getString("elnombre"));
			archivo.setTipo(resultSet.getString("eltipo"));
			archivo.setTamanio(resultSet.getInt("eltamanio"));
			archivo.setFechaCreacion(Instant.ofEpochMilli(resultSet.getDate("elfechamodificacion").getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
			archivo.setFechaCreacion(Instant.ofEpochMilli(resultSet.getDate("elfechacreacion").getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
			archivo.setCatedra(new Catedra(resultSet.getString("elcaid"), resultSet.getString("caurl"))); // revisar atributo
		}

		if (resultSet != null)
			resultSet.close();
		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

		return archivo;
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