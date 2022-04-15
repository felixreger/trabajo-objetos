package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.servlets.endpoints.request.body.JsonBody;
import com.trabajofinal.servlets.endpoints.request.body.JsonBodyBuffer;
import com.trabajofinal.servlets.endpoints.request.requestcontrol.RequestControl;
import com.trabajofinal.utils.servlets.autentificacion.DecodeAndEncode;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.utils.excepciones.ExcepcionServicio;
import com.trabajofinal.servicios.Servicios;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "Usuarios", value = ConstantesServlet.URL_USUARIO)
public class UsuarioServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    /**
     * Se retorna en formato json uno o varios usuarios segun sea especificado.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idUsuario = request.getParameter("idUsuario");
        List<Usuario> usuarios = new ArrayList<>();

        try {
            if (idUsuario == null)
                usuarios.addAll(servicio.getUsuarios());
            else
                usuarios.add(servicio.getUsuario(idUsuario));

            String listaUsuariosJson = this.gson.toJson(usuarios);
            out.print(listaUsuariosJson);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        out.flush();
    }

    /**
     * Se verifica que en la request se indique "usuario y contrasenia".
     * Luego se obtienen todos los parametros necesarios para crear una instancia de Usuario
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        JsonBody body = new JsonBodyBuffer(request);

        final String autorizacion = request.getHeader("Authorization");

        if (autorizacion == null || !autorizacion.toLowerCase().startsWith("basic")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] usuarioYContrasenia = DecodeAndEncode.valuesAutentificacion(autorizacion);
        String idUsuario = usuarioYContrasenia[0];
        String password = usuarioYContrasenia[1];
        password = DecodeAndEncode.encode(password);

        String nombre = body.getString("nombre");
        requestControl.add(nombre);

        if(!requestControl.esRequestValida()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            if(servicio.existeUsuario(idUsuario)) {
                response.setStatus(ConstantesServlet.UNPROCESSABLE_ENTITY);
                return;
            }
            servicio.addUsuario(new Usuario(idUsuario, nombre, 0, password));

        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Se realiza un control de la especificacion de la request.
     * Se obtienen todos los parametros necesarios para modificar la instancia del usuario
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        String idUsuario =(String)request.getAttribute("idUsuario");

        JsonBody body = new JsonBodyBuffer(request);
        String nombre = body.getString("nombre");
        Integer puntaje = body.getInt("puntaje");
        requestControl.addAll(Arrays.asList(nombre, puntaje));

        if(!requestControl.esRequestValida()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            servicio.updateUsuario(new Usuario(idUsuario, nombre, puntaje, false));
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Se elimina al usuario, sabiendo de antemano que existe debido al filter.
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idUsuario =(String)request.getAttribute("idUsuario"); //este atrib. siempre esta por el filter

        try {
            servicio.deleteUsuario(idUsuario);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}