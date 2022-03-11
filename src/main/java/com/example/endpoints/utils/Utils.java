package com.example.endpoints.utils;

import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.Usuario;
import com.example.servicios.Servicios;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static int NOT_FOUND = 404;
    public static int UNPROCESSABLE_ENTITY = 422;
    public static int INTERNAL_SERVER_ERROR = 500;

    public static final String URL_USUARIO = "/usuarios";
    public static final String URL_DIRECTORIO = "/directorio";
    public static final String URL_COMENTARIO = "/comentario";
    public static final String URL_CATEDRA = "/catedra";
    public static final String URL_CARPETA = "/carpeta";
    public static final String URL_ARCHIVO = "/archivo";
    public static final String URL_FILTRO_ARCHIVOS = "/filtro";
    public static final String URL_TOP = "/top";

    public static JSONObject getRequestBody(HttpServletRequest request) throws IOException {

        StringBuilder jb = new StringBuilder();
        String line;
        JSONObject body;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*error*/ }

        try {
            body =  new JSONObject(jb.toString());
        } catch (JSONException e) {
            throw new IOException("Error al obtener el body de la request");
        }
        return body;
    }

    public static Map<String, String> getCriterios(HttpServletRequest request){

        String criteriosParam = request.getParameter("criterios");

        // autor&juan@gmail.com&tipo&png
        String[] listaCriterios = criteriosParam.split("&");
        Map<String, String> criterios = new HashMap<>();

        int i = 0;
        while (i < listaCriterios.length -1){
            criterios.put(listaCriterios[i], listaCriterios[i+1]);
            i = i + 2;
        }

        return criterios;
    }
}
