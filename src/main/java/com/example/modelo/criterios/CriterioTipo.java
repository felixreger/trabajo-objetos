package com.example.modelo.criterios;


import com.example.modelo.Elemento;

public class CriterioTipo implements Criterio {
    String tipo;

    public CriterioTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public Boolean cumple(Elemento e) {
        return e.getTipo().equals(tipo);
    }
}