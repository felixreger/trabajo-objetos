package modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Carpeta extends Elemento{
    
    private Set<Elemento> listaElementos;

    public Carpeta(String nombre,String tipo, Integer tamanio, LocalDate fechaModificacion, LocalDate fechaCreacion, Catedra catedra){
        super(nombre, tipo, tamanio, fechaCreacion, fechaCreacion, catedra);  
    }

    public Carpeta() {
    }

    public void addElemento(List<Elemento> listaElementos){
        this.listaElementos.addAll(listaElementos);
    }

    public void eliminarElemento(Elemento elemento){
        listaElementos.remove(elemento);
    }

    @Override
    public String getPropietario() {
        return null;
    }

    
}