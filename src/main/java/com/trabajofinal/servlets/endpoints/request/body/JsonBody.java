package com.trabajofinal.servlets.endpoints.request.body;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

public abstract class JsonBody {

    protected JSONObject body = null;

    public String getString(String key) {
        try {
            return body.getString(key);
        }catch (JSONException e){
            return null;
        }
    }

    public Integer getInt(String key) {
        try {
            return body.getInt(key);
        }catch (JSONException e){
            return null;
        }
    }

    public abstract Set<String> getSet(String key);

}
