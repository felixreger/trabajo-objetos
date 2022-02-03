package modelo;

public class Opinion {
    private String descripcion;

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
    private Usuario autor;

    public Opinion(String descripcion, Usuario autor) {
        this.descripcion = descripcion;
        this.autor = autor;
    }
}