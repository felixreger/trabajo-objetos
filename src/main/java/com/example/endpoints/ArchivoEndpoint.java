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

    //todo: modificar query, ya no se pasa el usuario en el body.
    @Override //todos pueden hacer
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

            LocalDate fechaCreacion = LocalDate.now();
            LocalDate fechaModificacion = LocalDate.now();  //TODO: acomodar de nuevo!!

            String catedraParam = body.getString("catedra");
            String padre = body.getString("padre");

            String idUsuario = (String) request.getAttribute("idUsuario");
            Usuario propietario = servicio.getUsuario(idUsuario);
            if(propietario.esValido()) {
                //todo: verificar si la carpeta existe!
                Catedra catedra = servicio.getCatedra(catedraParam);
                Archivo archivo = new Archivo(nombre, tipo, tamanio, fechaModificacion, fechaCreacion, catedra, propietario, padre);
                servicio.addArchivo(archivo);
                out.print("Archivo " + nombre + ", agregado correctamente");
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("El propietario " + propietario + " no existe");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al agregar el archivo " + nombre);
        }finally {
            out.flush();
        }
    }

    @Override //solo los admins
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idArchivo = request.getParameter("nombre");
        try {
            if(!servicio.existeElemento(idArchivo)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("No se puede eliminar el archivo " + idArchivo +", porque no existe!");
            }else{
                servicio.deleteArchivo(idArchivo);
                out.print("Archivo eliminado exitosamente!");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al eliminar el archivo " + idArchivo);
        }finally{
            out.flush();
        }
    }
}
