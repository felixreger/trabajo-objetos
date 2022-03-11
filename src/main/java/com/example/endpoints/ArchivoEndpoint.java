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
import java.time.format.DateTimeFormatter;

@WebServlet(name="ArchivoEndpoint", value=Utils.URL_ARCHIVO)
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
            if (servicio.existeElemento(nombre)) {
                out.print("El archivo " + nombre + " ya existe!");
                out.flush();
                return;
            }
            String tipo = body.getString("tipo");
            Integer tamanio = body.getInt("tamanio");

            String fechaCreacionParam = body.getString("fechaCreacion");
            String fechaModificacionParam = body.getString("fechaModificacion");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

            LocalDate fechaCreacion = LocalDate.parse(fechaCreacionParam, formatter);
            LocalDate fechaModificacion = LocalDate.parse(fechaModificacionParam, formatter);

            String catedraParam = body.getString("catedra");
            String usuarioParam = body.getString("idUsuario");
            String padre = body.getString("padre");

            Usuario propietario = servicio.getUsuario(usuarioParam);
            if(propietario.isValid()) {
                Catedra catedra = servicio.getCatedra(catedraParam);
                Archivo archivo = new Archivo(nombre, tipo, tamanio, fechaModificacion, fechaCreacion, catedra, propietario, padre);
                servicio.addArchivo(archivo);
                out.print("Archivo " + nombre + ", agregado correctamente");
            }else
                response.setStatus(Utils.NOT_FOUND);
                out.print("El propietario " + propietario + " no existe");
        } catch (ExcepcionServicio e) {
            response.setStatus(Utils.INTERNAL_SERVER_ERROR);
            out.print("Error al agregar el archivo " + nombre);
        }finally {
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String nombre = request.getParameter("nombre");
        try {
            if (!servicio.existeElemento(nombre)) {
                response.setStatus(Utils.NOT_FOUND);
                out.print("El archivo " + nombre + " no existe!");
                out.flush();
                return;
            }
            servicio.deleteArchivo(nombre);
            out.print("Archivo eliminada exitosamente!");
        } catch (ExcepcionServicio e) {
            response.setStatus(Utils.INTERNAL_SERVER_ERROR);
            out.print("Error al eliminar el archivo " + nombre);
        }finally {
            out.flush();
        }
    }
}
