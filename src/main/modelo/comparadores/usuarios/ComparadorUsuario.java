package main.modelo.comparadores.usuarios;

import java.util.Comparator;

import main.modelo.Usuario;

public abstract class ComparadorUsuario implements Comparator<Usuario>{
    ComparadorUsuario siguiente;

    @Override
    public int compare(Usuario u1, Usuario u2) {

        int result = comparar(u1, u2);
        if ((result==0) && siguiente!=null)
            return siguiente.comparar(u1, u2);
        return result;
    }

    public abstract int comparar(Usuario u1,Usuario u2);
}

