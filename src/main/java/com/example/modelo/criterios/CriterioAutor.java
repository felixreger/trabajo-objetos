package com.example.modelo.criterios;


import com.example.modelo.Elemento;
import com.example.modelo.Usuario;

public class CriterioAutor implements Criterio {
    private Usuario autor;

    public Usuario getAutor() {
        return this.autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public CriterioAutor(Usuario usuario){
        autor = usuario;
    }

    @Override
    public Boolean cumple(Elemento elemento) {
        return elemento.equals(elemento);
    }
}
