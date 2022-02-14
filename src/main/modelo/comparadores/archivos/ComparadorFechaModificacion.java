package main.modelo.comparadores.archivos;

import main.modelo.Archivo;

public class ComparadorFechaModificacion extends ComparadorArchivo {

    public ComparadorFechaModificacion() {
        siguiente = null;
    }

    public ComparadorFechaModificacion(ComparadorArchivo comparadorArchivo) {
        siguiente = comparadorArchivo;
    }

    @Override
    public int comparar(Archivo a1, Archivo a2) {
        if (a1.getFechaModificacion().equals(a2.getFechaModificacion()) && siguiente!=null){
            return siguiente.comparar(a1, a2);
        }
        return a1.getFechaModificacion().isAfter(a2.getFechaModificacion()) ? 1 : -1;
    }
}