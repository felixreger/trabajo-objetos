package com.trabajofinal.servlets.endpoints.request.requestcontrol;

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

    public boolean esRequestValida() {
        for (Object o : parametros) {
            if(o == null){
                return false;
            }
        }
        return true;
    }
}

