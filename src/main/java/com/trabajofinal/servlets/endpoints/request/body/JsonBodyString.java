package com.trabajofinal.servlets.endpoints.request.body;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class JsonBodyString extends JsonBody {

    public JsonBodyString(String bodyRequest) {
        body = new JSONObject(bodyRequest);
    }

    public Set<String> getPalabrasClave(String key) {
        try {
            JSONArray palabrasClaveParam = body.getJSONArray(key);
            return this.getPalabras(palabrasClaveParam);
        }catch (JSONException e){
            return null;
        }
    }

    private Set<String> getPalabras(JSONArray palabrasClaveParam) {
        Set<String> tmp = new HashSet<>();
        for (Object j: palabrasClaveParam){
            tmp.add(j.toString());
        }
        return tmp;
    }

}
