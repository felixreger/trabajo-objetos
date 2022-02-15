package main.modelo.comparadores.elementos;

import main.modelo.Elemento;

public class ComparadorFechaCreacion extends ComparadorElemento {

    public ComparadorFechaCreacion() {
        siguiente = null;
    }

    public ComparadorFechaCreacion(ComparadorElemento comparadorArchivo) {
        siguiente = comparadorArchivo;
    }

    @Override
    public int comparar(Elemento a1, Elemento a2) {
        if (a1.getFechaCreacion().equals(a2.getFechaCreacion()) && siguiente!=null){
            return siguiente.comparar(a1, a2);
        }
        return a1.getFechaCreacion().isAfter(a2.getFechaCreacion()) ? 1 : -1;
    }
}