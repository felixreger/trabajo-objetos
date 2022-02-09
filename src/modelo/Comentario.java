package modelo;

public class Comentario {
    private String descripcion;
    private Usuario autor;
    
    public Comentario(String descripcion, Usuario autor) {
        this.descripcion = descripcion;
        this.autor = autor;
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
    
}