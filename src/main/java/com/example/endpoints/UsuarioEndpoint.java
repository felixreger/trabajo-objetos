package com.example.endpoints;

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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        List<Usuario> usuarios = new ArrayList<>();

        try {
            if (id == null)
                usuarios.addAll(servicio.getUsuarios());
            else
                usuarios.add(servicio.getUsuario(id));

        } catch (ExcepcionServicio e) {
            response.setStatus(Utils.INTERNAL_SERVER_ERROR);
            out.print("Error al cargar el/los usuario/s");
            out.flush();
            return;
        }

        String listaUsuariosJson = this.gson.toJson(usuarios);
        out.print(listaUsuariosJson);
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        JSONObject body = Utils.getRequestBody(request);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = body.getString("id");
        String nombre = body.getString("nombre");
        int puntaje = body.getInt("puntaje");

        try {
            if(servicio.existeUsuario(id)){
                response.setStatus(Utils.UNPROCESSABLE_ENTITY);
                out.print("El usuario con id " + id +", ya existe!");
                out.flush();
                return;
            }
            servicio.addUsuario(new Usuario(id, nombre, puntaje));
            out.print("Usuario guardado exitosamente!");

        } catch (ExcepcionServicio e) {
            response.setStatus(Utils.INTERNAL_SERVER_ERROR);
            out.print("Error al guardar el usuario");
        }finally {
            out.flush();
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        JSONObject body = Utils.getRequestBody(request);

        String id = body.getString("id");
        String nombre = body.getString("nombre");
        int puntaje = body.getInt("puntaje");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            if(!servicio.existeUsuario(id)){
                response.setStatus(Utils.NOT_FOUND);
                out.print("No se puede actualizar usuario con id " + id +", porque no existe!");
                out.flush();
                return;
            }
            servicio.updateUsuario(new Usuario(id, nombre, puntaje));
            out.print("Usuario actualizado exitosamente!");

        } catch (ExcepcionServicio e) {
            response.setStatus(Utils.INTERNAL_SERVER_ERROR);
            out.print("Error al actualizar el usuario " + id);
        } finally{
            out.flush();
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String id = String.valueOf(request.getParameter("id"));

        try {
            if(!servicio.existeUsuario(id)){
                response.setStatus(Utils.NOT_FOUND);
                out.print("No se puede eliminar usuario con id " + id +", porque no existe!");
                out.flush();
                return;
            }
            servicio.deleteUsuario(id);
            out.print("Usuario " + id + " eliminado exitosamente!");
        } catch (ExcepcionServicio e) {
            response.setStatus(Utils.INTERNAL_SERVER_ERROR);
            out.print("Error al eliminar el usuario " + id);
        }finally {
            out.flush();
        }
    }
}