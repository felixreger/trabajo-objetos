package com.trabajofinal.modelo;

import com.trabajofinal.modelo.criterios.archivo.CriterioArchivo;

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

    public boolean esValido() {
        return nombre.equals("");
    }

    public String getPath() {
        return this.path;
    }

    public abstract Long getTamanio();

    public abstract List<Archivo> filtrar(CriterioArchivo c);

    public abstract Set<String> getPalabrasClave();

    public void setPath(String path) {
        this.path = path;
    }

}