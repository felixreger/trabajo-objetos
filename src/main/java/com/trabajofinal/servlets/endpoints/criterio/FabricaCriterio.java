package com.trabajofinal.servlets.endpoints.criterio;

import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.modelo.criterios.*;
import com.trabajofinal.servicios.Servicios;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class FabricaCriterio {

    private final Servicios servicio = Servicios.getInstance();

    public Criterio getCriterioArchivo(Map<String, String> criterio) {

        ArrayList<Criterio> criterioCompuesto = new ArrayList<>();

        for (Map.Entry<String, String> entry : criterio.entrySet()) {

            if (entry.getKey().equalsIgnoreCase(ConstantesServlet.AUTOR)) {
                String usuarioParam = entry.getValue();
                try {
                    Usuario u = servicio.getUsuario(usuarioParam);
                    criterioCompuesto.add(new CriterioAutor(u));
                } catch (ExcepcionServicio e) {
                    e.printStackTrace();
                }

            } else if (entry.getKey().equalsIgnoreCase(ConstantesServlet.TIPO)) {
                String tipoParam = entry.getValue();
                criterioCompuesto.add(new CriterioTipo(tipoParam));

            } else if (entry.getKey().equalsIgnoreCase(ConstantesServlet.CONTIENE_NOMBRE)) {
                String nombreParam = entry.getValue();
                criterioCompuesto.add(new CriterioContieneNombreElemento(nombreParam));

            } else if (entry.getKey().equalsIgnoreCase(ConstantesServlet.FECHA_CREACION)) {
                String nombreParam = entry.getValue();
                criterioCompuesto.add(new CriterioFechaCreacion(this.getFecha(nombreParam)));

            }else if (entry.getKey().equalsIgnoreCase(ConstantesServlet.FECHA_MODIFICACION)) {
                String nombreParam = entry.getValue();
                criterioCompuesto.add(new CriterioFechaModificacion(this.getFecha(nombreParam)));
            }
        }

        if (criterioCompuesto.size() > 1) {
            Criterio anterior = criterioCompuesto.get(0);
            Criterio nuevo = null;
            for(int i = 1; i < criterioCompuesto.size(); i++){
                nuevo = new CriterioAnd(anterior, criterioCompuesto.get(i));
                anterior = nuevo;
            }
            return nuevo;
        }

        return criterioCompuesto.get(0);
    }

    private LocalDate getFecha(String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha, formatter);
    }

}
