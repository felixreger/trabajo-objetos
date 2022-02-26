package com.example.modelo.comparadores.usuarios;


import com.example.modelo.Usuario;

public class ComparadorPuntaje extends ComparadorUsuario{
    
    public ComparadorPuntaje() {
        siguiente = null;
    }

    public ComparadorPuntaje(ComparadorUsuario siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public int comparar(Usuario a1, Usuario a2) {
        
        if (a1.getPuntaje() - a2.getPuntaje() == 0 && siguiente!=null){
            return siguiente.comparar(a1, a2);
        }

        return a1.getPuntaje() - a2.getPuntaje();

    }
}