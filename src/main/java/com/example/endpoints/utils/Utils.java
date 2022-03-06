package com.example.endpoints.utils;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class Utils {

    public static int NOT_FOUND = 404;
    public static int UNPROCESSABLE_ENTITY = 422;
    public static int INTERNAL_SERVER_ERROR = 500;

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
