package com.trabajofinal.utils.excepciones;

public class ExcepcionServicio extends Exception{
    /**
     * Utilizada para lanzar una excepcion en los servicios por un error interno
     */
    public ExcepcionServicio(String message){
        super(message);
    }
}
