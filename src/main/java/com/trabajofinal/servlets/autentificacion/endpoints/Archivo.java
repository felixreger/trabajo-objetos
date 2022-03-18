package com.trabajofinal.servlets.autentificacion.endpoints;

import com.trabajofinal.servlets.autentificacion.cors.CorsFilter;
import com.trabajofinal.servlets.autentificacion.credencial.Credencial;
import com.trabajofinal.utils.servlets.endpoints.Constantes;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthArchivo", urlPatterns = Constantes.URL_ARCHIVO)
public class Archivo extends HttpFilter {

    Credencial controlador = new Credencial();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!CorsFilter.habilitarCors(request, response)) return;

        String method = request.getMethod();
        if(method.equalsIgnoreCase("DELETE") || method.equalsIgnoreCase("POST")) {
            if (!controlador.setUserAndPassword(request, response))
                return;
            if(method.equalsIgnoreCase("DELETE")){
                if(!controlador.verificarCredencial(response, com.trabajofinal.utils.servlets.autentificacion.Constantes.CREDENCIAL_ADMIN))
                    return;
            }
        }
        request.setAttribute("idUsuario", controlador.getIdUsuario());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
