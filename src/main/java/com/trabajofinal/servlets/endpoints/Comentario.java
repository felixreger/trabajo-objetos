package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.servlets.endpoints.body.RequestBody;
import com.trabajofinal.utils.servlets.endpoints.Constantes;
import com.trabajofinal.exceptions.ExcepcionServicio;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.servicios.Servicios;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="Comentario", value= Constantes.URL_COMENTARIO)
public class Comentario extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathElemento = request.getParameter("pathElemento");
        List<com.trabajofinal.modelo.Comentario> comentarios;

        try {
            if(servicio.existeElemento(pathElemento)){
                comentarios = servicio.getComentarios(pathElemento);
                String comentarioJson = this.gson.toJson(comentarios);
                out.print(comentarioJson);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("No existe el elmento asociado al comentario");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al buscar los comentarios ");
        }finally {
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idUsuario = (String)request.getAttribute("idUsuario");
        JSONObject body = RequestBody.getRequestBody(request);

        String pathElemento = body.getString("pathElemento");
        Integer idComentario = body.getInt("idComentario");
        String contenido = body.getString("contenido");

        try {
            if (servicio.existeComentario(idComentario)){
                response.setStatus(Constantes.UNPROCESSABLE_ENTITY);
                out.print("El comentario ya existe!");
                out.flush();
                return;
            }
            if(!servicio.existeElemento(pathElemento)){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("El elemento asociado al comentario no existe!");
                out.flush();
                return;
            }
            Usuario usuario = servicio.getUsuario(idUsuario);
            com.trabajofinal.modelo.Comentario cm = new com.trabajofinal.modelo.Comentario(idComentario, contenido, usuario, pathElemento);
            System.out.println("Comentario " + cm);
            servicio.addComentario(cm);
            out.print("Comentario agregado correctamente");

        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al agregar el comentario " + contenido);
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Integer idComentario = Integer.parseInt(request.getParameter("idComentario"));

        String idUsuario = (String)request.getAttribute("idUsuario"); //es el autor del comentario
        JSONObject body = RequestBody.getRequestBody(request);
        String contenido = body.getString("contenido");

        try {
            Usuario usuario = servicio.getUsuario(idUsuario);
            if(!servicio.existeComentario(idComentario)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("El comentario no existe!");
            } else{
                String idElemento = servicio.getComentario(idComentario).getNombreElemento();
                com.trabajofinal.modelo.Comentario cm = new com.trabajofinal.modelo.Comentario(idComentario, contenido, usuario,  idElemento);
                servicio.updateComentario(cm);
                out.print("Comentario actualizado correctamente");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al actualizar el comentario " + contenido);
        }finally {
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Integer idComentario = Integer.parseInt(request.getParameter("idComentario"));
        try {
            servicio.deleteComentario(idComentario);
            out.print("Comentario eliminado correctamente");
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al eliminar el comentario");
        }finally{
            out.flush();
        }
    }
}
