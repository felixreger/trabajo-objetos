package modelo;

public class Catedra {
    private String nombre;
    private String urlPaginaWeb;

    public Catedra(){

    }

    public Catedra(String nombre) {
        this.nombre = nombre;
    }

    public Catedra(String nombre, String urlPaginaWeb) {
        this.nombre = nombre;
        this.urlPaginaWeb = urlPaginaWeb;
    }

    public String getNombre(){
        return nombre;
    }

    public String getUrlPaginaWeb(){
        return urlPaginaWeb;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setPaginaWeb(String urlPaginaWeb){
        this.urlPaginaWeb = urlPaginaWeb;
    }


}