package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.modelo.Carpeta;
import com.trabajofinal.servlets.endpoints.body.RequestBody;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.servicios.Servicios;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name="Carpeta", value= ConstantesServlet.URL_CARPETA)
public class CarpetaServlet extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = RequestBody.getRequestBody(request);
        String nombre = body.getString("nombre");

        String path = body.getString("path");
        String descripcion = body.getString("descripcion");
        String pathDelElemento = path + ":" + nombre;
        String usuarioParam =(String)request.getAttribute("idUsuario");

        try {
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
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathCarpeta = request.getParameter("pathCarpeta");
        try {
            if(!servicio.existeElemento(pathCarpeta)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            servicio.deleteCarpeta(pathCarpeta);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
