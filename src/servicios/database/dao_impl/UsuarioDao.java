package servicios.database.dao_impl;

import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;
import servicios.database.Dao;
import servicios.database.IDao;

import java.sql.SQLException;

public class UsuarioDao extends Dao implements IDao<Usuario, String>{

	private static UsuarioDao usuarioDao = null;

	private UsuarioDao(){}

	public static UsuarioDao getInstance(){
		if (usuarioDao == null)
			usuarioDao = new UsuarioDao();
		return usuarioDao;
	}

    @Override
    public List<Usuario> getAll() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
			String queryString = "SELECT * FROM usuario";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
                usuarios.add(new Usuario(resultSet.getString("mail"),
                resultSet.getString("nombre"),
                resultSet.getInt("puntaje")));
			}
            return usuarios;
		} catch (SQLException e) {
			e.printStackTrace();
            return usuarios;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
    }

    @Override 
    public Usuario get(String mail) {
		Usuario usuario = new Usuario();
        try {
			String queryString = "SELECT * FROM usuario WHERE mail = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, mail);
			resultSet = ptmt.executeQuery();
			if (resultSet.next()) {
				usuario.setNombre(resultSet.getString("nombre"));
				usuario.setPuntaje(resultSet.getInt("puntaje"));
				usuario.setMail(resultSet.getString("mail"));

				return usuario;
			}
            return usuario;
		} catch (SQLException e) {
			e.printStackTrace();
            return usuario;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        
    }

    @Override
    public boolean delete(String mail) {
        try {
			String queryString = "DELETE FROM usuario WHERE mail=?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, mail);
			ptmt.executeUpdate();
			System.out.println("Data deleted Successfully");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
        
    }

    @Override
    public void add(Usuario usuario) {

        try {

            String queryString = "INSERT INTO usuario(mail, nombre, puntaje) VALUES(?,?,?)";
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);

            ptmt.setString(1, usuario.getMail());
            ptmt.setString(2, usuario.getNombre());
            ptmt.setInt(3, usuario.getPuntaje());
            ptmt.executeUpdate();

            System.out.println("Data Added Successfully");
    
            } catch (Exception e) {
            	e.printStackTrace();
            }finally{
    
            try {
                    if (ptmt != null)
                        ptmt.close();
                    if (connection != null)
                        connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    @Override
    public void update(Usuario usuario) {
        try {
			String queryString = "UPDATE usuario SET nombre=?, puntaje=? WHERE mail=?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, usuario.getNombre());
			ptmt.setInt(2, usuario.getPuntaje());
			ptmt.setString(3, usuario.getMail());
			ptmt.executeUpdate();
			System.out.println("Table Updated Successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			}

			catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
        
    }

}
