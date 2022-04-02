package com.trabajofinal.servlets.endpoints.archivo;

import com.trabajofinal.excepciones.ExcepcionRequest;
import com.trabajofinal.servlets.endpoints.request.requestcontrol.RequestControl;
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
import java.util.Collections;

@WebServlet(name="ArchivoFuente", value= ConstantesServlet.URL_FUENTE)
public class ArchivoFuente extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestControl requestControl = new RequestControl();

        String path = request.getParameter("path");
        requestControl.add(path);

        try {
            requestControl.validarRequest();
            ArchivoBytes file = servicio.getArchivoFuente(path);

            if(!file.esValido()){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            response.setContentType(file.getExtension());
            OutputStream o = response.getOutputStream();
            o.write(file.getArchivoFuente());
            o.close();
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ExcepcionRequest e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
