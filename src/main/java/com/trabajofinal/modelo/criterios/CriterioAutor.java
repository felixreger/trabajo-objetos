package com.trabajofinal.modelo.criterios;

import com.trabajofinal.modelo.Archivo;
import com.trabajofinal.modelo.Usuario;

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
