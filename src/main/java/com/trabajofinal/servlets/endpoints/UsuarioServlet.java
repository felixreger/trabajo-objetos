package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.utils.servlets.autentificacion.DecodeAndEncode;
import com.trabajofinal.servlets.endpoints.body.RequestBody;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.servicios.Servicios;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "Usuarios", value = ConstantesServlet.URL_USUARIO)
public class UsuarioServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = RequestBody.getRequestBody(request);

        final String autorizacion = request.getHeader("Authorization");
        if (autorizacion != null && autorizacion.toLowerCase().startsWith("basic")) {
            String[] usuarioYContrasenia = DecodeAndEncode.valuesAutentificacion(autorizacion);
            String idUsuario = usuarioYContrasenia[0];
            String password = usuarioYContrasenia[1];
            password = DecodeAndEncode.encode(password);

            String nombre = body.getString("nombre");
            int puntaje = body.getInt("puntaje");

            try {
                if(servicio.existeUsuario(idUsuario)) {
                    response.setStatus(ConstantesServlet.UNPROCESSABLE_ENTITY);
                    return;
                }
                servicio.addUsuario(new Usuario(idUsuario, nombre, puntaje, password));
            } catch (ExcepcionServicio e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idUsuario =(String)request.getAttribute("idUsuario");

        JSONObject body = RequestBody.getRequestBody(request);
        String nombre = body.getString("nombre");
        int puntaje = body.getInt("puntaje");

        try {
            servicio.updateUsuario(new Usuario(idUsuario, nombre, puntaje));
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idUsuario =(String)request.getAttribute("idUsuario");

        try {
            servicio.deleteUsuario(idUsuario);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}