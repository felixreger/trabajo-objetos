package servicios.database.dao_impl.elementos;

import java.util.List;

import servicios.database.IDao;
import servicios.database.Dao;

import java.sql.Date;
import java.sql.SQLException;

import modelo.Elemento;


public abstract class ElementoDao extends Dao implements IDao<Elemento, String>{

    @Override
    public abstract List<Elemento> getAll();

    @Override
    public abstract Elemento get(String id);

    @Override
    public void update(Elemento elem) {
        try {
			String queryString = "UPDATE elementos SET(eltamanio, eltipo, elfechacreacion, elfechamodificacion) VALUES=(?,?,?,?) WHERE elnombre=?";
            
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setInt(1, elem.getTamanio());
			ptmt.setString(2, elem.getTipo());
			ptmt.setDate(3, Date.valueOf(elem.getFechaCreacion()));
            ptmt.setDate(4, Date.valueOf(elem.getFechaModificacion()));
            ptmt.setString(5, elem.getNombre());
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
    public boolean delete(String id) {
        try {
			String queryString = "DELETE FROM elementos WHERE elnombre=?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, id);
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
    public void add(Elemento elem) {
        try {

            String queryString = "INSERT INTO elementos (elnombre, elcaId, elelempadre, elpropietario, eltamanio, eltipo, elfechamodificacion, elfechacreacion)"
                                + "VALUES(?,?,?,?,?,?,?,?)";
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);

            ptmt.setString(1, elem.getNombre());
            ptmt.setString(2, elem.getCatedra().getNombre());
            ptmt.setString(3, elem.getPadre().getNombre()); 
            ptmt.setString(4, elem.getPropietario());
            ptmt.setInt(5, elem.getTamanio());
            ptmt.setString(6, elem.getTipo());
            ptmt.setDate(7, Date.valueOf(elem.getFechaCreacion()));
            ptmt.setDate(8, Date.valueOf(elem.getFechaModificacion()));

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
