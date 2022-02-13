package main.modelo.comparadores.archivos;

import java.util.Comparator;

import main.modelo.Archivo;

public abstract class ComparadorArchivo implements Comparator<Archivo> {
    ComparadorArchivo siguiente;

    @Override
    public int compare(Archivo a1, Archivo a2) {

        int result = comparar(a1, a2);
        if ((result==0) && siguiente!=null)
            return siguiente.comparar(a1, a2);
        return result;
    }

    public abstract int comparar(Archivo a1,Archivo a2);

}
