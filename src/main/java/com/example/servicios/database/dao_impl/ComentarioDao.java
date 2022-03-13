package com.example.servicios.database.dao_impl;

import com.example.modelo.Comentario;
import com.example.modelo.Usuario;
import com.example.servicios.database.Dao;
import com.example.servicios.database.IDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ComentarioDao extends Dao implements IDao<Comentario, Integer> {

    private static ComentarioDao comentarioDao = null;

    private ComentarioDao(){}

    public static ComentarioDao getInstance(){
        if (comentarioDao == null)
            comentarioDao = new ComentarioDao();

        return comentarioDao;
    }

    public List<Comentario> getComentarios(String nombreElem) throws SQLException {

		List<Comentario> opiniones = new ArrayList<>();

		String queryString = "SELECT * FROM comentarios c JOIN elementos e  on c.coidelemento = e.elnombre JOIN usuarios u on c.coautor = u.usmail where c.coidelemento = ?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, nombreElem);
		resultSet = ptmt.executeQuery();
		while (resultSet.next()) {
			opiniones.add(
					new Comentario(
							resultSet.getInt("coId"),
							resultSet.getString("codescripcion"),
							new Usuario(resultSet.getString("usmail"), resultSet.getString("usnombre"), resultSet.getInt("uspuntaje")),
							resultSet.getString("elnombre")
					));
		}

		if (resultSet != null)
			resultSet.close();
		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

		return opiniones;
	}

    @Override
    public Comentario get(Integer nombre) throws SQLException {

		Comentario comentario = new Comentario();

		String queryString = "SELECT * FROM comentarios c JOIN elementos e on c.coidelemento = e.elnombre JOIN usuarios u on c.coautor = u.usmail where c.coid = ?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setInt(1, nombre);
		resultSet = ptmt.executeQuery();

		if (resultSet.next()) {
			comentario.setId(nombre);
			comentario.setNombreElemento(resultSet.getString("coidelemento"));
			comentario.setDescripcion(resultSet.getString("codescripcion"));
			comentario.setAutor(new Usuario(resultSet.getString("usmail"), resultSet.getString("usnombre"), resultSet.getInt("uspuntaje")));
		}

		if (resultSet != null)
			resultSet.close();
		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

		return comentario;
	}

    @Override
    public void update(Comentario comentario) throws SQLException {

		String queryString = "UPDATE comentarios SET codescripcion=? WHERE coid=?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, comentario.getDescripcion());
		ptmt.setInt(2, comentario.getId());
		ptmt.executeUpdate();

		System.out.println("Table Updated Successfully");

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
	}

    @Override
    public void delete(Integer idCom) throws SQLException {

		String queryString = "DELETE FROM comentarios WHERE coid=?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setInt(1, idCom);
		ptmt.executeUpdate();
		System.out.println("Data deleted Successfully");

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
	}

    @Override
    public void add(Comentario comentario) throws SQLException {

		String queryString = "INSERT INTO comentarios (coid, coidelemento, coautor, codescripcion) VALUES(?,?,?,?)";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);

		ptmt.setInt(1, comentario.getId());
		ptmt.setString(2, comentario.getNombreElemento());
		ptmt.setString(3, comentario.getAutor().getMail());
		ptmt.setString(4, comentario.getDescripcion());

		ptmt.executeUpdate();

		System.out.println("Data Added Successfully");

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
	}

	@Override
	public boolean exist(Integer id) throws SQLException {

		String queryString = "SELECT coid FROM comentarios where coid = ?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setInt(1, id);
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

	@Override
    public List<Comentario> getAll() throws SQLException {
		List<Comentario> comentarios = new ArrayList<>();

		String queryString = "SELECT * FROM comentarios c JOIN Elemento e on c.coid = e.elnombre JOIN usuarios u on u.usmail = c.coautor";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		resultSet = ptmt.executeQuery();

		while (resultSet.next()) {
			comentarios.add(
					new Comentario(resultSet.getInt("coid"), resultSet.getString("codescripcion"),
							new Usuario(resultSet.getString("coautor"), resultSet.getString("usnombre"), resultSet.getInt("uspuntaje")),
							resultSet.getString("elnombre")
					));
		}

		if (resultSet != null)
			resultSet.close();
		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

		return comentarios;
	}
}
