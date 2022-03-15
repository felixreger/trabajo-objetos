package com.example.modelo;

import com.example.modelo.criterios.Criterio;

import java.time.LocalDate;
import java.util.*;


public class Archivo extends Elemento implements Comparator<Elemento> {

    private Integer tamanio;
    private String extension;
    private Set<String> palabrasClaves;

    public Archivo(String nombre, String tipo,
                   Integer tamanio,
                   LocalDate fechaModificacion,
                   LocalDate fechaCreacion,
                   Catedra catedra,
                   Usuario propietario, String padre) {

        super(nombre, tipo, fechaModificacion, fechaCreacion, padre, propietario, catedra);
        
        this.tamanio = tamanio;
        this.palabrasClaves = new HashSet<>();
    }

    public Archivo() {
    }

    @Override
    public Integer getTamanio() {
        return tamanio;
    }

    @Override
    public List<Archivo> filtrar(Criterio c) {

        List<Archivo> retorno = new ArrayList<>();
        if (c.cumple(this)) {
            retorno.add(this);
        }
        return retorno;
    }

    public void addPalabraClave(Set<String> palabras){
        this.palabrasClaves.addAll(palabras);
    }

    @Override //todo: retornar una copia?
    public Set<String> getPalabrasClave() {
        return palabrasClaves;
    }

    @Override
    public int compare(Elemento o1, Elemento o2) {
        return o1.getNombre().compareTo(o2.getNombre());
    }

}
