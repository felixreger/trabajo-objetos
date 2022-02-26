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

    public List<Comentario> getComentarios(String nombreElem) {
    
        List<Comentario> opiniones = new ArrayList<>();
        try {
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
            return opiniones;
		} catch (SQLException e) {
			e.printStackTrace();
            return opiniones;
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
    public Comentario get(Integer nombre) {
        
        Comentario comentario = new Comentario();
        try {
			String queryString = "SELECT * FROM comentario c JOIN Elemento e on c.coid = e.elnombre where c.coid = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
            ptmt.setInt(1, nombre);
			resultSet = ptmt.executeQuery();
			if (resultSet.next()) {
                comentario.setDescripcion(resultSet.getString("codescripcion"));
                comentario.setAutor(new Usuario(resultSet.getString("usmail"), resultSet.getString("usnombre"), resultSet.getInt("uspuntaje")));  
			}
            return comentario;
		} catch (SQLException e) {
			e.printStackTrace();
            return comentario;
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
    public void update(Comentario comentario) {

        try {
			String queryString = "UPDATE comentario c SET c.codescripcion=? WHERE c.coid=?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, comentario.getDescripcion());
			ptmt.setInt(2, comentario.getId());
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
    public boolean delete(Integer idCom) {
        
        try {
			String queryString = "DELETE FROM comentario WHERE coid=?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setInt(1, idCom);
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
    public void add(Comentario comentario) {
        try {

            String queryString = "INSERT INTO comentarios (coid, coautor,codescripcion,coidelemento) VALUES(?,?,?,?)";
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);

			ptmt.setInt(1, comentario.getId());
            ptmt.setString(2, comentario.getAutor().getMail());
            ptmt.setString(3, comentario.getDescripcion());
            ptmt.setString(4, comentario.getNombreElemento());
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
    public List<Comentario> getAll() {
        List<Comentario> comentarios = new ArrayList<>();
        try {
			String queryString = "SELECT * FROM comentarios c JOIN Elemento e on c.coid = e.elnombre JOIN usuarios u on u.usmail = c.coautor";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {  
                comentarios.add(
					new Comentario(resultSet.getInt("coid"),resultSet.getString("codescripcion"), 
                		new Usuario(resultSet.getString("coautor"), resultSet.getString("usnombre"), resultSet.getInt("uspuntaje")),
						resultSet.getString("elnombre")
				));
				// Tiene mal la ionstanciacion de ussuario 
			}
            return comentarios;
		} catch (SQLException e) {
			e.printStackTrace();
            return comentarios;
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
}
