package servicios.database.dao_impl;

import java.util.List;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;

import java.util.ArrayList;
import modelo.Catedra;
import modelo.Elemento;
import servicios.database.Dao;
import servicios.database.IDao;

import java.sql.Date;
import java.sql.SQLException;

public class ElementoDao extends Dao implements IDao<Elemento, String>{

    private static ElementoDao elementoDao = null;

    private ElementoDao(){
    }

    public static ElementoDao getInstance(){
        if (elementoDao == null)
            elementoDao = new ElementoDao();
            
        return elementoDao;
    }

    @Override
    public List<Elemento> getAll() {
        List<Elemento> elementos = new ArrayList<>();
        try {
			String queryString = "SELECT * FROM elementos e JOIN catedra c on e.elNombre = c.caId";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();

            while (resultSet.next()) { // todo: ver si con el nombre del atributo solo alcanza o falta agregarle la tabla
                elementos.add(
                    new Elemento(resultSet.getString("nombre"),
                        resultSet.getString("tipo"),
                        resultSet.getInt("tamanio"),
                        LocalDate.parse(resultSet.getString("fechaModificacion")),
                        LocalDate.parse(resultSet.getString("fechaCreacion")),
                        new Catedra(resultSet.getString("caId"), resultSet.getString("caUrl"))));
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
    public Elemento get(String nombre) {
        
        Elemento elemento = new Elemento();
        try {
			String queryString = "SELECT * FROM elementos e JOIN catedra c on e.elNombre = c.caId WHERE elNombre = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, nombre);
			resultSet = ptmt.executeQuery();
			if (resultSet.next()) {
				elemento.setNombre(resultSet.getString("elNombre"));
				elemento.setTipo(resultSet.getString("elTipo"));
				elemento.setTamanio(resultSet.getInt("elTamanio"));
                elemento.setFechaCreacion(Instant.ofEpochMilli(resultSet.getDate("elFechaModificacion").getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
                elemento.setFechaCreacion(Instant.ofEpochMilli(resultSet.getDate("elFechaCreacion").getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				elemento.setCatedra(new Catedra(resultSet.getString("elCaId"), resultSet.getString("caUrl"))); // revisar atributo
				
                return elemento;
			}
            return elemento;
		} catch (SQLException e) {
			e.printStackTrace();
            return elemento;
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
    public void update(Elemento elem) {
        try {
			String queryString = "UPDATE elemento SET(elTamanio, elTipo, elFechaCreacion, elFechaModificacion) VALUES=(?,?,?,?) WHERE elNombre=?";
            
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
			String queryString = "DELETE FROM elemento WHERE elNombre=?";
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

    public void add(Elemento elem) {
        try {

            String queryString = "INSERT INTO elemento(elNombre, elCaId, elElemPadre, elPropietario, elTamanio, elTipo, elFechaModificacion, elFechaCreacion)"
                                + "VALUES(?,?,?,?,?,?,?,?)";
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);

            ptmt.setString(1, elem.getNombre());
            ptmt.setString(2, elem.getCatedra().getNombre());
            ptmt.setString(3, elem.getPropietario());
            ptmt.setString(4, elem.getPropietario());
            ptmt.setInt(5, elem.getTamanio());
            ptmt.setInt(6, elem.getTamanio());
            ptmt.setString(7, elem.getTipo());
            ptmt.setDate(8, Date.valueOf(elem.getFechaCreacion()));
            ptmt.setDate(9, Date.valueOf(elem.getFechaModificacion()));

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