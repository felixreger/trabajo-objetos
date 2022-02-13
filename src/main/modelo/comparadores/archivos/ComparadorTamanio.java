package main.modelo.comparadores.archivos;

import main.modelo.Archivo;

public class ComparadorTamanio extends ComparadorArchivo {

    public ComparadorTamanio() {
        siguiente = null;
    }

    public ComparadorTamanio(ComparadorArchivo comparadorArchivo) {
        siguiente = comparadorArchivo;
    }

    @Override
    public int comparar(Archivo a1, Archivo a2) {
        if (a1.getTamanio()-a2.getTamanio() == 0 && siguiente!=null){
            return siguiente.comparar(a1, a2);
        }
        return a1.getTamanio()-a2.getTamanio();
    }
}