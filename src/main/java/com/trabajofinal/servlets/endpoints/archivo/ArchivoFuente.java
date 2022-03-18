package com.trabajofinal.servlets.endpoints.archivo;

import com.trabajofinal.utils.servlets.endpoints.Constantes;
import com.trabajofinal.exceptions.ExcepcionServicio;
import com.trabajofinal.servicios.Servicios;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name="ArchivoFuente", value= Constantes.URL_FUENTE)
public class ArchivoFuente extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getParameter("path");
        String extension = request.getParameter("extension");
        try {
            byte[] file = servicio.getArchivoFuente(path, extension);
            if(file == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            response.setContentType(extension);
            OutputStream o = response.getOutputStream();
            o.write(file);
            o.flush();
            o.close();
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
