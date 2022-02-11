package modelo;

import java.time.LocalDate;

public class Archivo extends Elemento {

    private Usuario propietario;
    private Catedra catedra;
    private Integer tamanio;

    public Archivo(String nombre, String tipo, Integer tamanio, LocalDate fechaModificacion, 
    LocalDate fechaCreacion, Catedra catedra, Usuario propietario) {
        super(nombre, tipo, fechaModificacion,  fechaCreacion);
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

}
