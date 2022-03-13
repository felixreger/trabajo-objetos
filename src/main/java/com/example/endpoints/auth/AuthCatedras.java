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

@WebFilter(filterName = "AuthCatedra", urlPatterns = Utils.URL_CATEDRA)
public class AuthCatedras extends HttpFilter {

    Credencial controlador = new Credencial();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String method = request.getMethod();
        PrintWriter out = response.getWriter();
        if(!method.equalsIgnoreCase("GET")) { //post, put y delete.
            if (!controlador.setUserAndPassword(request, response))
                return;
            if(!controlador.verificarCredencial(response, out, UtilsControl.CREDENCIAL_ADMIN))
                return;
        }
        out.flush();
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
