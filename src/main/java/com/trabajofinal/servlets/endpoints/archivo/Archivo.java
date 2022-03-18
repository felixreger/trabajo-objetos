package com.trabajofinal.servlets.endpoints.archivo;

import com.trabajofinal.utils.servlets.endpoints.Constantes;
import com.trabajofinal.exceptions.ExcepcionServicio;
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

@WebServlet(name="ArchivoEndpoint", value= Constantes.URL_ARCHIVO)
@MultipartConfig(maxFileSize = 16177216)
public class Archivo extends HttpServlet {

    private final Servicios servicio = Servicios.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String bodyRequest = request.getParameter("request");
        JSONObject body = new JSONObject(bodyRequest);

        //todo, JUAN: aca podria estar todo en el mismo parametro, pero tengo que recortar el path
        String nombre = body.getString("nombre");
        String path = body.getString("path");

        try {
            if(!servicio.existeDirectorio(path)){
                out.print("El directorio" + path  + " no existe");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.flush();
                return;
            }

            if (servicio.existeElemento(path + nombre)) {
                out.print("El archivo " + nombre + " ya existe!");
                response.setStatus(Constantes.UNPROCESSABLE_ENTITY);
                out.flush();
                return;
            }

            JSONArray palabrasClaveParam = body.getJSONArray("palabrasclave");
            Set<String> palabrasClave = this.getPalabrasClave(palabrasClaveParam);

            String catedraParam = body.getString("catedra");

            String idUsuario = (String) request.getAttribute("idUsuario");
            Usuario propietario = servicio.getUsuario(idUsuario);

            Part filePart = request.getPart("data");
            InputStream is = filePart.getInputStream();

            if(propietario.esValido()) {
                Catedra catedra = servicio.getCatedra(catedraParam);
                if(catedra.isEmpty()){
                    out.print("La catedra " + catedraParam + " no existe!");
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.flush();
                    return;
                }

                com.trabajofinal.modelo.Archivo archivo = new com.trabajofinal.modelo.Archivo(nombre, propietario, LocalDate.now(), LocalDate.now(), path, filePart.getSize(), filePart.getContentType(), catedra);
                archivo.addPalabraClave(palabrasClave);
                archivo.addFuente(is);
                servicio.addArchivo(archivo);
                out.print("Archivo " + nombre + ", agregado correctamente");
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("El propietario " + propietario + " no existe");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al agregar el archivo " + nombre);
        } finally {
            out.flush();
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
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathArchivo = request.getParameter("pathArchivo");

        try {
            if(!servicio.existeElemento(pathArchivo)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("No se puede eliminar el archivo porque no existe!");
            }else{
                servicio.deleteArchivo(pathArchivo);
                out.print("Archivo eliminado exitosamente!");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al eliminar el archivo ");
        }finally{
            out.flush();
        }
    }
}
