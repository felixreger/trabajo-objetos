package com.trabajofinal.modelo.criterios.archivo;

import com.trabajofinal.modelo.Archivo;

public class Tipo implements CriterioArchivo {
    String tipo;

    public Tipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public Boolean cumple(Archivo e) {
        return e.getExtension().equals(tipo);
    }
}
