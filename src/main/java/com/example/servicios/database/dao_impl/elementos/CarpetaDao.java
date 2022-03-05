package com.example.servicios.database.dao_impl.elementos;

import com.example.modelo.Carpeta;
import com.example.modelo.Catedra;
import com.example.modelo.Elemento;
import com.example.modelo.Usuario;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;




public class CarpetaDao extends ElementoDao{

    private static CarpetaDao carpetaDao = null;

    private CarpetaDao(){
    }

    public static CarpetaDao getInstance(){
        if (carpetaDao == null)
            carpetaDao = new CarpetaDao();
            
        return carpetaDao;
    }

    @Override
    public List<Elemento> getAll() throws SQLException {

        List<Elemento> elementos = new ArrayList<>();

		String queryString = "SELECT * FROM elementos e JOIN catedras c on e.elcaId = c.caid JOIN usuarios u on u.usmail = e.elpropietario where e.eltipo = ?";
		
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, "carpeta");
		resultSet = ptmt.executeQuery();

		while (resultSet.next()) {
			elementos.add(
				new Carpeta(
					resultSet.getString("elnombre"),
					resultSet.getString("eltipo"),
					LocalDate.parse(resultSet.getString("elfechamodificacion")),
					LocalDate.parse(resultSet.getString("elfechacreacion")),
					resultSet.getString("elelempadre"),
					new Usuario(
						resultSet.getString("usmail"),
						resultSet.getString("usnombre"),
						resultSet.getInt("uspuntaje")
					),
					new Catedra(
						resultSet.getString("caid"),
						resultSet.getString("caurl")
					)
					));
		}

		if (resultSet != null)
			resultSet.close();
		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

		return elementos;
    }

    @Override
    public Elemento get(String nombre) throws SQLException {
        
        Elemento elemento = new Carpeta(); 

		String queryString = "SELECT * FROM elementos e JOIN catedras c on e.elcaId = c.caid JOIN usuarios u on e.elpropietario = u.usmail WHERE elnombre = ?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, nombre);
		resultSet = ptmt.executeQuery();

		if (resultSet.next()) {
			elemento.setNombre(resultSet.getString("elnombre"));
			elemento.setTipo(resultSet.getString("eltipo"));
			elemento.setFechaCreacion(Instant.ofEpochMilli(resultSet.getDate("elfechamodificacion").getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
			elemento.setFechaCreacion(Instant.ofEpochMilli(resultSet.getDate("elfechacreacion").getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
			elemento.setCatedra(new Catedra(resultSet.getString("elcaid"), resultSet.getString("caurl")));
			elemento.setPadre(resultSet.getString("elelempadre"));
			elemento.setPropietario(
				new Usuario(
					resultSet.getString("usmail"),
					resultSet.getString("usnombre"),
					resultSet.getInt("uspuntaje")
				)
			);
		}

		if (resultSet != null)
			resultSet.close();
		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

		return elemento;
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