package com.example.endpoints.auth;

import com.example.endpoints.auth.control.Credencial;
import com.example.endpoints.auth.control.utils.UtilsControl;
import com.example.endpoints.utils.Utils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "AuthUsuario", urlPatterns = Utils.URL_USUARIO)
public class AuthUsuario extends HttpFilter {

    Credencial controlador = new Credencial();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String method = request.getMethod();
        PrintWriter out = response.getWriter();

        if(method.equalsIgnoreCase("DELETE") || method.equalsIgnoreCase("PUT") ){
            if (!controlador.setUserAndPassword(request, response) || !controlador.credencial(response, out, UtilsControl.CREDENCIAL_SIMPLE))
                return;
        }
        request.setAttribute("idUsuario", controlador.getIdUsuario());
        out.flush();
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}











