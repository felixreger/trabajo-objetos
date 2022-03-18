package com.trabajofinal.modelo.comparadores.elementos;


import com.trabajofinal.modelo.Elemento;

public class ComparadorTamanio extends ComparadorElemento {

    public ComparadorTamanio() {
        siguiente = null;
    }

    public ComparadorTamanio(ComparadorElemento comparadorArchivo) {
        siguiente = comparadorArchivo;
    }

    @Override
    public int comparar(Elemento a1, Elemento a2) {
        if (a1.getTamanio()-a2.getTamanio() == 0 && siguiente!=null){
            return siguiente.comparar(a1, a2);
        }
        return Math.toIntExact(a1.getTamanio() - a2.getTamanio());
    }
}