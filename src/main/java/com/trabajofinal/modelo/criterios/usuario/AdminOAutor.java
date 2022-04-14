package com.trabajofinal.modelo.criterios.usuario;

import com.trabajofinal.modelo.Usuario;

public class AdminOAutor implements CriterioCredencial{

    private String usuario;
    private String contrasenia;
    private String autor;

    public AdminOAutor(String usuario, String contrasenia, String autor) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.autor = autor;
    }

    /**
     * Primero se verica "usuario y contrasenia". Luego si esto es asi,
     * retorna verdadero en caso de que sea autor o admin.
     */
    @Override
    public Boolean cumple(Usuario u) {
        CriterioCredencial c = new CredencialSimple(usuario, contrasenia);
        if(c.cumple(u)){
            if(autor.equalsIgnoreCase(usuario))
                return true;
            else
                return u.esAdmin();
        }
        return false;
    }
}
