package com.trabajofinal.modelo.criterios;

import com.trabajofinal.modelo.Archivo;
import com.trabajofinal.modelo.Usuario;

public class Autor implements CriterioArchivo {
    private final Usuario autor;

    public Autor(Usuario usuario){
        autor = usuario;
    }

    @Override
    public Boolean cumple(Archivo elemento) {
        return elemento.getPropietario().equals(autor.getMail());
    }
}
