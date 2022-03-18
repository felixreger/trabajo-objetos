package com.trabajofinal.modelo;

import com.trabajofinal.modelo.criterios.Criterio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Carpeta extends Elemento {

    private final Set<Elemento> listaElementos;
    private String descripcion;

    public Carpeta(String nombre) {
        super(nombre);
        listaElementos = new HashSet<>();
    }

    public Carpeta(String nombre, Usuario propietario, LocalDate fechaCreacion, LocalDate fechaModificacion, String path, String descripcion) {
        super(nombre, propietario, fechaCreacion, fechaModificacion, path);
        this.descripcion = descripcion;
        this.listaElementos = new HashSet<>();
    }

    public Carpeta() {
        super();
        listaElementos = new HashSet<>();
    }

    public void addElemento(Elemento elemento) {
        this.listaElementos.add(elemento);
    }

    @Override 
    public Long getTamanio() {
		long suma = 0L;
        for (Elemento elemento : listaElementos)
            suma = suma + elemento.getTamanio();
		return suma;
    }

    @Override
    public List<Archivo> filtrar(Criterio c) {
        List<Archivo> cumplen = new ArrayList<>();
        for (Elemento elem : listaElementos) {
            cumplen.addAll(elem.filtrar(c));
        }
        return cumplen;
    }

    @Override
    public Set<String> getPalabrasClave() {
        Set<String> retorno = new HashSet<>();
        for (Elemento elem : listaElementos)
            retorno.addAll(elem.getPalabrasClave());

        return retorno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return super.toString() + " " + listaElementos.toString();
    }

}