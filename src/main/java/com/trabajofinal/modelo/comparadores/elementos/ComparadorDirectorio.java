package com.trabajofinal.modelo.comparadores.elementos;


import com.trabajofinal.modelo.Elemento;

public class ComparadorDirectorio extends ComparadorElemento {

    public ComparadorDirectorio() {
        siguiente = null;
    }

    public ComparadorDirectorio(ComparadorDirectorio comparadorDirectorio) {
        siguiente = comparadorDirectorio;
    }

    @Override
    public int comparar(Elemento a1, Elemento a2) {
        if (a1.getPath().length() - a2.getPath().length() == 0 && siguiente != null) {
            return siguiente.comparar(a1, a2);
        }
        return a2.getPath().length() - a1.getPath().length();
    }

}
