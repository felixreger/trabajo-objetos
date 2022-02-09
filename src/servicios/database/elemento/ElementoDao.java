package servicios.database.elemento;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import modelo.Catedra;
import modelo.Elemento;
import servicios.database.Dao;

import java.sql.SQLException;

public class ElementoDao extends Dao implements IElementoDao{

    private static IElementoDao elementoDao = null;

    private ElementoDao(){
    }

    public static IElementoDao getInstance(){
        if (elementoDao == null)
            elementoDao = new ElementoDao();
            
        return elementoDao;
    }

    @Override
    public List<Elemento> getElementos() {
        List<Elemento> elementos = new ArrayList<>();
        try {
			String queryString = "SELECT * FROM elementos";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();

            // todo : utilizar las consultas compuestas
            while (resultSet.next()) {
                elementos.add(
                    new Elemento(resultSet.getString("nombre"),
                        resultSet.getString("tipo"),
                        resultSet.getInt("tamanio"),
                        LocalDate.parse(resultSet.getString("fechaModificacion")),
                        LocalDate.parse(resultSet.getString("fechaCreacion")),
                        new Catedra("catedra")));
			}
            return elementos;
		} catch (SQLException e) {
			e.printStackTrace();
            return elementos;
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
    public Elemento getElemento(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateElemento(Elemento elem) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean deleteElemento(Elemento elem) {
        // Verificar que el elemento exista
        if(!getElemento(elem.getNombre()).isValid()){
            
            // En caso de eliminarlo borrar el registro
            /* try {
                String queryString = "DELETE FROM usuario WHERE mail=?";
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

            } */
            


            return true;
        }
        return false;
    }

    @Override
    public void addElemento(Elemento elem) {
        // TODO Auto-generated method stub
        
    }

    
}