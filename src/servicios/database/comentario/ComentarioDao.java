package servicios.database.comentario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Comentario;
import modelo.Usuario;
import servicios.database.Dao;

public class ComentarioDao extends Dao implements IComentarioDao{

    private static IComentarioDao comentarioDao = null;

    private ComentarioDao(){}

    public static IComentarioDao getInstance(){
        if (comentarioDao == null)
            comentarioDao = new ComentarioDao();

        return comentarioDao;
    }

    @Override
    public List<Comentario> getComentarios(String elem) {
        /* A resolver:
            1ero: Dado el elemento encontrar el comentario
            2do: En el constructor del comentario se debe especificar el Usuario 
        */
        
        List<Comentario> opiniones = new ArrayList<>();
        try {
			String queryString = "SELECT * FROM opinion where opElemId = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
            ptmt.setString(1, elem);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
                // todo: ver como resolver referencia. 
                opiniones.add(new Comentario(resultSet.getString("descripcion"), new Usuario()));
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
    public Comentario getComentario(String nombre) {
        
        return null;
    }

    @Override
    public void updateComentario(Comentario opinion) {
        
        
    }

    @Override
    public boolean deleteComentario(Comentario opinion) {
        
        return true;
    }

    @Override
    public void addComentario(Comentario opinion) {
        
    }
    
}
