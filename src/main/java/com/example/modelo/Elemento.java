package com.example.modelo;

import com.example.modelo.criterios.Criterio;

import java.time.LocalDate;
import java.util.List;


public abstract class Elemento {

    private String padre;
    private String nombre;
    private String tipo;
    private LocalDate fechaModificacion;
    private LocalDate fechaCreacion;
    private Catedra catedra;
    private Usuario propietario;

    public boolean isValid() {
        return nombre.isEmpty()
                && fechaModificacion == null
                && fechaCreacion == null
                && catedra.isEmpty();
    }

    public Elemento() {
        this.nombre = new String();
        this.tipo = new String();
        this.fechaModificacion = null;
        this.fechaCreacion = null;
        this.catedra = new Catedra();
    }

    public Elemento(String nombre, String tipo, LocalDate fechaModificacion, LocalDate fechaCreacion, String padre, Usuario propietario, Catedra catedra) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaModificacion = fechaModificacion;
        this.fechaCreacion = fechaCreacion;
        this.padre = padre;
        this.propietario = propietario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public void setCatedra(Catedra catedra) {
        this.catedra = catedra;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getPadre() {
        return this.padre;
    }

    public void setPadre(String carpeta) {
        this.padre = carpeta;
    }

    public abstract Integer getTamanio();

    public void setTamanio(int int1) {
    }

    public String getPropietario() {
        return propietario.getMail();
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public Catedra getCatedra(){
        return catedra;
    }

    @Override
    public String toString() {
        return "nombre: " + nombre + " | tipo:" + tipo + " | padre:" + padre;
    }

    public abstract List<Archivo> filtrar(Criterio c);

    @Override
    public boolean equals(Object obj) {
        return this.nombre.equals(((Elemento) obj).getNombre());
    }
}