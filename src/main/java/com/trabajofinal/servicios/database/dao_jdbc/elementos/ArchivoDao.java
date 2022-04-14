package com.trabajofinal.servicios.database.dao_jdbc.elementos;

import com.trabajofinal.modelo.*;
import com.trabajofinal.utils.servlets.endpoints.ArchivoBytes;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;
import java.sql.SQLException;

public class ArchivoDao extends ElementoDao {

    private static ArchivoDao archivoDao = null;

    private ArchivoDao(){
    }

    public static ArchivoDao getInstance(){
        if (archivoDao == null)
            archivoDao = new ArchivoDao();
            
        return archivoDao;
    }

	/**
	 * A partir de resultSet, se crea el usuario
	 */
	private Usuario cargarUsuario(ResultSet resultSet) throws SQLException {
		return new Usuario(
			resultSet.getString("usmail"),
			resultSet.getString("usnombre"),
			resultSet.getInt("uspuntaje"),
			resultSet.getBoolean("usesadmin")
		);
	}

	/**
	 * A partir de resultSet, se crea la catedra
	 */
	private Catedra cargarCatedra(ResultSet resultSet) throws SQLException {
		return new Catedra(
				resultSet.getString("caid"),
				resultSet.getString("caurl")
		);
	}

	/**
	 * A partir de resultSet, se crea el archivo
	 */
	public Archivo getArchivo(ResultSet resultSet) throws SQLException {
		Archivo tmp = new Archivo(
			resultSet.getString("elnombre"),
			this.cargarUsuario(resultSet),
			LocalDate.parse(resultSet.getString("elfechacreacion")),
			LocalDate.parse(resultSet.getString("elfechamodificacion")),
			this.getPath(resultSet),
			resultSet.getLong("artamanio"),
			resultSet.getString("arextension"),
			this.cargarCatedra(resultSet)
		);
		String palabrasClave = resultSet.getString("arpalabraclave");
		tmp.addPalabraClave(this.getListaPalabrasClave(palabrasClave));
		tmp.addFuente(null);
		return tmp;
	}

	/**
	 * Desde la base se reciben las palabras claves en un string concatenado con el simbolo $.
	 * En este metodo, usando dicho simbolo, se crea un Set con las palabras claves.
	 */
	private Set<String> getListaPalabrasClave(String palabrasClave) {
		return new HashSet<>(Arrays.asList(palabrasClave.split("\\$")));
	}

	@Override
    public List<Elemento> getAll() throws SQLException {

        List<Elemento> archivos = new ArrayList<>(); //todo: sacar el *, porque se trae el arch. fuente al dope
		String queryString = "SELECT * FROM archivos a JOIN usuarios u on u.usmail = a.elpropietario JOIN catedras c on a.arcaid = c.caid";
		connection = getConnection();

		ptmt = connection.prepareStatement(queryString);
		resultSet = ptmt.executeQuery();

		while (resultSet.next()) {
			archivos.add(this.getArchivo(resultSet));
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
    public Elemento get(String path) throws SQLException {
        Elemento archivo = new Archivo();

		String queryString = "SELECT * FROM archivos a JOIN usuarios u on u.usmail = a.elpropietario WHERE elpath = ?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, path);
		resultSet = ptmt.executeQuery();

		if (resultSet.next()) {
			archivo = this.getArchivo(resultSet);
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
	public void update(Elemento elem) throws SQLException {
		//todo: implementar
	}

	@Override
	public void add(Elemento elem) throws SQLException {
		String queryString = "insert into archivos(elnombre, elpropietario, elfechacreacion, elfechamodificacion, elpath, artamanio, arextension, arpalabraclave, arcaid, arfuente) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		Archivo archivo = (Archivo)elem;

		ptmt.setString(1, archivo.getNombre());
		ptmt.setString(2, archivo.getPropietario());
		ptmt.setDate(3, Date.valueOf(archivo.getFechaCreacion()));
		ptmt.setDate(4, Date.valueOf(archivo.getFechaModificacion()));

		ptmt.setString(5, archivo.getPath() + ":" + archivo.getNombre());
		ptmt.setLong(6, archivo.getTamanio());
		ptmt.setString(7, archivo.getExtension());
		ptmt.setString(8, this.convertirAString(archivo.getPalabrasClave()));
		ptmt.setString(9, archivo.getCatedra().getNombre());
		ptmt.setBinaryStream(10, archivo.getFuente(), archivo.getTamanio());
		ptmt.executeUpdate();

		System.out.println("Archivo agregado correctamente");

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
	}

	/**
	 * Se convierte el Set de palabras claves a un string con el simbolo $ como separador.
	 */
	private String convertirAString(Set<String> palabrasClave) {
		StringBuilder sb = new StringBuilder();
		for(String s : palabrasClave) {
			sb.append(s);
			sb.append('$');
		}
		return sb.toString();
	}

	public ArchivoBytes getArchivoFuente(String pathArchivo) throws SQLException {

		String queryString = "SELECT arfuente, arextension FROM archivos a WHERE elpath = ?";

		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, pathArchivo);
		resultSet = ptmt.executeQuery();

		ArchivoBytes archivoBytes = new ArchivoBytes();

		if (resultSet.next()) {
			archivoBytes.setArchivoFuente(resultSet.getBytes("arfuente"));
			archivoBytes.setExtension(resultSet.getString("arextension"));
		}

		if (resultSet != null)
			resultSet.close();
		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

		return archivoBytes;
	}
}