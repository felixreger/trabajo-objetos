package com.trabajofinal.servlets.autentificacion.endpoints;

import com.trabajofinal.servlets.autentificacion.cors.CorsFilter;
import com.trabajofinal.servlets.autentificacion.credencial.Credencial;
import com.trabajofinal.utils.servlets.autentificacion.ConstantesFilter;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthUsuario", urlPatterns = ConstantesServlet.URL_USUARIO)
public class UsuarioFilter extends HttpFilter {

    Credencial controlador = new Credencial();

    /**
     * Si el usuario o contrasenia es incorrecto, entonces, termina el metodo.
     * Sino se verifica la credencial segun el tipo de metodo
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!CorsFilter.habilitarCors(request, response)) return;

        String method = request.getMethod();
        if(method.equalsIgnoreCase("DELETE") || method.equalsIgnoreCase("PUT") ){
            if (!controlador.setUserAndPassword(request, response) || !controlador.verificarCredencial(response, ConstantesFilter.CREDENCIAL_SIMPLE))
                return;
        }
        request.setAttribute("idUsuario", controlador.getIdUsuario()); //este atributo se utiliza en el endpoint
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}











