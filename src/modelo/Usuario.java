package modelo;

public class Usuario {
    private String mail;
    private String nombre;
    private int puntaje;

    public Usuario(String mail, String nombre, int puntaje){
        this.mail = mail;
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

    public Usuario(){
        this.mail = "";
        this.nombre = "";
        this.puntaje = 0;
    }

    public boolean isValid(){
        return this.mail != "" && this.nombre != "" && this.puntaje != 0;
    }
    
    public void setMail(String mail){
        this.mail = mail;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setPuntaje(int puntaje){
        this.puntaje = puntaje;
    }

    public String getMail(){
        return mail;
    }

    public String getNombre(){
        return nombre;
    }

    public int getPuntaje(){
        return puntaje;
    }

    public void subirArchivo(){

    }

    public void generarComentario(String comentario, Elemento elemento){
        elemento.agregarComentario(comentario);
    }

    public String verElemento(String nombre){
        return null;
    }

    @Override
    public String toString() {
        return "{" +
            " mail='" + getMail() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", puntaje='" + getPuntaje() + "'" +
            "}";
    }
}
