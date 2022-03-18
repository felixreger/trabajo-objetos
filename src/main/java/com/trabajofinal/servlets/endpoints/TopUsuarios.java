package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.servlets.autentificacion.cors.CorsFilter;
import com.trabajofinal.utils.servlets.endpoints.Constantes;
import com.trabajofinal.exceptions.ExcepcionServicio;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.servicios.Servicios;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "Top", value = Constantes.URL_TOP)
public class TopUsuarios extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();
    private final int TOP = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!CorsFilter.habilitarCors(request, response)) return;

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Usuario> usuarios;
        try {
            usuarios = servicio.getTopUsuarios(TOP);
            String listaUsuariosJson = this.gson.toJson(usuarios);
            out.print(listaUsuariosJson);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al cargar el/los usuario/s");
        }finally {
            out.flush();
        }
    }
}