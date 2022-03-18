package com.trabajofinal.modelo;

import com.trabajofinal.modelo.criterios.Criterio;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public abstract class Elemento {

    private String nombre;
    private Usuario propietario;
    private LocalDate fechaCreacion;
    private LocalDate fechaModificacion;
    private String path;

    public Elemento() {
        this.nombre = "";
    }

    public Elemento(String nombre, Usuario propietario, LocalDate fechaCreacion, LocalDate fechaModificacion, String path) {
        this.nombre = nombre;
        this.propietario = propietario;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.path = path;
    }

    public Elemento(String nombre) {
        this.nombre = nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombre() {
        return this.nombre;
    }


    public String getPropietario() {
        return propietario.getMail();
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return "nombre: " + nombre + " | padre " + this.getPath();
    }

    @Override
    public boolean equals(Object obj) {
        return this.nombre.equals(((Elemento) obj).getNombre());
    }

    public boolean esValido() {
        return nombre.equals("");
    }

    public String getPath() {
        return this.path;
    }

    public abstract Long getTamanio();

    public abstract List<Archivo> filtrar(Criterio c);

    public abstract Set<String> getPalabrasClave();

    public void setPath(String path) {
        this.path = path;
    }

}