package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.modelo.Comentario;
import com.trabajofinal.servlets.endpoints.body.RequestBody;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.servicios.Servicios;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="Comentario", value= ConstantesServlet.URL_COMENTARIO)
public class ComentarioServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();
    private static int ID_COMENTARIO;

    @Override
    public void init() throws ServletException {
        try {
            ID_COMENTARIO =  servicio.getUltimoComentarioId();
        } catch (ExcepcionServicio e) {
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathElemento = request.getParameter("pathElemento");
        List<Comentario> comentarios;

        try {
            if(!servicio.existeElemento(pathElemento)){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            comentarios = servicio.getComentarios(pathElemento);
            String comentarioJson = this.gson.toJson(comentarios);
            out.print(comentarioJson);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idUsuario = (String)request.getAttribute("idUsuario");
        JSONObject body = RequestBody.getRequestBody(request);

        String pathElemento = body.getString("pathElemento");
        Integer idComentario = ++ID_COMENTARIO;
        String contenido = body.getString("contenido");

        try {
            if (servicio.existeComentario(idComentario)){
                response.setStatus(ConstantesServlet.UNPROCESSABLE_ENTITY);
                return;
            }
            if(!servicio.existeElemento(pathElemento)){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Usuario usuario = servicio.getUsuario(idUsuario);
            Comentario cm = new Comentario(idComentario, contenido, usuario, pathElemento);
            servicio.addComentario(cm);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Integer idComentario = Integer.parseInt(request.getParameter("idComentario"));

        String idUsuario = (String)request.getAttribute("idUsuario");
        JSONObject body = RequestBody.getRequestBody(request);
        String contenido = body.getString("contenido");

        try {
            Usuario usuario = servicio.getUsuario(idUsuario);
            if(!servicio.existeComentario(idComentario)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            String idElemento = servicio.getComentario(idComentario).getNombreElemento();
            Comentario cm = new Comentario(idComentario, contenido, usuario,  idElemento);
            servicio.updateComentario(cm);

        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Integer idComentario = Integer.parseInt(request.getParameter("idComentario"));
        try {
            servicio.deleteComentario(idComentario);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
