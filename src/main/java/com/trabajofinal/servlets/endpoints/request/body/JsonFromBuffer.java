package com.trabajofinal.servlets.endpoints.request.body;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Set;

public class JsonFromBuffer extends JsonBody{

    public JsonFromBuffer(HttpServletRequest request){
        body = this.getRequestBody(request);
    }

    @Override
    public Set<String> getSet(String key) {
        return null;
    }

    private JSONObject getRequestBody(HttpServletRequest request) {
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
            body = new JSONObject();
        }

        return body;
    }
}
