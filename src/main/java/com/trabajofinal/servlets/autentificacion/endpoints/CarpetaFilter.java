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

@WebFilter(filterName = "AuthCarpeta", urlPatterns = ConstantesServlet.URL_CARPETA)
public class CarpetaFilter extends HttpFilter {

    Credencial controlador = new Credencial();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!CorsFilter.habilitarCors(request, response)) return;

        String method = request.getMethod();
        if(method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("DELETE")){
            if (!controlador.setUserAndPassword(request, response))
                return;
            if(!controlador.verificarCredencial(response, ConstantesFilter.CREDENCIAL_ADMIN))
                return;
        }
        request.setAttribute("idUsuario", controlador.getIdUsuario());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}