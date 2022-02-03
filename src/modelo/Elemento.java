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

    public List<Opinion> getListaOpiniones() {
        return this.listaOpiniones;
    }

    public void setListaOpiniones(List<Opinion> listaOpiniones) {
        this.listaOpiniones = listaOpiniones;
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

    public void eliminarElemento(Elemento elemento){
        return;
    }

    public String getNombre(){
        return this.nombre;
    }

	public void agregarComentario(String comentario) {
	}
}