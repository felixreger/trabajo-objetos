package com.trabajofinal.modelo.comparadores.elementos;


import com.trabajofinal.modelo.Elemento;

public class ComparadorNombre extends ComparadorElemento{

    public ComparadorNombre(){
        siguiente = null;
    }

    public ComparadorNombre(ComparadorElemento comparadorArchivo){
        siguiente = comparadorArchivo;
    }
    
    @Override
    public int comparar(Elemento a1, Elemento a2) {
        
        if (a1.getNombre().compareTo(a2.getNombre()) == 0 && siguiente!=null){
            return siguiente.comparar(a1, a2);
        }

        return a1.getNombre().compareTo(a2.getNombre());

    }
}
