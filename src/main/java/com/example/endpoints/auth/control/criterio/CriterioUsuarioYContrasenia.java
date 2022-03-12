package com.example.endpoints.auth.control.criterio;

import com.example.endpoints.auth.control.utils.DecodeAndEncode;
import com.example.modelo.Usuario;

public class CriterioUsuarioYContrasenia implements CriterioCredencial {

    private String usuario;
    private String contrasenia;

    public CriterioUsuarioYContrasenia(String usuario, String contrasenia) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public Boolean cumple(Usuario u) {
        String password = u.getPassword();
        password = DecodeAndEncode.decode(password);

        return usuario.equalsIgnoreCase(u.getMail()) && contrasenia.equals(password);
    }
}
