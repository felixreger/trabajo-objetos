package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.modelo.Comentario;
import com.trabajofinal.servlets.endpoints.request.body.JsonBody;
import com.trabajofinal.servlets.endpoints.request.body.JsonBodyBuffer;
import com.trabajofinal.servlets.endpoints.request.requestcontrol.RequestControl;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.servicios.Servicios;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name="Comentario", value= ConstantesServlet.URL_COMENTARIO)
public class ComentarioServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();
    private static int ID_COMENTARIO;

    /**
     * Se obtiene el id del ultimo comentario agregado, para luego
     * autoincrementar este id con nuevos comentarios.
     */
    @Override
    public void init() throws ServletException {
        try {
            ID_COMENTARIO =  servicio.getUltimoComentarioId();
        } catch (ExcepcionServicio e) {
            throw new ServletException(e.getMessage());
        }
    }

    /**
     * Se realiza un control de la especificacion de la request.
     * Luego se retorna en formato json todos los cometarios asociados a un elemento
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        String pathElemento = request.getParameter("pathElemento");
        requestControl.add(pathElemento);

        if(!requestControl.esRequestValida()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            if(!servicio.existeElemento(pathElemento)){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            List<Comentario> comentarios = servicio.getComentarios(pathElemento);
            String comentarioJson = this.gson.toJson(comentarios);
            out.print(comentarioJson);

        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        out.flush();
    }

    /**
     * Se realiza un control de la especificacion de la request.
     * Se obtienen todos los parametros necesarios para crear una instancia de Comentario
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        String idUsuario = (String)request.getAttribute("idUsuario"); //esto siempre esta porque se saca de la auth.

        JsonBody body = new JsonBodyBuffer(request);
        String pathElemento = body.getString("pathElemento");
        Integer idComentario = ++ID_COMENTARIO;
        String contenido = body.getString("contenido");
        requestControl.addAll(Arrays.asList(pathElemento, contenido));

        if(!requestControl.esRequestValida()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

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

    /**
     * Se realiza un control de la especificacion de la request.
     * Se obtienen todos los parametros necesarios para modificar una instancia de Comentario
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        Integer idComentario = Integer.parseInt(request.getParameter("idComentario"));
        String idUsuario = (String)request.getAttribute("idUsuario");

        JsonBody body = new JsonBodyBuffer(request);
        String contenido = body.getString("contenido");
        requestControl.add(contenido);

        if(!requestControl.esRequestValida()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

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

    /**
     * Se elimina el comentario indicada por el id. En este caso, el comentario
     * existe si o si, debido a que antes se verifico el filtro asociado a este metodo
     */
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
