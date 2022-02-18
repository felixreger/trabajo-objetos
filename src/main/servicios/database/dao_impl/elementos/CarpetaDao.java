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
import main.modelo.Usuario;


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
			String queryString = "SELECT * FROM elementos e JOIN catedras c on e.elcaId = c.caid JOIN usuarios u on u.usmail = e.elpropietario where e.eltipo = ?";
		
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, "carpeta");
			resultSet = ptmt.executeQuery();

            while (resultSet.next()) { 
                elementos.add(
                    new Carpeta(
						resultSet.getString("elnombre"),
                        resultSet.getString("eltipo"),
                        LocalDate.parse(resultSet.getString("elfechamodificacion")),
                        LocalDate.parse(resultSet.getString("elfechacreacion")),
						resultSet.getString("elelempadre"),
						new Usuario(
							resultSet.getString("usmail"),
							resultSet.getString("usnombre"),
							resultSet.getInt("uspuntaje")
						),
						new Catedra(
							resultSet.getString("caid"),
							resultSet.getString("caurl")
						)
						));
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
			String queryString = "SELECT * FROM elementos e JOIN catedras c on e.elcaId = c.caid JOIN usuarios u on e.elpropietario = u.usmail WHERE elnombre = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, nombre);
			resultSet = ptmt.executeQuery();
			if (resultSet.next()) {
				elemento.setNombre(resultSet.getString("elnombre"));
				elemento.setTipo(resultSet.getString("eltipo"));
                elemento.setFechaCreacion(Instant.ofEpochMilli(resultSet.getDate("elfechamodificacion").getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
                elemento.setFechaCreacion(Instant.ofEpochMilli(resultSet.getDate("elfechacreacion").getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				elemento.setCatedra(new Catedra(resultSet.getString("elcaid"), resultSet.getString("caurl"))); 
				elemento.setPadre(resultSet.getString("elelempadre"));
				elemento.setPropietario(
					new Usuario(
						resultSet.getString("usmail"),
						resultSet.getString("usnombre"),
						resultSet.getInt("uspuntaje")
					)
				);
				
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