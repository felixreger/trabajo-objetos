package com.example.endpoints.auth.control;

import com.example.endpoints.auth.control.criterio.CriterioCredencial;
import com.example.endpoints.auth.control.utils.DecodeAndEncode;
import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Usuario;
import com.example.servicios.Servicios;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

    public boolean credencial(HttpServletResponse response, PrintWriter out, String tipoCredencial) throws IOException {
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
                out.print("El usuario no existe!");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resultado = false;
            }
        }catch (ExcepcionServicio e) {
            out.print("Error al buscar al usuario");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultado = false;
        }

        return resultado;
    }

}
