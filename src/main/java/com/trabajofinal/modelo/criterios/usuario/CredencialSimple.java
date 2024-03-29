package com.trabajofinal.modelo.criterios.usuario;

import com.trabajofinal.utils.servlets.autentificacion.DecodeAndEncode;
import com.trabajofinal.modelo.Usuario;

public class CredencialSimple implements CriterioCredencial {

    private String usuario;
    private String contrasenia;

    public CredencialSimple(String usuario, String contrasenia) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    /**
     * Verifica unicamente "usuario y contraseña" correctas.
     */
    public Boolean cumple(Usuario u) {
        String password = u.getPassword();
        password = DecodeAndEncode.decode(password);

        return usuario.equalsIgnoreCase(u.getMail()) && contrasenia.equals(password);
    }
}
