package com.trabajofinal.servlets.endpoints.archivo;

import com.trabajofinal.utils.servlets.endpoints.ArchivoBytes;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.servicios.Servicios;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name="ArchivoFuente", value= ConstantesServlet.URL_FUENTE)
public class ArchivoFuente extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getParameter("path");

        try {
            ArchivoBytes file = servicio.getArchivoFuente(path);
            if(!file.esValido()){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            response.setContentType(file.getExtension());
            OutputStream o = response.getOutputStream();
            o.write(file.getArchivoFuente());
            //o.flush();
            o.close();
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
