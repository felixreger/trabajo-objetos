package com.example.endpoints;


import com.example.endpoints.utils.Utils;
import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Archivo;
import com.example.modelo.Carpeta;
import com.example.modelo.Catedra;
import com.example.modelo.Usuario;
import com.example.servicios.Servicios;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

@WebServlet(name="Carpeta", value="/carpeta")
public class CarpetaEndpoint extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject body = Utils.getRequestBody(request);

        String nombre = body.getString("nombre");
        String tipo = body.getString("tipo");
        LocalDate fechaModificacion = LocalDate.parse(body.getString("fechaModificacion"));
        LocalDate fechaCreacion = LocalDate.parse(body.getString("fechaCreacion"));
        String padre = body.getString("padre");
        String usuarioParam = body.getString("propietario");
        String catedraParam = body.getString("catedra");

        try {
            Usuario propietario = servicio.getUsuario(usuarioParam);
            if(propietario.isValid()){
                Catedra catedra = servicio.getCatedra(catedraParam);
                Carpeta carpeta = new Carpeta(nombre, tipo, fechaModificacion, fechaCreacion, padre, propietario, catedra);
                carpeta.setTamanio(0);
                servicio.addCarpeta(carpeta);
                out.print("Carpeta agregada");
            }else
                out.print("El usuario " + propietario + " no existe");
            } catch (ExcepcionServicio e) {
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

        String nombre = request.getParameter("nombre");
        try {
            servicio.deleteArchivo(nombre);
            out.print("Carpeta eliminada exitosamente!");
        } catch (ExcepcionServicio e) {
            out.print("Error al eliminar la carpeta " + nombre);
        }finally{
            out.flush();
        }
    }
}
