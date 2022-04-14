package com.trabajofinal.modelo.criterios.archivo;

import com.trabajofinal.modelo.Archivo;
import com.trabajofinal.modelo.Usuario;

public class Autor implements CriterioArchivo {
    private final Usuario autor;

    public Autor(Usuario usuario){
        autor = usuario;
    }

    /**
     Verifica que el archivo pertenezca al autor indicado
     */
    @Override
    public Boolean cumple(Archivo elemento) {
        return elemento.getPropietario().equals(autor.getMail());
    }
}
