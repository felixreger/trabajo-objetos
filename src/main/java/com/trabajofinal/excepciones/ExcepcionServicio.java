package com.trabajofinal.excepciones;

public class ExcepcionServicio extends Exception{
    /**
     * Clase utilizada para levantar excepciones en los servicios
     */
    public ExcepcionServicio(String message){
        super(message);
    }
}
