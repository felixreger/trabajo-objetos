package com.example.endpoints.utils;

import com.example.endpoints.auth.control.criterio.CriterioCredencial;
import com.example.endpoints.auth.control.criterio.CriterioEsAdmin;
import com.example.endpoints.auth.control.criterio.CriterioUsuarioContraseniaAdmin;
import com.example.endpoints.auth.control.criterio.CriterioUsuarioYContrasenia;
import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Usuario;
import com.example.modelo.criterios.*;
import com.example.servicios.Servicios;

import java.util.ArrayList;
import java.util.Map;

public class FabricaCriterio {

    private final Servicios servicio = Servicios.getInstance();

    //todo: corroborar criterios nuevos.
    public Criterio getCriterioArchivo(Map<String, String> criterio) {

        ArrayList<Criterio> compuesto = new ArrayList<>();

        for (Map.Entry<String, String> entry : criterio.entrySet()) {

            if (entry.getKey().equalsIgnoreCase(Utils.AUTOR)) {
                String usuarioParam = entry.getValue();
                try {
                    Usuario u = servicio.getUsuario(usuarioParam);
                    compuesto.add(new CriterioAutor(u));
                } catch (ExcepcionServicio e) {
                    e.printStackTrace();
                }

            } else if (entry.getKey().equalsIgnoreCase(Utils.TIPO)) {
                String tipoParam = entry.getValue();
                compuesto.add(new CriterioTipo(tipoParam));

            } else if (entry.getKey().equalsIgnoreCase(Utils.CONTIENE_NOMBRE)) {
                String nombreParam = entry.getValue();
                compuesto.add(new CriterioContieneNombreElemento(nombreParam));

            } else if (entry.getKey().equalsIgnoreCase(Utils.FECHA_CREACION)) {
                String nombreParam = entry.getValue();
                compuesto.add(new CriterioContieneNombreElemento(nombreParam));

            }else if (entry.getKey().equalsIgnoreCase(Utils.FECHA_MODIFICACION)) {
                String nombreParam = entry.getValue();
                compuesto.add(new CriterioContieneNombreElemento(nombreParam));
            }
        }

        if (compuesto.size() > 1) {
            Criterio anterior = compuesto.get(0);
            Criterio nuevo = null;
            for(int i = 1; i < compuesto.size(); i++){
                nuevo = new CriterioAnd(anterior, compuesto.get(i));
                anterior = nuevo;
            }
            return nuevo;
        }

        return compuesto.get(0);
    }
}
