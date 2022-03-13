package com.example.endpoints.auth.control.criterio;

import com.example.endpoints.auth.control.utils.DecodeAndEncode;
import com.example.modelo.Usuario;

public class CriterioUsuarioAutor implements CriterioCredencial{

    private String usuario;
    private String contrasenia;
    private String autor; //todo: acomodar nombre

    public CriterioUsuarioAutor(String usuario, String contrasenia, String autor) {
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
