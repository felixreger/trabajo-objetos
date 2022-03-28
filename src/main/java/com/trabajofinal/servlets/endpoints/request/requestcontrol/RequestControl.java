package com.trabajofinal.servlets.endpoints.request.requestcontrol;

import com.trabajofinal.excepciones.ExcepcionRequest;

import java.util.ArrayList;
import java.util.List;

public class RequestControl {

    private List<Object> parametros;

    public RequestControl(){
        parametros = new ArrayList<>();
    }

    public void agregarParametros(List<Object> parametros){
        this.parametros.addAll(parametros);
    }

    public void validarRequest() throws ExcepcionRequest {
        for (Object o : parametros) {
            if(o == null){
                throw new ExcepcionRequest("Bad request");
            }
        }
    }

    public void agregarBody(List<Object> asList) {
        this.parametros.addAll(asList);
    }
}

