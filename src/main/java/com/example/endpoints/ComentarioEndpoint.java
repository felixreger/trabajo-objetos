package com.example.endpoints;

import com.example.endpoints.utils.Utils;
import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Comentario;
import com.example.modelo.Usuario;
import com.example.servicios.Servicios;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="Comentario", value=Utils.URL_COMENTARIO)
public class ComentarioEndpoint extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idElemento = request.getParameter("idElemento");
        List<Comentario> comentarios;

        try {
            if(servicio.existeElemento(idElemento)){
                comentarios = servicio.getComentarios(idElemento);
                String comentarioJson = this.gson.toJson(comentarios);
                out.print(comentarioJson);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("No existe " + idElemento);
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al buscar los comentarios ");
        }finally {
            out.flush();
        }
    }

    //todos pueden acceder
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = Utils.getRequestBody(request);

        String idElemento = body.getString("idElemento");
        Integer idComentario = body.getInt("idComentario");
        String contenido = body.getString("contenido");
        String nombreAutor = body.getString("idUsuario");

        try {
            if (servicio.existeComentario(idComentario)){
                response.setStatus(Utils.UNPROCESSABLE_ENTITY);
                out.print("El comentario ya existe!");
                out.flush();
                return;
            }
            Usuario usuario = servicio.getUsuario(nombreAutor);
            if (usuario.esValido()){
                Comentario cm = new Comentario(idComentario, contenido, usuario, idElemento);
                servicio.addComentario(cm);
                out.print("Comentario agregado correctamente");
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("El usuario no existe!");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al agregar el comentario " + contenido);
        } finally {
            out.flush();
        }
    }

    //solo el que hizo el comentario puede modificarlo
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idUsuario = (String)request.getAttribute("idUsuario"); //es el autor del comentario

        JSONObject body = Utils.getRequestBody(request);
        Integer idComentario =body.getInt("idComentario");
        String contenido = body.getString("contenido");
        String idElemento = "";

        try {
            Comentario tmp = servicio.getComentario(idComentario);
            if (!tmp.esValido()){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("El comentario no existe!");
                out.flush();
                return;
            }
            idElemento = tmp.getNombreElemento();
            Usuario usuario = servicio.getUsuario(idUsuario);
            Comentario cm = new Comentario(idComentario, contenido, usuario, idElemento);
            servicio.updateComentario(cm);
            out.print("Comentario actualizado correctamente");
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al actualizar el comentario " + contenido);
        }finally {
            out.flush();
        }
    }

    //los admins y los autores pueden eliminar
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
