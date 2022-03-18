package com.trabajofinal.servlets.endpoints;

import com.trabajofinal.servlets.autentificacion.cors.CorsFilter;
import com.trabajofinal.utils.servlets.endpoints.Constantes;
import com.trabajofinal.exceptions.ExcepcionServicio;
import com.trabajofinal.modelo.Elemento;
import com.trabajofinal.servicios.Servicios;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet(name = "Directorio", value = Constantes.URL_DIRECTORIO)
public class Directorio extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!CorsFilter.habilitarCors(request, response)) return;

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathCarpetaBase = request.getParameter("pathCarpetaBase");
        Elemento directorio;
        try {
            if(servicio.existeElemento(pathCarpetaBase)){
                directorio = servicio.getDirectorio(pathCarpetaBase);
                long tamanio = directorio.getTamanio();
                String dirJson = this.gson.toJson(directorio);
                Set<String> pClaves =  directorio.getPalabrasClave();
                String pClavesJson = this.gson.toJson(pClaves);
                String aux = agregarPalabrasClave(dirJson, pClavesJson);

                out.print(this.agregarTamanio(aux, tamanio));
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("El directorio no existe");
            }
        } catch (ExcepcionServicio e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error al cargar el directorio ");
        }finally{
            out.flush();
        }
    }

    private String agregarTamanio(String json, long tamanio) {
        String tam = ", \"tamanio\":  " + tamanio + "}";
        return json + tam;
    }

    private String agregarPalabrasClave(String json, String palabras){
        json = json.substring(0, json.length() - 1);
        return json + ", \"palabrasClave\": " + palabras;
    }
}