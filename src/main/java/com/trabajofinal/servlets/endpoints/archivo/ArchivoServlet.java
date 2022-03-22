package com.trabajofinal.servlets.endpoints.archivo;

import com.trabajofinal.modelo.Archivo;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.modelo.Catedra;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.servicios.Servicios;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name="ArchivoEndpoint", value= ConstantesServlet.URL_ARCHIVO)
@MultipartConfig(maxFileSize = 90177216)
public class ArchivoServlet extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String bodyRequest = request.getParameter("request");
        JSONObject body = new JSONObject(bodyRequest);

        String nombre = body.getString("nombre");
        String path = body.getString("path");

        JSONArray palabrasClaveParam = body.getJSONArray("palabrasclave");
        Set<String> palabrasClave = this.getPalabrasClave(palabrasClaveParam);
        String catedraParam = body.getString("catedra");
        String idUsuario = (String) request.getAttribute("idUsuario");
        String pathDelElemento = path + ":" + nombre;

        Part filePart = request.getPart("data");
        InputStream is = filePart.getInputStream();

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

    private Set<String> getPalabrasClave(JSONArray palabrasClaveParam) {
        Set<String> tmp = new HashSet<>();
        for (Object j: palabrasClaveParam){
            tmp.add(j.toString());
        }
        return tmp;
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathArchivo = request.getParameter("pathArchivo");

        try {
            if(!servicio.existeElemento(pathArchivo)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            servicio.deleteArchivo(pathArchivo);
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
