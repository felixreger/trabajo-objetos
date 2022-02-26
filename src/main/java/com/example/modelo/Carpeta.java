package com.example.modelo;

import com.example.modelo.criterios.Criterio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Carpeta extends Elemento {

    private Set<Elemento> listaElementos;

    public Carpeta(String nombre, String tipo, LocalDate fechaModificacion, LocalDate fechaCreacion, String padre, Usuario propietario, Catedra catedra) {
        super(nombre, tipo, fechaCreacion, fechaCreacion, padre, propietario, catedra);
        listaElementos = new HashSet<>();
    }

    public Carpeta() {
        listaElementos = new HashSet<>();
    }

    public void addElemento(List<Elemento> listaElementos) {
        this.listaElementos.addAll(listaElementos);
    }

    public void eliminarElemento(Elemento elemento) {
        listaElementos.remove(elemento);
    }

    @Override 
    public Integer getTamanio() {
		Integer suma = 0;

        for (Elemento elemento : listaElementos) {
            suma = suma + elemento.getTamanio();
        }
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
    public String toString() {
        return super.toString() + " " + listaElementos.toString();
    }

}