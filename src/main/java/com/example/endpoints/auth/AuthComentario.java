package com.example.endpoints.auth;

import com.example.endpoints.utils.DecodeAndEncode;
import com.example.endpoints.utils.Utils;
import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Comentario;
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

@WebFilter(filterName = "AuthComentario", urlPatterns = Utils.URL_COMENTARIO)
public class AuthComentario extends HttpFilter {

    private final Servicios servicio = Servicios.getInstance();


    //todo: implementar bien, esta horrible.
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String method = request.getMethod();
        PrintWriter out = response.getWriter();

        if(method.equalsIgnoreCase("DELETE") || method.equalsIgnoreCase("PUT") ){
            final String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
                String[] values = DecodeAndEncode.valuesAuth(authorization);
                String usuarioParam = values[0]; // tomcat@gmail.com
                String passwordParam = values[1]; // 1234

                try {
                    Usuario usuario = servicio.getUsuario(usuarioParam);
                    if(usuario.isValid()){

                        if(method.equalsIgnoreCase("DELETE")){

                            Integer idComentario = Integer.parseInt(request.getParameter("idComentario"));
                            Comentario comentario = servicio.getComentario(idComentario);
                            if (!comentario.isValid()){
                                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                                out.print("El comentario no existe!");
                                out.flush();
                                return;
                            }
                            if(!credencialesValidasDelete(usuario, usuarioParam, passwordParam, comentario.getAutor().getMail())){
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                                return;
                            }
                        }else{
                            if(!credencialesValidasPut(usuario, usuarioParam, passwordParam)){
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                                return;
                            }
                        }
                    }else{
                        out.print("El usuario no existe!");
                        response.setStatus(Utils.NOT_FOUND);
                    }
                } catch (ExcepcionServicio e) {
                    out.print("Error al buscar al usuario");
                    response.setStatus(Utils.INTERNAL_SERVER_ERROR);
                }
            }
        }
        out.flush();
        chain.doFilter(request, response);
    }

    // Puede ser modificado solo por quien lo creo.
    //todo: es seguro de esta manera?
    private boolean credencialesValidasPut(Usuario usuario, String usuarioParam, String passwordParam) {
        String password = usuario.getPassword();
        password = DecodeAndEncode.decode(password);

        return usuarioParam.equalsIgnoreCase(usuario.getMail()) && passwordParam.equals(password);
    }

    // El comentario puede ser eliminado por el mismo usuario que lo creo o por un admin.
    //todo: es seguro de esta manera?
    private boolean credencialesValidasDelete(Usuario usuario, String usuarioParam, String passwordParam, String autor) {
        String password = usuario.getPassword();
        password = DecodeAndEncode.decode(password);

        if(usuarioParam.equalsIgnoreCase(usuario.getMail()) && passwordParam.equals(password)){
            //Usuario bien logeado
            if(autor.equalsIgnoreCase(usuarioParam))
                //si es quien hizo el comentario
                return true;
            else
               return usuario.esAdmin();
        }
        return false;
    }

    @Override
    public void destroy() {

    }

}
