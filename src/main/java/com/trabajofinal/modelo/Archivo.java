package com.trabajofinal.modelo;

import com.trabajofinal.modelo.criterios.archivo.CriterioArchivo;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;


public class Archivo extends Elemento {

    private Long tamanio;
    private String extension;
    private Set<String> palabrasClaves;
    private Catedra catedra;
    private transient InputStream fuente;

    public Archivo() {
    }

    public Archivo(String nombre, Usuario propietario, LocalDate fechaCreacion, LocalDate fechaModificacion, String path, Long tamanio, String extension, Catedra catedra) {
        super(nombre, propietario, fechaCreacion, fechaModificacion, path);
        this.tamanio = tamanio;
        this.extension = extension;
        this.catedra = catedra;
        this.palabrasClaves = new HashSet<>();
    }

    @Override
    public Long getTamanio() {
        return tamanio;
    }

    @Override
    public List<Archivo> filtrar(CriterioArchivo c) {

        List<Archivo> retorno = new ArrayList<>();
        if (c.cumple(this)) {
            retorno.add(this);
        }
        return retorno;
    }

    public void addPalabraClave(Set<String> palabras){
        this.palabrasClaves.addAll(palabras);
    }

    @Override
    public Set<String> getPalabrasClave() {
        return new HashSet<>(palabrasClaves);
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setTamanio(Long tamanio) {
        this.tamanio = tamanio;
    }

    public Catedra getCatedra() {
        return catedra;
    }

    public void addFuente(InputStream filePart) {
        this.fuente = filePart;
    }

    public InputStream getFuente() {
        return this.fuente;
    }

}
