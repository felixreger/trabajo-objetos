package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.utils.servlets.autentificacion.DecodeAndEncode;
import com.trabajofinal.servlets.endpoints.body.RequestBody;
import com.trabajofinal.utils.servlets.endpoints.Constantes;
import com.trabajofinal.exceptions.ExcepcionServicio;
import com.trabajofinal.servicios.Servicios;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "Usuarios", value = Constantes.URL_USUARIO)
public class Usuario extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String id = request.getParameter("idUsuario");
        List<com.trabajofinal.modelo.Usuario> usuarios = new ArrayList<>();

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
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
                if(servicio.existeUsuario(idUsuario)){
                    response.setStatus(Constantes.UNPROCESSABLE_ENTITY);
                    out.print("El usuario con id " + idUsuario +", ya existe!");
                }else{
                    servicio.addUsuario(new com.trabajofinal.modelo.Usuario(idUsuario, nombre, puntaje, password));
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

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idUsuario =(String)request.getAttribute("idUsuario");

        JSONObject body = RequestBody.getRequestBody(request);
        String nombre = body.getString("nombre");
        int puntaje = body.getInt("puntaje");

        try {
            servicio.updateUsuario(new com.trabajofinal.modelo.Usuario(idUsuario, nombre, puntaje));
            out.print("Usuario actualizado exitosamente!");
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al actualizar el usuario " + idUsuario);
        } finally{
            out.flush();
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String idUsuario =(String)request.getAttribute("idUsuario");

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