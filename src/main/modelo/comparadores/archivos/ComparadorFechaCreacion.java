package main.modelo.comparadores.archivos;

import main.modelo.Archivo;

public class ComparadorFechaCreacion extends ComparadorArchivo {

    public ComparadorFechaCreacion() {
        siguiente = null;
    }

    public ComparadorFechaCreacion(ComparadorArchivo comparadorArchivo) {
        siguiente = comparadorArchivo;
    }

    @Override
    public int comparar(Archivo a1, Archivo a2) {
        if (a1.getFechaCreacion().equals(a2.getFechaCreacion()) && siguiente!=null){
            return siguiente.comparar(a1, a2);
        }
        return a1.getFechaCreacion().isAfter(a2.getFechaCreacion()) ? 1 : -1;
    }
}