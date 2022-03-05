package com.example.endpoints;

import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Elemento;
import com.example.servicios.Servicios;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Directorio", value = "/directorio")
public class DirectorioEndpoint extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String raiz = request.getParameter("carpetaBase");
        Elemento directorio;
        //todo: corroborar si existe el directorio
        try {
            directorio = servicio.getDirectorio(raiz);
            String dirJson = this.gson.toJson(directorio);
            out.print(dirJson);
        } catch (ExcepcionServicio e) {
            response.setStatus(500);
            out.print("Error al cargar el directorio " + raiz);
        }finally{
            out.flush();
        }
    }

}
