package com.trabajofinal.modelo.comparadores.elementos;


import com.trabajofinal.modelo.Elemento;

import java.util.Comparator;

public abstract class ComparadorElemento implements Comparator<Elemento> {
    ComparadorElemento siguiente;

    @Override
    public int compare(Elemento a1, Elemento a2) {

        int result = comparar(a1, a2);
        if ((result==0) && siguiente!=null)
            return siguiente.comparar(a1, a2);
        return result;
    }

    public abstract int comparar(Elemento a1,Elemento a2);

}
