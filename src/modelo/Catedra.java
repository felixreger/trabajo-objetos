package modelo;

public class Catedra {
    private String nombre;
    private String urlPaginaWeb;

    public Catedra(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public String getUrlPaginaWeb(){
        return urlPaginaWeb;
    }

}