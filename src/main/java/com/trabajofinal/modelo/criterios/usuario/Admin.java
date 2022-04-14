package com.trabajofinal.modelo.criterios.usuario;

import com.trabajofinal.utils.servlets.autentificacion.DecodeAndEncode;
import com.trabajofinal.modelo.Usuario;

public class Admin implements CriterioCredencial{

    private String usuario;
    private String contrasenia;

    public Admin(String usuario, String contrasenia) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    /**
     * Verifica que el usuario y contraseña coincida con lo que se tiene almacenado.
     */
    @Override
    public Boolean cumple(Usuario u) {
        String password = u.getPassword();
        password = DecodeAndEncode.decode(password);

        return usuario.equalsIgnoreCase(u.getMail()) && contrasenia.equals(password) && u.esAdmin();
    }
}
