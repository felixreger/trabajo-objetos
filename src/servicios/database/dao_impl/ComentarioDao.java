package servicios.database.dao_impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Comentario;
import modelo.Usuario;
import servicios.database.Dao;
import servicios.database.IDao;

public class ComentarioDao extends Dao implements IDao<Comentario, Integer>{

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
			String queryString = "SELECT * FROM comentarios c JOIN elementos e JOIN usuarios u on c.coidelemento = e.elnombre and c.coautor = u.usmail where c.elnombre = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
            ptmt.setString(1, nombreElem);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {  
                opiniones.add(new Comentario(resultSet.getString("codescripcion"), 
                new Usuario(resultSet.getString("usmail"), resultSet.getString("usnombre"), resultSet.getInt("uspuntaje"))));
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

            String queryString = "INSERT INTO comentario(coid, coautor) VALUES(?,?)";
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);

            ptmt.setString(1, comentario.getDescripcion());
            ptmt.setString(2, comentario.getAutor().getMail());
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
        List<Comentario> opiniones = new ArrayList<>();
        try {
			String queryString = "SELECT * FROM Comentario c JOIN Elemento e on c.coid = e.elnombre";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {  
                opiniones.add(new Comentario(resultSet.getString("codescripcion"), 
                new Usuario(resultSet.getString("coMail"), resultSet.getString("elnombre"), resultSet.getInt("puntaje"))));
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
}
