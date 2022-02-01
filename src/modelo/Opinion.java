package modelo;

public class Opinion {
    private String descripcion;
    private Usuario autor;

    public Opinion(String descripcion, Usuario autor) {
        this.descripcion = descripcion;
        this.autor = autor;
    }
}