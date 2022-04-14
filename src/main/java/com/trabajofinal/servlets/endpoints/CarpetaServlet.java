package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.excepciones.ExcepcionRequest;
import com.trabajofinal.modelo.Carpeta;
import com.trabajofinal.servlets.endpoints.request.body.JsonBody;
import com.trabajofinal.servlets.endpoints.request.body.JsonBodyBuffer;
import com.trabajofinal.servlets.endpoints.request.requestcontrol.RequestControl;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.servicios.Servicios;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@WebServlet(name="Carpeta", value= ConstantesServlet.URL_CARPETA)
public class CarpetaServlet extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();

    /**
     * Se realiza un control de la especificacion de la request.
     * Se obtienen todos los parametros necesarios para crear una instancia de Carpeta
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        JsonBody body = new JsonBodyBuffer(request);

        String nombre = body.getString("nombre");
        String path = body.getString("path");
        String descripcion = body.getString("descripcion");
        requestControl.addAll(Arrays.asList(nombre, path, descripcion));

        String usuarioParam =(String)request.getAttribute("idUsuario"); //este atrib.siempre esta por el filter
        String pathDelElemento = path + ":" + nombre;

        try {
            requestControl.validarRequest();

            if(!servicio.existeDirectorio(path)){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if(servicio.existeElemento(pathDelElemento)) {
                response.setStatus(ConstantesServlet.UNPROCESSABLE_ENTITY);
                return;
            }
            Usuario propietario = servicio.getUsuario(usuarioParam);
            Carpeta carpeta = new Carpeta(nombre, propietario, LocalDate.now(), LocalDate.now(),  path, descripcion);
            servicio.addCarpeta(carpeta);

            int puntaje = propietario.getPuntaje();
            propietario.setPuntaje(++puntaje);
            servicio.updateUsuario(propietario);

        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ExcepcionRequest e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Se realiza un control de la especificacion de la request.
     * Luego, se elimina la carpeta especificado por el path a dicha carpeta
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        String pathCarpeta = request.getParameter("pathCarpeta");
        requestControl.add(pathCarpeta);

        try {
            requestControl.validarRequest();

            if(!servicio.existeElemento(pathCarpeta)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            servicio.deleteCarpeta(pathCarpeta);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ExcepcionRequest e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
