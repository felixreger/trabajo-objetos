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
