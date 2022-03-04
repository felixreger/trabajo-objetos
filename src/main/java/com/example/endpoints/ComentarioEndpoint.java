package com.example.endpoints;

import com.example.endpoints.utils.Utils;
import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Comentario;
import com.example.modelo.Usuario;
import com.example.servicios.Servicios;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="Comentario", value="/comentario")
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
            comentarios = servicio.getComentarios(idElemento);
            String comentarioJson = this.gson.toJson(comentarios);
            out.print(comentarioJson);
        } catch (ExcepcionServicio e) {
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

        JSONObject body = Utils.getRequestBody(request);

        String idElemento = body.getString("idElemento");
        Integer idComentario = body.getInt("idComentario");
        String contenido = body.getString("contenido");
        String nombreAutor = body.getString("nombreAutor");

        try {
            if (servicio.getComentario(idComentario).isValid()){
                response.setStatus(422);
                out.print("El comentario ya existe!");
                out.flush();
                return;
            }
        } catch (ExcepcionServicio e) {
            e.printStackTrace();
        }

        try {
            Usuario usuario = servicio.getUsuario(nombreAutor);
            Comentario cm = new Comentario(idComentario, contenido, usuario, idElemento);
            servicio.addComentario(cm);
        } catch (ExcepcionServicio e) {
            out.print("Error al agregar el comentario " + contenido);
        }finally {
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = Utils.getRequestBody(request);

        Integer idComentario =body.getInt("idComentario");
        String contenido = body.getString("contenido");
        String nombreAutor = body.getString("nombreAutor");
        String idElemento = "";

        try {
            Comentario tmp = servicio.getComentario(idComentario);
            if (!tmp.isValid()){
                response.setStatus(422);
                out.print("El comentario no existe!");
                out.flush();
                return;
            }
            idElemento = tmp.getNombreElemento();
        } catch (ExcepcionServicio e) {
            e.printStackTrace();
        }

        try {
            Usuario usuario = servicio.getUsuario(nombreAutor);
            Comentario cm = new Comentario(idComentario, contenido, usuario, idElemento);
            servicio.updateComentario(cm);
        } catch (ExcepcionServicio e) {
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

        Integer idComentario =Integer.parseInt(request.getParameter("idComentario"));

        try {
            Comentario tmp = servicio.getComentario(idComentario);
            if (!tmp.isValid()){
                response.setStatus(422);
                out.print("El comentario no existe!");
                out.flush();
                return;
            }
        } catch (ExcepcionServicio e) {
            e.printStackTrace();
        }

        try {
            servicio.deleteComentario(idComentario);
        } catch (ExcepcionServicio e) {
            out.print("Error al eliminar el comentario");
        }finally{
            out.flush();
        }
    }
}
