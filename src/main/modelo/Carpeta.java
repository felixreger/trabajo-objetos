package main.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.modelo.criterios.Criterio;

public class Carpeta extends Elemento{
    
    private Set<Elemento> listaElementos;

    public Carpeta(String nombre,String tipo, LocalDate fechaModificacion, LocalDate fechaCreacion, Carpeta padre){
        super(nombre, tipo, fechaCreacion, fechaCreacion, padre);  
    }

    public Carpeta() {
    }

    public void addElemento(List<Elemento> listaElementos){
        this.listaElementos.addAll(listaElementos);
    }

    public void eliminarElemento(Elemento elemento){
        listaElementos.remove(elemento);
    }

    @Override // todo: agregar el propietario
    public String getPropietario() {
        return null;
    }

    @Override // todo: calcular con la mayor presencia 
    public Catedra getCatedra() {
        return new Catedra();
    }

    @Override // todo: calcular 
    public Integer getTamanio() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<Archivo> buscar(Criterio c) {
    
        List<Archivo> cumplen = new ArrayList<>();
        for (Elemento elem : listaElementos ){
            cumplen.addAll(elem.buscar(c));
        }
        return cumplen;
    }

}