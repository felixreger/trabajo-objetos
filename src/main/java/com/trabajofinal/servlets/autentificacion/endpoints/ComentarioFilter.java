package com.trabajofinal.servlets.autentificacion.endpoints;

import com.trabajofinal.modelo.Comentario;
import com.trabajofinal.servlets.autentificacion.cors.CorsFilter;
import com.trabajofinal.servlets.autentificacion.credencial.Credencial;
import com.trabajofinal.utils.servlets.autentificacion.ConstantesFilter;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.servicios.Servicios;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthComentario", urlPatterns = ConstantesServlet.URL_COMENTARIO)
public class ComentarioFilter extends HttpFilter {

    private final Servicios servicio = Servicios.getInstance();
    Credencial controlador = new Credencial();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!CorsFilter.habilitarCors(request, response)) return;

        String method = request.getMethod();

        if(!method.equalsIgnoreCase("GET")){
            if (!controlador.setUserAndPassword(request, response))
                return;

            // si es POST, solo verifico credenciales correctas
            if(method.equalsIgnoreCase("POST")){
                if(!controlador.verificarCredencial(response, ConstantesFilter.CREDENCIAL_SIMPLE))
                    return;
            }else{
                try {
                    Comentario comentario = servicio.getComentario(Integer.parseInt(request.getParameter("idComentario")));
                    if (!comentario.esValido()){
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
                    controlador.setAutor(comentario.getAutor().getMail());

                    //Si es un put, solamente quien lo hizo puede modificar. Para delete, cualquiera de los dos pueden eliminar
                    if(!controlador.verificarCredencial(response, this.getCredencialAutor(method)))
                        return;

                } catch (ExcepcionServicio e) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }
            }
            request.setAttribute("idUsuario", controlador.getIdUsuario());
        }
        chain.doFilter(request, response);
    }

    private String getCredencialAutor(String method){
        if(method.equals("PUT"))
            return ConstantesFilter.CREDENCIAL_SIMPLE_UNICA;

        return ConstantesFilter.CREDENCIAL_COMPUESTA;
    }

    @Override
    public void destroy() {

    }

}
