package main.modelo.comparadores.elementos;

import main.modelo.Elemento;

public class CompararNombre extends ComparadorElemento{

    public CompararNombre(){
        siguiente = null;
    }

    public CompararNombre(ComparadorElemento comparadorArchivo){
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
