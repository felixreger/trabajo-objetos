package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.servlets.endpoints.request.requestcontrol.RequestControl;
import com.trabajofinal.utils.servlets.endpoints.ArchivoBytes;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.utils.excepciones.ExcepcionServicio;
import com.trabajofinal.servicios.Servicios;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name="ArchivoFuente", value= ConstantesServlet.URL_FUENTE)
public class ArchivoFuenteServlet extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();

    /**
     * Se realiza un control de la especificacion de la request.
     * Luego a partir del path al archivo, se obtiene el archivo fuente
     * y se retorna, especificando su tipo (pdf, png, ect)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestControl requestControl = new RequestControl();

        String path = request.getParameter("path");
        requestControl.add(path);

        if(!requestControl.esRequestValida()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
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
        }
    }
}
