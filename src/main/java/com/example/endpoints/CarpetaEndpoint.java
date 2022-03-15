package com.example.endpoints;


import com.example.endpoints.utils.Utils;
import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Carpeta;
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

@WebServlet(name="Carpeta", value=Utils.URL_CARPETA)
public class CarpetaEndpoint extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();

    @Override //solo admins
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = Utils.getRequestBody(request);
        String nombre = body.getString("nombre");
        String tipo = body.getString("tipo");

        String fechaCreacionParam = body.getString("fechaCreacion");
        String fechaModificacionParam = body.getString("fechaModificacion");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        //LocalDate fechaCreacion = LocalDate.parse(fechaCreacionParam, formatter);
        //LocalDate fechaModificacion = LocalDate.parse(fechaModificacionParam, formatter);

        LocalDate fechaCreacion = LocalDate.now();
        LocalDate fechaModificacion = LocalDate.now(); //TODO: acomodar de nuevo las fechas ._.

        String padre = body.getString("padre");

        //modificacion de la query
        String usuarioParam =(String)request.getAttribute("idUsuario");
        String catedraParam = body.getString("catedra");

        try {
            if(servicio.existeElemento(nombre)){
                out.print("La carpeta " + nombre + " ya existe!");
                out.flush();
                return;
            }

            Usuario propietario = servicio.getUsuario(usuarioParam);
            if(propietario.esValido()){
                Catedra catedra = servicio.getCatedra(catedraParam);
                Carpeta carpeta = new Carpeta(nombre, tipo, fechaModificacion, fechaCreacion, padre, propietario, catedra);
                //todo: ver el tamanio
                //todo: con la modificacion, no hace falta guardar tamanio
                servicio.addCarpeta(carpeta);
                out.print("Carpeta agregada");
            }else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("El usuario " + propietario + " no existe");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al agregar la carpeta " + nombre);
        }finally{
            out.flush();
        }
    }

    @Override //solo admins
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idCarpeta = request.getParameter("nombre");
        try {
            if(!servicio.existeElemento(idCarpeta)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("No se puede eliminar la carpeta " + idCarpeta +", porque no existe!");
            }else{
                servicio.deleteCarpeta(idCarpeta);
                out.print("Carpeta eliminada exitosamente!");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al eliminar la carpeta " + idCarpeta);
        }finally{
            out.flush();
        }
    }
}
