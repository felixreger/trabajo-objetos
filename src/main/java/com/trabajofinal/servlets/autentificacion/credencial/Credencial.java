package com.trabajofinal.servlets.autentificacion.credencial;

import com.trabajofinal.modelo.criterios.usuario.CriterioCredencial;
import com.trabajofinal.servlets.autentificacion.criterios.FabCriterioCredencial;
import com.trabajofinal.utils.servlets.autentificacion.DecodeAndEncode;
import com.trabajofinal.utils.excepciones.ExcepcionServicio;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.servicios.Servicios;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Credencial {

    private String usuarioParam;
    private String passwordParam;
    private String autor = "";
    private final Servicios servicio = Servicios.getInstance();

    /**
     * Metodo para setear el nombre de usuario y contrasenia de la request que intenta acceder a algun recurso.
     * Retorna true si el usuario y contraseña son validos, falso en otro caso(ademas se retorna en error de NO AUTORIZADO)
     */
    public boolean setUserAndPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            String[] values = DecodeAndEncode.valuesAutentificacion(authorization);
            usuarioParam = values[0];
            passwordParam = values[1];
            return true;
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    public String getIdUsuario() {
        return usuarioParam;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

    /**
     * Verifica que el usuario que intenta acceder a un recurso pueda hacerlo, segun sus credenciales.
     * Se utilizan criterios para realizar la operacion.
     * Retorna verdadero si puede acceder sin problemas, falso si no esta autorizado(ademas de un error en el response)
     */
    public boolean verificarCredencial(HttpServletResponse response, String tipoCredencial) throws IOException {
        boolean resultado = true;

        try {
            Usuario usuario = servicio.getUsuario(usuarioParam);
            if(usuario.esValido()){
                FabCriterioCredencial fabrica = new FabCriterioCredencial(usuarioParam, passwordParam, this.autor);
                CriterioCredencial c = fabrica.getCriterio(tipoCredencial);
                if(!c.cumple(usuario)){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    resultado = false;
                }
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resultado = false;
            }
        }catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultado = false;
        }

        return resultado;
    }

}
