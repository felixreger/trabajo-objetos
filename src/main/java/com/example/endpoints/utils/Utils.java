package com.example.endpoints.utils;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class Utils {

    //TODO: acomodar mejor
    public static final String URL_USUARIO = "/usuarios";
    public static final String URL_DIRECTORIO = "/directorio";
    public static final String URL_COMENTARIO = "/comentario";
    public static final String URL_CATEDRA = "/catedra";
    public static final String URL_CARPETA = "/carpeta";
    public static final String URL_ARCHIVO = "/archivo";
    public static final String URL_FILTRO_ARCHIVOS = "/filtro";
    public static final String URL_TOP = "/top";
    public static final String URL_PALABRAS_CLAVE = "/palabrasclave";

    public static final int UNPROCESSABLE_ENTITY = 422;

    public static final String AUTOR = "autor";
    public static final String TIPO = "tipo";
    public static final String CONTIENE_NOMBRE = "contienenombre";
    public static final String FECHA_CREACION = "fechacreacion";
    public static final String FECHA_MODIFICACION = "fechamodificacion";


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
}
