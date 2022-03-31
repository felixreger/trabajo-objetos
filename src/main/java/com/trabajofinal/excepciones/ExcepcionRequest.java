package com.trabajofinal.excepciones;

public class ExcepcionRequest extends Exception{
    /**
     * Clase utilizada para lanzar excepciones si la request no es la indicada
     */
    public ExcepcionRequest(String message){
        super(message);
    }
}
