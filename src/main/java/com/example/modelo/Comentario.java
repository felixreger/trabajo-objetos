package com.example.modelo;

public class Comentario {
    private Integer id;
    private String descripcion;
    private Usuario autor;
    private String nombreElemento;
    
    public Comentario(Integer id, String descripcion, Usuario autor, String nombreElemento) {
        this.id = id;
        this.descripcion = descripcion;
        this.autor = autor;
        this.nombreElemento = nombreElemento;
    }
    
    public Comentario() {
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getAutor() {
        return this.autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Integer getId(){
        return id;
    }
    
    public void setNombreElemento(String nombreElem){
        this.nombreElemento = nombreElem;
    }

    public String getNombreElemento(){
        return nombreElemento;
    }

    @Override
    public String toString() {
        
        return "Comentario : " + descripcion + " , " + autor;
    }
    
}