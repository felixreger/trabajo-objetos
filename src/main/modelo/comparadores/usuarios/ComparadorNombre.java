package main.modelo.comparadores.usuarios;

import main.modelo.Usuario;

public class ComparadorNombre extends ComparadorUsuario{

    public ComparadorNombre(){
        siguiente = null;
    }

    public ComparadorNombre(ComparadorNombre compararUsuario){
        siguiente = compararUsuario;
    }

    @Override
    public int comparar(Usuario u1, Usuario u2) {
        if (u1.getNombre().compareTo(u2.getNombre()) == 0 && siguiente!=null){
            return siguiente.comparar(u1, u2);
        }

        return u1.getNombre().compareTo(u2.getNombre());
    }
    
}
