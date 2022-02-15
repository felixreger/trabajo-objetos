package main.modelo;

import java.time.LocalDate;
import java.util.List;

import main.modelo.criterios.Criterio;

public abstract class Elemento{

    private String padre;
    private String nombre;
    private String tipo;
    private LocalDate fechaModificacion;
    private LocalDate fechaCreacion;
    private Catedra catedra;

    public boolean isValid(){
        return 
        nombre == null
        && fechaModificacion == null
        && fechaCreacion == null
        && catedra ==
         null;    
    }

    public Elemento(){
        this.nombre = "";
        this.tipo = "";
        this.fechaModificacion = null;
        this.fechaCreacion = null;
        this.catedra = new Catedra(); // todo : verificar que funcione lo invalido
    }

    public Elemento(String nombre,String tipo, LocalDate fechaModificacion, LocalDate fechaCreacion, String padre){
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaModificacion = fechaModificacion;
        this.fechaCreacion = fechaCreacion;
        this.padre = padre;
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

    public String getNombre(){
        return this.nombre;
    }

    public String getPadre(){
        return this.padre;
    }

    public void setPadre(String carpeta){
        this.padre = carpeta;
    }

    public abstract String getPropietario();

    public abstract Catedra getCatedra();

    public abstract String getNombrePadre();

    public abstract Integer getTamanio();

    public void setTamanio(int int1) {
    }

    @Override
    public String toString() {
        return nombre + ", " + tipo;
    }

    public abstract List<Archivo> filtrar(Criterio c);

}