package main.modelo.comparadores.elementos;

import main.modelo.Elemento;

// Esta clase es utilitaria para comparar dos elementos al momento de generar el arbol de directorios.
public class ComparadorDirectorio extends ComparadorElemento{
    
    public ComparadorDirectorio() {
        siguiente = null;
    }

    public ComparadorDirectorio(ComparadorDirectorio comparadorDirectorio) {
        siguiente = comparadorDirectorio;
    }

    @Override
    public int comparar(Elemento a1, Elemento a2) {
        if (a1.getPadre().length() - a2.getPadre().length() == 0 && siguiente!=null){
            return siguiente.comparar(a1, a2);
        }
        return a1.getPadre().length() - a2.getPadre().length();
    }

    
}
