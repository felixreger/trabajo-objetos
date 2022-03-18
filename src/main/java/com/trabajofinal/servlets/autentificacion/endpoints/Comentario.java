package com.trabajofinal.servlets.autentificacion.endpoints;

import com.trabajofinal.servlets.autentificacion.cors.CorsFilter;
import com.trabajofinal.servlets.autentificacion.credencial.Credencial;
import com.trabajofinal.utils.servlets.endpoints.Constantes;
import com.trabajofinal.exceptions.ExcepcionServicio;
import com.trabajofinal.servicios.Servicios;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "AuthComentario", urlPatterns = Constantes.URL_COMENTARIO)
public class Comentario extends HttpFilter {

    private final Servicios servicio = Servicios.getInstance();
    Credencial controlador = new Credencial();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!CorsFilter.habilitarCors(request, response)) return;

        String method = request.getMethod();
        PrintWriter out = response.getWriter();

        if(!method.equalsIgnoreCase("GET")){
            if (!controlador.setUserAndPassword(request, response))
                return;

            // si es POST, solo verifico credenciales correctas
            if(method.equalsIgnoreCase("POST")){
                if(!controlador.verificarCredencial(response, com.trabajofinal.utils.servlets.autentificacion.Constantes.CREDENCIAL_SIMPLE))
                    return;
            }else{
                try {
                    com.trabajofinal.modelo.Comentario comentario = servicio.getComentario(Integer.parseInt(request.getParameter("idComentario")));
                    if (!comentario.esValido()){
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print("El comentario no existe!");
                        out.flush();
                        return;
                    }
                    controlador.setAutor(comentario.getAutor().getMail());

                    //solamente quien lo hizo
                    if(method.equalsIgnoreCase("PUT")){
                        if(!controlador.verificarCredencial(response, com.trabajofinal.utils.servlets.autentificacion.Constantes.CREDENCIAL_SIMPLE_UNICA))
                            return;
                    }else{
                        //si es un DELETE, cualquiera de los dos puede
                        if(!controlador.verificarCredencial(response, com.trabajofinal.utils.servlets.autentificacion.Constantes.CREDENCIAL_COMPUESTA))
                            return;
                    }
                } catch (ExcepcionServicio e) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }
            }
            request.setAttribute("idUsuario", controlador.getIdUsuario());
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
