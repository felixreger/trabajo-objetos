package com.example.endpoints;

import com.example.endpoints.utils.Utils;
import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Carpeta;
import com.example.modelo.Elemento;
import com.example.servicios.Servicios;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "Directorio", value = Utils.URL_DIRECTORIO)
public class DirectorioEndpoint extends HttpServlet {

    private final Gson gson = new Gson();
    private final Servicios servicio = Servicios.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String raiz = request.getParameter("carpetaBase");
        Elemento directorio;
        try {
            if(servicio.existeElemento(raiz)){
                directorio = servicio.getDirectorio(raiz);
                int tamanio = directorio.getTamanio();
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
            out.print("Error al cargar el directorio " + raiz);
        }finally{
            out.flush();
        }
    }

    //se lo requiere aca en otro lado?
    private String agregarTamanio(String json, int tamanio) {
        String tam = ", \"tamanio\":  " + tamanio + "}";
        return json + tam;
    }

    private String agregarPalabrasClave(String json, String palabras){
        json = json.substring(0, json.length() - 1);

        return json + ", \"palabrasClave\": " + palabras;
    }
}
