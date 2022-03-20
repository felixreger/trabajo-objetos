package com.trabajofinal.servicios.database.dao_jdbc.elementos;

import com.trabajofinal.modelo.Carpeta;
import com.trabajofinal.modelo.Elemento;
import com.trabajofinal.modelo.Usuario;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CarpetaDao extends ElementoDao {

    private static CarpetaDao carpetaDao = null;

    private CarpetaDao(){
    }

    public static CarpetaDao getInstance(){
        if (carpetaDao == null)
            carpetaDao = new CarpetaDao();
            
        return carpetaDao;
    }

	private Usuario cargarUsuario(ResultSet resultSet) throws SQLException {
		return new Usuario(
			resultSet.getString("usmail"),
			resultSet.getString("usnombre"),
			resultSet.getInt("uspuntaje"),
			resultSet.getBoolean("usesadmin")
		);
	}

	private Carpeta getCarpeta(ResultSet resultSet) throws SQLException {
		return new Carpeta(
			resultSet.getString("elnombre"),
			this.cargarUsuario(resultSet),
			LocalDate.parse(resultSet.getString("elfechacreacion")),
			LocalDate.parse(resultSet.getString("elfechamodificacion")),
			this.getPath(resultSet),
			resultSet.getString("cadescripcion")
		);
	}

    @Override
    public List<Elemento> getAll() throws SQLException {

        List<Elemento> elementos = new ArrayList<>();
		String queryString = "SELECT * FROM carpetas c JOIN usuarios u on u.usmail = c.elpropietario";
		
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		resultSet = ptmt.executeQuery();

		while (resultSet.next()) {
			elementos.add(this.getCarpeta(resultSet));
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
    public Elemento get(String pathCarpeta) throws SQLException {

        Carpeta elemento = new Carpeta();

		String queryString = "SELECT * FROM carpetas c JOIN usuarios u on u.usmail = c.elpropietario WHERE c.elpath = ?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, pathCarpeta);
		resultSet = ptmt.executeQuery();

		if (resultSet.next()) {
			elemento = this.getCarpeta(resultSet);
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
	public void update(Elemento elem) throws SQLException {
		//todo: implementar
	}

	@Override
	public void add(Elemento elem) throws SQLException {

		String queryString = "insert into carpetas(elnombre, elpropietario, elfechacreacion, elfechamodificacion, elpath, cadescripcion)\n" +
				"\t\tvalues (?, ?, ?, ?, ?, ?)";

		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		Carpeta carpeta = (Carpeta)elem;
		ptmt.setString(1, carpeta.getNombre());
		ptmt.setString(2, carpeta.getPropietario());
		ptmt.setDate(3, Date.valueOf(carpeta.getFechaCreacion()));
		ptmt.setDate(4, Date.valueOf(carpeta.getFechaModificacion()));

		String path = carpeta.getPath()+ ":" + carpeta.getNombre();
		ptmt.setString(5, path);
		ptmt.setString(6, carpeta.getDescripcion());

		ptmt.executeUpdate();

		System.out.println("Carpeta agregada correctamente");

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
	}

}