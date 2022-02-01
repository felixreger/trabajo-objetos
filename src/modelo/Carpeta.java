package modelo;

import java.time.LocalDate;
import java.util.List;

public class Carpeta extends Elemento{
    
    private List<Elemento> listaElementos;

    public Carpeta(String nombre,String tipo, Integer tamanio, List<Opinion> listaOpiniones, LocalDate fechaModificacion, LocalDate fechaCreacion, Catedra catedra){
        super(nombre, tipo, tamanio, listaOpiniones, fechaCreacion, fechaCreacion, catedra);  
    }

    public void addElemento(List<Elemento> listaElementos){
        this.listaElementos.addAll(listaElementos);
    }

    public void eliminarElemento(Elemento elemento){
        listaElementos.remove(elemento);
    }
}