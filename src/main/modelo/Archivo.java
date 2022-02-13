package main.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.modelo.criterios.Criterio;

public class Archivo extends Elemento {

    private Usuario propietario;
    private Catedra catedra;
    private Integer tamanio;

    public Archivo(String nombre, String tipo, Integer tamanio, LocalDate fechaModificacion, 
    LocalDate fechaCreacion, Catedra catedra, Usuario propietario, Carpeta padre) {
        super(nombre, tipo, fechaModificacion,  fechaCreacion, padre);
        this.propietario = propietario;
        this.catedra = catedra;
        this.tamanio = tamanio;
    }

    public Archivo() {
    }

    public String getPropietario(){
        return propietario.getMail();
    }
    
    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    @Override
    public Catedra getCatedra() {
        return catedra;
    }

    @Override
    public Integer getTamanio() {
        return tamanio;
    }

    @Override
    public List<Archivo> buscar(Criterio c) {
        
        List<Archivo> retorno = new ArrayList<>();
        if(c.cumple(this)){
            retorno.add(this);
        }
        return retorno;
    }

}
