package com.example.endpoints.utils;

import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Usuario;
import com.example.modelo.criterios.*;
import com.example.servicios.Servicios;

import java.util.ArrayList;
import java.util.Map;

public class FabricaCriterio {

    private final Servicios servicio = Servicios.getInstance();

    public Criterio getCriterio(Map<String, Object> criterio) {
        if (criterio == null) {
            return null;
        }

        ArrayList<Criterio> compuesto = new ArrayList<>();

        for (Map.Entry<String, Object> entry : criterio.entrySet()) {

            if (entry.getKey().equalsIgnoreCase("autor")) {
                String usuarioParam = (String) entry.getValue();
                try {
                    Usuario u = servicio.getUsuario(usuarioParam);
                    compuesto.add(new CriterioAutor(u));
                } catch (ExcepcionServicio e) {
                    e.printStackTrace();
                }

            } else if (entry.getKey().equalsIgnoreCase("tipo")) {
                String tipoParam = (String) entry.getValue();
                compuesto.add(new CriterioTipo(tipoParam));

            } else if (entry.getKey().equalsIgnoreCase("contienenombre")) {
                String nombreParam = (String) entry.getValue();
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
