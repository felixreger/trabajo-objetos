package com.example.endpoints;

import com.example.endpoints.utils.Utils;
import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Archivo;
import com.example.modelo.Catedra;
import com.example.modelo.Usuario;
import com.example.servicios.Servicios;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

@WebServlet(name="ArchivoEndpoint", value="/archivo")
public class ArchivoEndpoint extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = Utils.getRequestBody(request);
        String nombre = body.getString("nombre");

        try {
            if (servicio.getArchivo(nombre).isValid()) {
                out.print("El archivo " + nombre + " ya existe!");
                out.flush();
                return;
            }
        } catch (ExcepcionServicio e) {
            e.printStackTrace();
        }

        String tipo = body.getString("tipo");
        Integer tamanio = body.getInt("tamanio");
        LocalDate fechaModificacion = LocalDate.parse(body.getString("fechaModificacion"));
        LocalDate fechaCreacion = LocalDate.parse(body.getString("fechaCreacion"));
        String catedraParam = body.getString("catedra");
        String usuarioParam = body.getString("propietario");
        String padre = body.getString("padre");

        try {
            Usuario propietario = servicio.getUsuario(usuarioParam);
            if(propietario.isValid()) {
                Catedra catedra = servicio.getCatedra(catedraParam);
                Archivo archivo = new Archivo(nombre, tipo, tamanio, fechaModificacion, fechaCreacion, catedra, propietario, padre);
                servicio.addArchivo(archivo);
            }else
                out.print("El propietario " + propietario + " no existe");
        } catch (ExcepcionServicio e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String nombre = request.getParameter("nombre");
        try {
            if (!servicio.getArchivo(nombre).isValid()) {
                out.print("El archivo " + nombre + " no existe!");
                out.flush();
                return;
            }
        } catch (ExcepcionServicio e) {
            e.printStackTrace();
        }

        try {
            servicio.deleteArchivo(nombre);
            out.print("Archivo eliminada exitosamente!");
        } catch (ExcepcionServicio e) {
            out.print("Error al eliminar el archivo " + nombre);
        }finally {
            out.flush();
        }
    }
}
