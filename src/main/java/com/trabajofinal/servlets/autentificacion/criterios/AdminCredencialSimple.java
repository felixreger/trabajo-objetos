package com.trabajofinal.servlets.autentificacion.criterios;

import com.trabajofinal.modelo.Usuario;

public class AdminCredencialSimple implements CriterioCredencial{

    private String usuario;
    private String contrasenia;
    private String autor;

    public AdminCredencialSimple(String usuario, String contrasenia, String autor) {
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
