package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.excepciones.ExcepcionRequest;
import com.trabajofinal.servlets.autentificacion.cors.CorsFilter;
import com.trabajofinal.servlets.endpoints.request.requestcontrol.RequestControl;
import com.trabajofinal.utils.servlets.endpoints.ConstantesServlet;
import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.modelo.Elemento;
import com.trabajofinal.servicios.Servicios;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Set;

@WebServlet(name = "Directorio", value = ConstantesServlet.URL_DIRECTORIO)
public class DirectorioServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    /**
     * Se realiza un control de la especificacion de la request.
     * Se retorna en formato json un directorio completo a partir de una
     * carpeta base.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!CorsFilter.habilitarCors(request, response)) return;

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RequestControl requestControl = new RequestControl();

        String pathCarpetaBase = request.getParameter("pathCarpetaBase");
        requestControl.add(pathCarpetaBase);

        try {
            requestControl.validarRequest();

            if(!servicio.existeElemento(pathCarpetaBase)){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Elemento directorio = servicio.getDirectorio(pathCarpetaBase);
            long tamanio = directorio.getTamanio();
            String dirJson = this.gson.toJson(directorio);
            Set<String> pClaves =  directorio.getPalabrasClave();
            String pClavesJson = this.gson.toJson(pClaves);
            String aux = agregarPalabrasClave(dirJson, pClavesJson);
            out.print(this.agregarTamanio(aux, tamanio));

        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ExcepcionRequest e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        out.flush();
    }

    /**
     * Se agrega el json el tamanio del directorio.
     */
    private String agregarTamanio(String json, long tamanio) {
        String tam = ", \"tamanio\":  " + tamanio + "}";
        return json + tam;
    }

    /**
     * Se agrega al json las palabras clave asociadas al directorio.
     */
    private String agregarPalabrasClave(String json, String palabras){
        json = json.substring(0, json.length() - 1);
        return json + ", \"palabrasClave\": " + palabras;
    }
}
