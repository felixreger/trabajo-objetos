package com.trabajofinal.modelo.criterios;

import com.trabajofinal.modelo.Archivo;

public class CriterioTipo implements Criterio {
    String tipo;

    public CriterioTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public Boolean cumple(Archivo e) {
        return e.getExtension().equals(tipo);
    }
}
