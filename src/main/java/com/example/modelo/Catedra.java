package com.example.modelo;

public class Catedra {
    private String nombre;
    private String urlPaginaWeb;

    public Catedra(){

    }
    
    public boolean isEmpty(){
        return nombre.isEmpty();
    }

    public Catedra(String nombre) {
        this.nombre = nombre;
        this.urlPaginaWeb = "www.default.com";
    }

    public Catedra(String nombre, String urlPaginaWeb) {
        this.nombre = nombre;
        this.urlPaginaWeb = urlPaginaWeb;
    }

    public String getNombre(){
        return nombre;
    }

    public String getUrlPaginaWeb(){
        return urlPaginaWeb;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setPaginaWeb(String urlPaginaWeb){
        this.urlPaginaWeb = urlPaginaWeb;
    }

    @Override
    public String toString() {
        return "Nombre catedra " + nombre + " y url " + urlPaginaWeb;
    }
}