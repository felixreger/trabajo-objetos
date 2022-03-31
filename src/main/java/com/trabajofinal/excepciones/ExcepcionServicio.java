package com.trabajofinal.excepciones;

public class ExcepcionServicio extends Exception{
    /**
     * Clase utilizada para lanzar una excepcion en los servicios por un error interno
     */
    public ExcepcionServicio(String message){
        super(message);
    }
}
