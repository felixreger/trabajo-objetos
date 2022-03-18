package com.trabajofinal.servlets.autentificacion.endpoints;

import com.trabajofinal.servlets.autentificacion.credencial.Credencial;
import com.trabajofinal.utils.servlets.endpoints.Constantes;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthArchivoFuente", urlPatterns = Constantes.URL_FUENTE)
public class ArchivoFuente extends HttpFilter {
    Credencial controlador = new Credencial();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String method = request.getMethod();

        if(method.equalsIgnoreCase("GET")) {
            if (!controlador.setUserAndPassword(request, response))
                return;
            if(!controlador.verificarCredencial(response, com.trabajofinal.utils.servlets.autentificacion.Constantes.CREDENCIAL_SIMPLE))
                return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
