package com.example.endpoints;

import com.example.endpoints.auth.control.utils.DecodeAndEncode;
import com.example.endpoints.utils.Utils;
import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Usuario;
import com.example.servicios.Servicios;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "Usuarios", value = Utils.URL_USUARIO)
public class UsuarioEndpoint extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    //todos pueden acceder
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String id = request.getParameter("idUsuario");
        List<Usuario> usuarios = new ArrayList<>();

        try {
            if (id == null)
                usuarios.addAll(servicio.getUsuarios());
            else
                usuarios.add(servicio.getUsuario(id));

            String listaUsuariosJson = this.gson.toJson(usuarios);
            out.print(listaUsuariosJson);

        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al cargar el/los usuario/s");
        }finally {
            out.flush();
        }
    }

    //se necesita de usuario y contraseña para dar de alta
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = Utils.getRequestBody(request);

        final String autorizacion = request.getHeader("Authorization");
        if (autorizacion != null && autorizacion.toLowerCase().startsWith("basic")) {
            String[] usuarioYContrasenia = DecodeAndEncode.valuesAutentificacion(autorizacion);
            String idUsuario = usuarioYContrasenia[0];
            String password = usuarioYContrasenia[1];
            password = DecodeAndEncode.encode(password);

            String nombre = body.getString("nombre");
            int puntaje = body.getInt("puntaje");

            try {
                if(servicio.existeUsuario(idUsuario)){
                    response.setStatus(Utils.UNPROCESSABLE_ENTITY);
                    out.print("El usuario con id " + idUsuario +", ya existe!");
                }else{
                    servicio.addUsuario(new Usuario(idUsuario, nombre, puntaje, password));
                    out.print("Usuario guardado exitosamente!");
                }
            } catch (ExcepcionServicio e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Error al guardar el usuario");
            }finally {
                out.flush();
            }
        }
    }

    // todo: cambiar parametros en la query
    // el mismo usuario puede hacer un update a si mismo
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idUsuario =(String)request.getAttribute("idUsuario");

        JSONObject body = Utils.getRequestBody(request);
        String nombre = body.getString("nombre");
        int puntaje = body.getInt("puntaje");

        // Si entra hasta aca, es porque existe y ya dio usuario y contraseña.
        try {
            servicio.updateUsuario(new Usuario(idUsuario, nombre, puntaje));
            out.print("Usuario actualizado exitosamente!");
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al actualizar el usuario " + idUsuario);
        } finally{
            out.flush();
        }
    }

    // todo: cambiar parametros en la query
    //solo el mismo puede eliminarse
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String idUsuario =(String)request.getAttribute("idUsuario");

        //Ya se verifica que exista el usuario y que tenga las credenciales correctas.
        try {
            servicio.deleteUsuario(idUsuario);
            out.print("Usuario " + idUsuario + " eliminado exitosamente!");
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al eliminar el usuario " + idUsuario);
        }finally {
            out.flush();
        }
    }
}