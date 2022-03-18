package com.trabajofinal.servlets.autentificacion.credencial;

import com.trabajofinal.servlets.autentificacion.criterios.CriterioCredencial;
import com.trabajofinal.servlets.autentificacion.criterios.FabricaCriterio;
import com.trabajofinal.utils.servlets.autentificacion.DecodeAndEncode;
import com.trabajofinal.exceptions.ExcepcionServicio;
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

    public boolean verificarCredencial(HttpServletResponse response, String tipoCredencial) throws IOException {
        boolean resultado = true;

        try {
            Usuario usuario = servicio.getUsuario(usuarioParam);
            if(usuario.esValido()){
                FabricaCriterio fabrica = new FabricaCriterio();
                CriterioCredencial c = fabrica.getCriterioCredencial(tipoCredencial, usuarioParam, passwordParam, this.autor);
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
