package com.example.servicios.database.dao_impl;

import com.example.modelo.Usuario;
import com.example.servicios.database.Dao;
import com.example.servicios.database.IDao;

import java.util.ArrayList;
import java.util.List;



import java.sql.SQLException;

public class UsuarioDao extends Dao implements IDao<Usuario, String> {

	private static UsuarioDao usuarioDao = null;

	private UsuarioDao(){}

	public static UsuarioDao getInstance(){
		if (usuarioDao == null)
			usuarioDao = new UsuarioDao();
		return usuarioDao;
	}

    @Override
    public List<Usuario> getAll() throws SQLException {
		List<Usuario> usuarios = new ArrayList<>();

		String queryString = "SELECT * FROM usuarios";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		resultSet = ptmt.executeQuery();

		while (resultSet.next()) {
			usuarios.add(new Usuario(resultSet.getString("usmail"),
					resultSet.getString("usnombre"),
					resultSet.getInt("uspuntaje")));
		}

		if (resultSet != null)
			resultSet.close();
		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

		return usuarios;
	}

    @Override 
    public Usuario get(String mail) throws SQLException {
		Usuario usuario = new Usuario();

		String queryString = "SELECT * FROM usuarios WHERE usmail = ?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, mail);
		resultSet = ptmt.executeQuery();

		if (resultSet.next()) {
			usuario.setNombre(resultSet.getString("usnombre"));
			usuario.setPuntaje(resultSet.getInt("uspuntaje"));
			usuario.setMail(resultSet.getString("usmail"));

			if (resultSet != null)
				resultSet.close();
			if (ptmt != null)
				ptmt.close();
			if (connection != null)
				connection.close();

		}
		return usuario;

	}

    @Override
    public void delete(String mail) throws SQLException {

		String queryString = "DELETE FROM usuarios WHERE usmail=?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, mail);
		ptmt.executeUpdate();

		System.out.println("Data deleted Successfully");

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
	}

    @Override
    public void add(Usuario usuario) throws SQLException {

		String queryString = "INSERT INTO usuarios(usmail, usnombre, uspuntaje) VALUES(?,?,?)";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);

		ptmt.setString(1, usuario.getMail());
		ptmt.setString(2, usuario.getNombre());
		ptmt.setInt(3, usuario.getPuntaje());
		ptmt.executeUpdate();

		System.out.println("Data Added Successfully");

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();

    }

    @Override
    public void update(Usuario usuario) throws SQLException {

		String queryString = "UPDATE usuarios SET usnombre=?, uspuntaje=? WHERE usmail=?";
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setString(1, usuario.getNombre());
		ptmt.setInt(2, usuario.getPuntaje());
		ptmt.setString(3, usuario.getMail());
		ptmt.executeUpdate();
		System.out.println("Table Updated Successfully");

		if (ptmt != null)
			ptmt.close();
		if (connection != null)
			connection.close();
	}

}
