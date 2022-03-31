package com.trabajofinal.servlets.endpoints.criterio;

import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.modelo.criterios.*;
import com.trabajofinal.servicios.Servicios;
import com.trabajofinal.utils.criterios.IFabricaCriterio;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FabCriterioArchivo implements IFabricaCriterio<CriterioArchivo, Map<String, String>> {

    private final Servicios servicio = Servicios.getInstance();
    private boolean esValido = true;

    @Override
    public CriterioArchivo getCriterio(Map<String, String> criterio)  {

        ArrayList<CriterioArchivo> criterioCompuesto = new ArrayList<>();

        for (Map.Entry<String, String> entry : criterio.entrySet()) {

            if (entry.getKey().equalsIgnoreCase(ConstantesServlet.AUTOR)) {
                String usuarioParam = entry.getValue();
                try {
                    Usuario u = servicio.getUsuario(usuarioParam);
                    criterioCompuesto.add(new Autor(u));
                } catch (ExcepcionServicio e) {
                    e.printStackTrace();
                }

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

        if (criterioCompuesto.size() > 1) {
            CriterioArchivo anterior = criterioCompuesto.get(0);
            CriterioArchivo nuevo = null;
            for(int i = 1; i < criterioCompuesto.size(); i++){
                nuevo = new And(anterior, criterioCompuesto.get(i));
                anterior = nuevo;
            }
            return nuevo;
        }

        if(criterioCompuesto.isEmpty()){
            esValido = false;
            return null;
        }

        return criterioCompuesto.get(0);
    }

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
