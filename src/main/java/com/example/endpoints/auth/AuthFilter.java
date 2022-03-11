package com.example.endpoints.auth;


import com.example.endpoints.utils.Utils;
import com.example.exceptions.ExcepcionServicio;
import com.example.servicios.Servicios;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "SecurityFilter",
        urlPatterns = {Utils.URL_USUARIO, Utils.URL_CARPETA, Utils.URL_ARCHIVO, Utils.URL_CATEDRA, Utils.URL_COMENTARIO})
public class AuthFilter extends HttpFilter {

    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String method = req.getMethod();
        if(method.equalsIgnoreCase("DELETE")){
            String usuario = req.getParameter("idUsuario");

            try { //todo: verificar que exista el usuario
                if(!servicio.esAdmin(usuario)){
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } catch (ExcepcionServicio e) {
                res.setStatus(Utils.INTERNAL_SERVER_ERROR);
                return;
            }
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
