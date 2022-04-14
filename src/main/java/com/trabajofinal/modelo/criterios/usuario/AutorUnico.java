package com.trabajofinal.modelo.criterios.usuario;

import com.trabajofinal.utils.servlets.autentificacion.DecodeAndEncode;
import com.trabajofinal.modelo.Usuario;

public class AutorUnico implements CriterioCredencial{

    private String usuario;
    private String contrasenia;
    private String autor;

    public AutorUnico(String usuario, String contrasenia, String autor) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.autor = autor;
    }

    /**
     * Verifica que el usuario sea el mismo que creó el recurso.
     */
    @Override
    public Boolean cumple(Usuario u) {
        String password = u.getPassword();
        password = DecodeAndEncode.decode(password);

        return usuario.equals(u.getMail()) && contrasenia.equals(password) && usuario.equals(autor);
    }
}
