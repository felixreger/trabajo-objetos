package com.example.endpoints.auth.control.criterio;

import com.example.endpoints.auth.control.utils.DecodeAndEncode;
import com.example.modelo.Usuario;

public class CriterioEsAdmin implements CriterioCredencial{

    private String usuario;
    private String contrasenia;

    public CriterioEsAdmin(String usuario, String contrasenia) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    @Override
    public Boolean cumple(Usuario u) {
        String password = u.getPassword();
        password = DecodeAndEncode.decode(password);

        return usuario.equalsIgnoreCase(u.getMail()) && contrasenia.equals(password) && u.esAdmin();
    }
}
