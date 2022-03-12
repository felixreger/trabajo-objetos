package com.example.endpoints.auth.control.criterio;

import com.example.modelo.Usuario;

public class CriterioUsuarioContraseniaAdmin implements CriterioCredencial{

    private String usuario;
    private String contrasenia;
    private String autor;

    public CriterioUsuarioContraseniaAdmin(String usuario, String contrasenia, String autor) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.autor = autor;
    }

    @Override
    public Boolean cumple(Usuario u) {
        CriterioCredencial c = new CriterioUsuarioYContrasenia(usuario, contrasenia);
        if(c.cumple(u)){
            if(autor.equalsIgnoreCase(usuario)) //si es quien hizo el comentario
                return true;
            else
                return u.esAdmin();
        }
        return false;
    }
}
