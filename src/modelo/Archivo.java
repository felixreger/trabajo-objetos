package modelo;

import java.time.LocalDate;
import java.util.List;

public class Archivo extends Elemento {

    private Usuario propietario;

    public Archivo(String nombre, String tipo, Integer tamanio, List<Opinion> listaOpiniones, LocalDate fechaModificacion, LocalDate fechaCreacion, Catedra catedra, Usuario propietario) {
        super(nombre, tipo, tamanio, listaOpiniones, fechaModificacion,  fechaCreacion,  catedra);
        this.propietario = propietario;
    }
    
}
