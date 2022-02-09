package modelo;

import java.time.LocalDate;
import servicios.Servicios;

public class Elemento{
    private String nombre;
    private String tipo;
    private Integer tamanio;
    private LocalDate fechaModificacion;
    private LocalDate fechaCreacion;
    private Catedra catedra;

    private Servicios servicio = Servicios.getInstance();

    public boolean isValid(){
        return 
        nombre == null
          && tamanio == null
        && fechaModificacion == null
        && fechaCreacion == null
        && catedra ==
         null;    }

    public Elemento(){
        this.nombre = null;
        this.tipo = null;
        this.fechaModificacion = null;
        this.fechaCreacion = null;
        this.catedra = null;
    }

    public Elemento(String nombre,String tipo, Integer tamanio, LocalDate fechaModificacion, LocalDate fechaCreacion, Catedra catedra){
        this.nombre = nombre;
        this.tipo = tipo;
        this.tamanio = tamanio;
        this.fechaModificacion = fechaModificacion;
        this.fechaCreacion = fechaCreacion;
        this.catedra = catedra;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getTamanio() {
        return this.tamanio;
    }

    public void setTamanio(Integer tamanio) {
        this.tamanio = tamanio;
    }

    public LocalDate getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Catedra getCatedra() {
        return this.catedra;
    }

    public void setCatedra(Catedra catedra) {
        this.catedra = catedra;
    }

    public String getNombre(){
        return this.nombre;
    }

	public void agregarComentario(Comentario comentario) {
        servicio.addComentario(comentario);
	}

}