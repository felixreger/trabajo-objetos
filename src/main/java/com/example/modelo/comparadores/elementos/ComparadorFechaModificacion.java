package com.example.modelo.comparadores.elementos;


import com.example.modelo.Elemento;

public class ComparadorFechaModificacion extends ComparadorElemento {

    public ComparadorFechaModificacion() {
        siguiente = null;
    }

    public ComparadorFechaModificacion(ComparadorElemento comparadorArchivo) {
        siguiente = comparadorArchivo;
    }

    @Override
    public int comparar(Elemento a1, Elemento a2) {
        if (a1.getFechaModificacion().equals(a2.getFechaModificacion()) && siguiente!=null){
            return siguiente.comparar(a1, a2);
        }
        return a1.getFechaModificacion().isAfter(a2.getFechaModificacion()) ? 1 : -1;
    }
}