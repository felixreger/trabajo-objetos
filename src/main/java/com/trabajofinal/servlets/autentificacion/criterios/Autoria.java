package com.trabajofinal.servlets.autentificacion.criterios;

import com.trabajofinal.utils.servlets.autentificacion.DecodeAndEncode;
import com.trabajofinal.modelo.Usuario;

public class Autoria implements CriterioCredencial{

    private String usuario;
    private String contrasenia;
    private String autor;

    public Autoria(String usuario, String contrasenia, String autor) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.autor = autor;
    }

    @Override
    public Boolean cumple(Usuario u) {
        String password = u.getPassword();
        password = DecodeAndEncode.decode(password);

        return usuario.equals(u.getMail()) && contrasenia.equals(password) && usuario.equals(autor);
    }
}
