package servicios.database.dao_impl.elementos;

import java.util.List;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import modelo.Archivo;
import modelo.Carpeta;
import modelo.Catedra;
import modelo.Elemento;
import modelo.Usuario;

import java.sql.SQLException;

public class ArchivoDao extends ElementoDao{

    private static ArchivoDao archivoDao = null;

    private ArchivoDao(){
    }

    public static ArchivoDao getInstance(){
        if (archivoDao == null)
            archivoDao = new ArchivoDao();
            
        return archivoDao;
    }

    @Override
    public List<Elemento> getAll() {

        List<Elemento> archivos = new ArrayList<>();
        try {
			String queryString = "SELECT * FROM elementos e JOIN catedras c on e.elcaid = c.caid where e.eltipo != ?";
			connection = getConnection();

			ptmt = connection.prepareStatement(queryString);
            ptmt.setString(1, "carpeta");
			resultSet = ptmt.executeQuery();
            while (resultSet.next()) { // todo: ver si con el nombre del atributo solo alcanza o falta agregarle la tabla
                archivos.add(
                    new Archivo(
                        resultSet.getString("elnombre"),
                        resultSet.getString("eltipo"),
                        resultSet.getInt("eltamanio"),
                        LocalDate.parse(resultSet.getString("elfechamodificacion")),
                        LocalDate.parse(resultSet.getString("elfechacreacion")), 
                        new Catedra(),
                        new Usuario(), //todo agregar params. 
						new Carpeta()

                    ));
			}
            return archivos;
		} catch (SQLException e) {
			e.printStackTrace();
            return archivos;
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
    public Elemento get(String id) {

        Elemento archivo = new Archivo();
        try {
			String queryString = "SELECT * FROM elementos e JOIN catedras c on e.elcaid = c.caid WHERE elnombre = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, id);
			resultSet = ptmt.executeQuery();
			if (resultSet.next()) {
				archivo.setNombre(resultSet.getString("elnombre"));
				archivo.setTipo(resultSet.getString("eltipo"));
				archivo.setTamanio(resultSet.getInt("eltamanio"));
                archivo.setFechaCreacion(Instant.ofEpochMilli(resultSet.getDate("elfechamodificacion").getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
                archivo.setFechaCreacion(Instant.ofEpochMilli(resultSet.getDate("elfechacreacion").getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				archivo.setCatedra(new Catedra(resultSet.getString("elcaid"), resultSet.getString("caurl"))); // revisar atributo
				
                return archivo;
			}	
            return archivo;
		} catch (SQLException e) {
			e.printStackTrace();
            return archivo;
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