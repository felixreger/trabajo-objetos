package com.trabajofinal.servlets.autentificacion.criterios;

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
     * Criterio que verifica que el usuario y contraseña coincida con lo que se tiene almacenado.
     * @param u que es el usuario que intenta acceder.
     * @return verdadero si las credenciales son correctas, falso en otro caso.
     */
    @Override
    public Boolean cumple(Usuario u) {
        String password = u.getPassword();
        password = DecodeAndEncode.decode(password);

        return usuario.equalsIgnoreCase(u.getMail()) && contrasenia.equals(password) && u.esAdmin();
    }
}
