package com.example.modelo;

import java.util.Objects;

public class Usuario {
    private String mail;
    private transient String password;
    private String nombre;
    private int puntaje;
    private boolean admin;

    public Usuario(String mail, String nombre, int puntaje){
        this.mail = mail;
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

    public Usuario(String mail, String nombre, int puntaje, String password){
        this.mail = mail;
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.password = password;
    }

    public Usuario(){
        this.mail = "";
        this.nombre = "";
        this.puntaje = 0;
    }

    public boolean esValido(){
        return !Objects.equals(this.mail, "");
    }
    
    public void setMail(String mail){
        this.mail = mail;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setPuntaje(int puntaje){
        this.puntaje = puntaje;
    }

    public String getMail(){
        return mail;
    }

    public String getNombre(){
        return nombre;
    }

    public int getPuntaje(){
        return puntaje;
    }

    @Override
    public String toString() {
        return "{" +
            " mail='" + getMail() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", puntaje='" + getPuntaje() + "'" +
            "}";
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean esAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
