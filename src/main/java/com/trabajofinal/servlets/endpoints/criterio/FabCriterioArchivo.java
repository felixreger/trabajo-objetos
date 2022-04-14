package com.trabajofinal.servlets.endpoints.criterio;

import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.modelo.criterios.archivo.*;
import com.trabajofinal.servicios.Servicios;
import com.trabajofinal.servlets.criterios.IFabricaCriterio;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FabCriterioArchivo implements IFabricaCriterio<Map<String, String>> {

    private final Servicios servicio = Servicios.getInstance();
    private boolean esValido = true;

    /**
     * Se obtiene el criterio del archivo segun los criterios(Map) que se obtienen en la request
     */
    @Override
    public CriterioArchivo getCriterio(Map<String, String> criterio)  {

        //Destinado a contener la lista de objetos de criterios
        ArrayList<CriterioArchivo> criterioCompuesto = new ArrayList<>();

        //Por cada criterio que se encuentre en el mapa, se crea un objeto correspondiente con
        //los datos que se encuentran en el "value"
        for (Map.Entry<String, String> entry : criterio.entrySet()) {

            if (entry.getKey().equalsIgnoreCase(ConstantesServlet.AUTOR)) {
                String usuarioParam = entry.getValue();
                try {
                    Usuario u = servicio.getUsuario(usuarioParam);
                    criterioCompuesto.add(new Autor(u));
                } catch (ExcepcionServicio ignored) {}

            } else if (entry.getKey().equalsIgnoreCase(ConstantesServlet.TIPO)) {
                String tipoParam = entry.getValue();
                criterioCompuesto.add(new Tipo(tipoParam));

            } else if (entry.getKey().equalsIgnoreCase(ConstantesServlet.CONTIENE_NOMBRE)) {
                String nombreParam = entry.getValue();
                criterioCompuesto.add(new ContieneNombreElemento(nombreParam));

            } else if (entry.getKey().equalsIgnoreCase(ConstantesServlet.FECHA_CREACION)) {
                String nombreParam = entry.getValue();
                criterioCompuesto.add(new FechaCreacion(this.getFecha(nombreParam)));

            }else if (entry.getKey().equalsIgnoreCase(ConstantesServlet.FECHA_MODIFICACION)) {
                String nombreParam = entry.getValue();
                criterioCompuesto.add(new FechaModificacion(this.getFecha(nombreParam)));

            }else if (entry.getKey().equalsIgnoreCase(ConstantesServlet.CONTIENE_PALABRA)){
                String nombreParam = entry.getValue();
                criterioCompuesto.add(new PalabrasClave(this.getPalabrasClave(nombreParam)));
            }
        }

        //Si no tengo ningun criterio, entonces hubo un error en la especificacion de los
        //nombres de los criterios, por lo que la fabrica creó un criterio NO valido.
        if(criterioCompuesto.isEmpty()){
            esValido = false;
            return null;
        }

        // Si tengo mas de 1 criterio, entonces significa que debo concatenar con el criterio AND
        if (criterioCompuesto.size() > 1) {
            CriterioArchivo anterior = criterioCompuesto.get(0);
            CriterioArchivo nuevo = null;

            //Por cada 2 criterios, se concatenan
            for(int i = 1; i < criterioCompuesto.size(); i++){
                nuevo = new And(anterior, criterioCompuesto.get(i));
                anterior = nuevo;
            }
            return nuevo;
        }

        return criterioCompuesto.get(0);
    }

    /**
     * A partir de un string que tienen un simbolo separador ","
     * obtengo que la lista de palabras claves.
     */
    private Set<String> getPalabrasClave(String nombreParam) {
        String[] palabrasClaveParam = nombreParam.split(",");
        return new HashSet<>(Arrays.asList(palabrasClaveParam));
    }

    private LocalDate getFecha(String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha, formatter);
    }

    public boolean esCriterioValido() {
        return esValido;
    }
}
