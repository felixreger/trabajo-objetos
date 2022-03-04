package com.example.modelo.criterios;

import com.example.modelo.Archivo;

public class CriterioTipo implements Criterio {
    String tipo;

    public CriterioTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public Boolean cumple(Archivo e) {
        return e.getTipo().equals(tipo);
    }
}
