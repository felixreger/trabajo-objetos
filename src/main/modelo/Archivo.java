package main.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.modelo.criterios.Criterio;

public class Archivo extends Elemento implements Comparator<Elemento> {

    private Integer tamanio;

    public Archivo(String nombre, String tipo, Integer tamanio, LocalDate fechaModificacion,
            LocalDate fechaCreacion, Catedra catedra, Usuario propietario, String padre) {
        super(nombre, tipo, fechaModificacion, fechaCreacion, padre, propietario, catedra);
        
        this.tamanio = tamanio;
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

    @Override
    public int compare(Elemento o1, Elemento o2) {
        return o1.getNombre().compareTo(o2.getNombre());
    }

}
