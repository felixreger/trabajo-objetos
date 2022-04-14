package com.trabajofinal.excepciones;

public class ExcepcionRequest extends Exception{
    /**
     * Utilizada para lanzar una excepcion si la request no esta especificada correctament
     */
    public ExcepcionRequest(String message){
        super(message);
    }
}
