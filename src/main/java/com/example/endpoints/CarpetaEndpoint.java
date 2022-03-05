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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

        //todo: acomodar fechas
        String fechaC = body.getString("fechaCreacion");
        String fechaM = body.getString("fechaModificacion");
        LocalDate fechaModificacion = LocalDate.now();
        LocalDate fechaCreacion = LocalDate.now();

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
            }else {
                response.setStatus(422);
                out.print("El usuario " + propietario + " no existe");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(500);
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

        String idCarpeta = request.getParameter("nombre");

        try {
            if(!servicio.existeCarpeta(idCarpeta)) {
                response.setStatus(422);
                out.print("No se puede eliminar la carpeta " + idCarpeta +", porque no existe!");
                out.flush();
                return;
            }
            servicio.deleteCarpeta(idCarpeta);
            out.print("Carpeta eliminada exitosamente!");
        } catch (ExcepcionServicio e) {
            response.setStatus(500);
            out.print("Error al eliminar la carpeta " + idCarpeta);
        }finally{
            out.flush();
        }
    }
}