package com.example.modelo.criterios;

import com.example.modelo.Archivo;
import com.example.modelo.Usuario;

public class CriterioAutor implements Criterio {
    private Usuario autor;

    public CriterioAutor(Usuario usuario){
        autor = usuario;
    }

    @Override
    public Boolean cumple(Archivo elemento) {
        return elemento.getPropietario().equals(autor.getMail());
    }
}
