package com.example.endpoints.auth;

import com.example.endpoints.auth.control.Credencial;
import com.example.endpoints.auth.control.utils.UtilsControl;
import com.example.endpoints.utils.Utils;
import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Comentario;
import com.example.servicios.Servicios;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "AuthComentario", urlPatterns = Utils.URL_COMENTARIO)
public class AuthComentario extends HttpFilter {

    private final Servicios servicio = Servicios.getInstance();
    Credencial controlador = new Credencial();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String method = request.getMethod();
        PrintWriter out = response.getWriter();

        // entro por todos, menos el GET
        if(!method.equalsIgnoreCase("GET")){
            if (!controlador.setUserAndPassword(request, response))
                return;

            // si es POST, solo verifico credenciales correctas
            if(method.equalsIgnoreCase("POST")){
                if(!controlador.verificarCredencial(response, out, UtilsControl.CREDENCIAL_SIMPLE))
                    return;
            }else{
                try {
                    Comentario comentario = servicio.getComentario(Integer.parseInt(request.getParameter("idComentario")));
                    if (!comentario.esValido()){
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print("El comentario no existe!");
                        out.flush();
                        return;
                    }
                    controlador.setAutor(comentario.getAutor().getMail());

                    //solamente quien lo hizo
                    if(method.equalsIgnoreCase("PUT")){
                        if(!controlador.verificarCredencial(response, out, UtilsControl.CREDENCIAL_SIMPLE_UNICA))
                            return;
                    }else{
                        //si es un DELETE, cualquiera de los dos puede
                        if(!controlador.verificarCredencial(response, out, UtilsControl.CREDENCIAL_COMPUESTA))
                            return;
                    }
                } catch (ExcepcionServicio e) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }
            }
        }
        request.setAttribute("idUsuario", controlador.getIdUsuario());
        out.flush();
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
