package com.example.endpoints.auth;

import com.example.endpoints.utils.DecodeAndEncode;
import com.example.endpoints.utils.Utils;
import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Usuario;
import com.example.servicios.Servicios;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "AuthArchivo", urlPatterns = Utils.URL_ARCHIVO)
public class AuthArchivo extends HttpFilter {

    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String method = request.getMethod();
        PrintWriter out = response.getWriter();

        if(method.equalsIgnoreCase("DELETE")){

            final String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
                String[] values = DecodeAndEncode.valuesAuth(authorization);
                String usuarioParam = values[0]; // tomcat@gmail.com
                String passwordParam = values[1]; // 1234

                try {
                    Usuario usuario = servicio.getUsuario(usuarioParam);
                    if(usuario.isValid()){
                        if(!credencialesValidasAdmin(usuario, usuarioParam, passwordParam)){
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                            return;
                        }
                    }else{
                        out.print("El usuario no existe!");
                        response.setStatus(Utils.NOT_FOUND);
                        return;
                    }
                }catch (ExcepcionServicio e) {
                    out.print("Error al buscar al usuario");
                    response.setStatus(Utils.INTERNAL_SERVER_ERROR);
                    return;
                }
            }
        }
        out.flush();
        chain.doFilter(request, response);


    }

    private boolean credencialesValidasAdmin(Usuario usuario, String usuarioParam, String passwordParam) {
        String password = usuario.getPassword();
        password = DecodeAndEncode.decode(password);

        return usuarioParam.equalsIgnoreCase(usuario.getMail()) && passwordParam.equals(password) && usuario.esAdmin();
    }


    @Override
    public void destroy() {

    }
}
