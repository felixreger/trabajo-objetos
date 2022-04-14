package com.trabajofinal.servlets.endpoints.request.requestcontrol;

import com.trabajofinal.excepciones.ExcepcionRequest;

import java.util.ArrayList;
import java.util.List;

public class RequestControl {

    private final List<Object> parametros;

    public RequestControl(){
        parametros = new ArrayList<>();
    }

    public void addAll(List<Object> parametros){
        this.parametros.addAll(parametros);
    }

    public void add(Object parametros){
        this.parametros.add(parametros);
    }

    public void validarRequest() throws ExcepcionRequest {
        for (Object o : parametros) {
            if(o == null){
                throw new ExcepcionRequest("Bad request");
            }
        }
    }
}

