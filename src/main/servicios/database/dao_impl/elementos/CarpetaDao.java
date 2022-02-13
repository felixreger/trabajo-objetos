package main.servicios.database.dao_impl.elementos;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import main.modelo.Carpeta;
import main.modelo.Catedra;
import main.modelo.Elemento;


public class CarpetaDao extends ElementoDao{

    private static CarpetaDao carpetaDao = null;

    private CarpetaDao(){
    }

    public static CarpetaDao getInstance(){
        if (carpetaDao == null)
            carpetaDao = new CarpetaDao();
            
        return carpetaDao;
    }

    @Override
    public List<Elemento> getAll() {
        List<Elemento> elementos = new ArrayList<>();

        try {
			String queryString = "SELECT * FROM elementos e JOIN catedras c on e.elcaId = c.caid";
		
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();

            while (resultSet.next()) { // todo: ver si con el nombre del atributo solo alcanza o falta agregarle la tabla
                elementos.add(
                    new Carpeta(resultSet.getString("elnombre"),
                        resultSet.getString("eltipo"),
                        LocalDate.parse(resultSet.getString("elfechamodificacion")),
                        LocalDate.parse(resultSet.getString("elfechacreacion")),
						new Carpeta())
						);
                        // ver como agregar los elementos que contiene. 
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
        
        Elemento elemento = new Carpeta(); 
        try {
			String queryString = "SELECT * FROM elementos e JOIN catedras c on e.elcaId = c.caid WHERE elnombre = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, nombre);
			resultSet = ptmt.executeQuery();
			if (resultSet.next()) {
				elemento.setNombre(resultSet.getString("elnombre"));
				elemento.setTipo(resultSet.getString("eltipo"));
				// agregar elemento.setTamanio(resultSet.getInt("eltamanio"));
                elemento.setFechaCreacion(Instant.ofEpochMilli(resultSet.getDate("elfechamodificacion").getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
                elemento.setFechaCreacion(Instant.ofEpochMilli(resultSet.getDate("elfechacreacion").getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				elemento.setCatedra(new Catedra(resultSet.getString("elcaId"), resultSet.getString("caurl"))); // revisar atributo
				
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

}