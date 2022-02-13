package main.modelo.comparadores.archivos;

import main.modelo.Archivo;

public class CompararNombre extends ComparadorArchivo{

    public CompararNombre(){
        siguiente = null;
    }

    public CompararNombre(ComparadorArchivo comparadorArchivo){
        siguiente = comparadorArchivo;
    }
    
    @Override
    public int comparar(Archivo a1, Archivo a2) {
        
        if (a1.getNombre().compareTo(a2.getNombre()) == 0 && siguiente!=null){
            return siguiente.comparar(a1, a2);
        }

        return a1.getNombre().compareTo(a2.getNombre());

    }
}
