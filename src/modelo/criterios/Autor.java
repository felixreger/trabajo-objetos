package modelo.criterios;

import modelo.Usuario;

public class Autor {
    private Usuario autor;

    public Usuario getAutor() {
        return this.autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Autor(Usuario usuario){
        autor = usuario;
    }
}
