package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.excepciones.ExcepcionRequest;
import com.trabajofinal.modelo.Archivo;
import com.trabajofinal.servlets.endpoints.request.body.JsonBodyString;
import com.trabajofinal.servlets.endpoints.request.requestcontrol.RequestControl;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.modelo.Catedra;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.servicios.Servicios;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@WebServlet(name="ArchivoEndpoint", value= ConstantesServlet.URL_ARCHIVO)
@MultipartConfig(maxFileSize = 90177216)
public class ArchivoServlet extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();

    /**
     * Se realiza un control de la especificacion de la request.
     * Se obtienen todos los parametros necesarios para crear una instancia de Archivo
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        String bodyRequest = request.getParameter("request");
        Part filePart = request.getPart("data");

        JsonBodyString body = new JsonBodyString(bodyRequest);

        String nombre = body.getString("nombre");
        String path = body.getString("path");
        String catedraParam = body.getString("catedra");
        Set<String> palabrasClave = body.getPalabrasClave("palabrasclave");

        requestControl.addAll(Arrays.asList(bodyRequest, filePart, nombre, path, catedraParam, palabrasClave));
        try {
            requestControl.validarRequest();
        } catch (ExcepcionRequest e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        InputStream is = filePart.getInputStream();
        String idUsuario = (String) request.getAttribute("idUsuario"); //este atrib. siempre esta por el filter
        String pathDelElemento = path + ":" + nombre;

        try {
            if(!servicio.existeDirectorio(path)){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if (servicio.existeElemento(pathDelElemento)) {
                response.setStatus(ConstantesServlet.UNPROCESSABLE_ENTITY);
                return;
            }
            Usuario propietario = servicio.getUsuario(idUsuario);

            Catedra catedra = servicio.getCatedra(catedraParam);
            if(!catedra.esValida()){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Archivo archivo = new Archivo(nombre, propietario, LocalDate.now(), LocalDate.now(), path, filePart.getSize(), filePart.getContentType(), catedra);
            archivo.addPalabraClave(palabrasClave);
            archivo.addFuente(is);
            servicio.addArchivo(archivo);

            int puntaje = propietario.getPuntaje();
            propietario.setPuntaje(++puntaje);
            servicio.updateUsuario(propietario);

        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Se realiza un control de la especificacion de la request.
     * Luego, se elimina el archivo especificado por el path a dicho archivo
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        String pathArchivo = request.getParameter("pathArchivo");
        requestControl.add(pathArchivo);

        try {
            requestControl.validarRequest();
            if(!servicio.existeElemento(pathArchivo)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            servicio.deleteArchivo(pathArchivo);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ExcepcionRequest e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
