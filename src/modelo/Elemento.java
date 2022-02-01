package modelo;

import java.time.LocalDate;
import java.util.List;

public class Elemento{
    private String nombre;
    private String tipo;
    private Integer tamanio;
    private List<Opinion> listaOpiniones;
    private LocalDate fechaModificacion;
    private LocalDate fechaCreacion;
    private Catedra catedra;


    public Elemento(String nombre,String tipo, Integer tamanio, List<Opinion> listaOpiniones, LocalDate fechaModificacion, LocalDate fechaCreacion, Catedra catedra){
        this.nombre = nombre;
        this.tipo = tipo;
        this.tamanio = tamanio;
        this.listaOpiniones.addAll(listaOpiniones);
        this.fechaModificacion = fechaModificacion;
        this.fechaCreacion = fechaCreacion;
        this.catedra = catedra;
    }

    public void eliminarElemento(Elemento elemento){
        return;
    }

    public String getNombre(){
        return this.nombre;
    }
}