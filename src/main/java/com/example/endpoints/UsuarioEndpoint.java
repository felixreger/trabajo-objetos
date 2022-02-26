package com.example.endpoints;

import com.example.modelo.Usuario;
import com.example.servicios.Servicios;
import com.google.gson.Gson;

import java.io.*;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "Usuarios", value = "/usuarios")
public class UsuarioEndpoint extends HttpServlet {

    private final Gson gson = new Gson();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Usuario> usuarios = Servicios.getInstance().getUsuarios();

        String listaUsuariosJson = this.gson.toJson(usuarios);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(listaUsuariosJson);
        out.flush();

    }

}