package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.servlets.endpoints.body.RequestBody;
import com.trabajofinal.utils.servlets.endpoints.Constantes;
import com.trabajofinal.exceptions.ExcepcionServicio;
import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.servicios.Servicios;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

@WebServlet(name="Carpeta", value= Constantes.URL_CARPETA)
public class Carpeta extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = RequestBody.getRequestBody(request);
        String nombre = body.getString("nombre");

        String path = body.getString("path");
        String descripcion = body.getString("descripcion");

        String usuarioParam =(String)request.getAttribute("idUsuario");

        try {
            //TODO, Juan: aca tambien se podria mandar en solo param path+nombre, pero yo lo tendria que dividir
            if(servicio.existeElemento(path + nombre)){
                out.print("La carpeta " + nombre + " ya existe!");
                out.flush();
                return;
            }
            Usuario propietario = servicio.getUsuario(usuarioParam);
            if(propietario.esValido()){
                com.trabajofinal.modelo.Carpeta carpeta = new com.trabajofinal.modelo.Carpeta(nombre, propietario, LocalDate.now(), LocalDate.now(),  path, descripcion);
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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathCarpeta = request.getParameter("pathCarpeta");
        try {
            if(!servicio.existeElemento(pathCarpeta)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("No se puede eliminar la carpeta, porque no existe!");
            }else{
                servicio.deleteCarpeta(pathCarpeta);
                out.print("Carpeta eliminada exitosamente!");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al eliminar la carpeta ");
        }finally{
            out.flush();
        }
    }
}
