package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;

import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDao implements IUsuarioDao{

    
	Connection connection = null;
	PreparedStatement ptmt = null;
	Statement stmt = null;
	ResultSet resultSet = null;

    private Connection getConnection() throws SQLException {
		Connection conn;
		conn = Conexion.getInstance().getConnection();
		return conn;
	}

    @Override
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
			String queryString = "SELECT * FROM student";
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
    public Usuario getUsuario(String mail) {
        try {
			connection = getConnection();
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM user WHERE id=" + mail);
           
            if(resultSet.next())
{
                Usuario usuario = new Usuario();

                usuario.setMail( resultSet.getString("mail") );
                usuario.setNombre( resultSet.getString("nombre") );
                usuario.setPuntaje( resultSet.getInt("puntaje") );

                return usuario;
            }

		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			}

			catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return new Usuario();

    }

    @Override
    public void updateUsuario(Usuario usuario) {
        try {
			String queryString = "UPDATE student SET Name=? WHERE mail=?"; // todo: ver consulta
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, usuario.getNombre());
			ptmt.setString(2, usuario.getMail());
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

    @Override
    public void deleteUsuario(String mail) {
        try {
			String queryString = "DELETE FROM student WHERE mail=?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, mail);
			ptmt.executeUpdate();
			System.out.println("Data deleted Successfully");
		} catch (SQLException e) {
			e.printStackTrace();
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
    public void addUsuario(Usuario usuario) {

        try {

            String queryString = "INSERT INTO usuario(Mail, Nombre, Puntaje) VALUES(?,?,?)";
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
}
