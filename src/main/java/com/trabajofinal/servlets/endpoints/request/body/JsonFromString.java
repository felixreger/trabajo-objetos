package com.trabajofinal.servlets.endpoints.request.body;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class JsonFromString extends JsonBody{

    public JsonFromString(String bodyRequest) {
        body = new JSONObject(bodyRequest);
    }

    @Override
    public Set<String> getSet(String key) {
        try {
            JSONArray palabrasClaveParam = body.getJSONArray(key);
            return this.getPalabrasClave(palabrasClaveParam);
        }catch (JSONException e){
            return null;
        }
    }

    private Set<String> getPalabrasClave(JSONArray palabrasClaveParam) {
        Set<String> tmp = new HashSet<>();
        for (Object j: palabrasClaveParam){
            tmp.add(j.toString());
        }
        return tmp;
    }

}
