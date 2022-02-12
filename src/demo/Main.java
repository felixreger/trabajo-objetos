package demo;

import java.time.LocalDate;

import modelo.Archivo;
import modelo.Carpeta;
import modelo.Catedra;
import modelo.Comentario;
import modelo.Usuario;
import servicios.Servicios;

public class Main {
    public static void main(String[] args) {
        Servicios servicios = Servicios.getInstance();

        /*
        System.out.println("Inicio GetAll ");

        System.out.println("Usuarios" + servicios.getUsuarios());

        System.out.println("Archivos" + servicios.getArchivos());

        System.out.println("Carpetas" + servicios.getCarpetas());

        System.out.println("Comentarios de carpeta 1" + servicios.getComentarios("carpeta1"));

        System.out.println("Fin GetAll ");
        -------------------------------------------------------------------------------------
        System.out.println("Inicio por Id ");

        System.out.println("Usuario : " + servicios.getUsuario("felixregert@gmail.com"));

        System.out.println("Archivo : " + servicios.getArchivo("archivo1"));

        System.out.println("Carpeta : " + servicios.getCarpeta("carpeta1"));

        System.out.println("Fin por Id ");
        */
        
        System.out.println("Inicio Add ");

        Usuario u = new Usuario("jjjjj@gmail.com", "Juan dure", 40);
/*         servicios.addUsuario(u);
 */
        Carpeta c = new Carpeta("carpetaDeObjetosss", "carpeta", LocalDate.now(), LocalDate.now(), new Carpeta());

        servicios.addCarpeta(c);
        Comentario com = new Comentario(675, "Esto es un nuevo comentario", u, "carpetaDeObjetosss");
        servicios.addComentario(com);

/* 
        Archivo a = new Archivo("parcial205549","pdf",123,LocalDate.now(),LocalDate.now(),new Catedra("objetos","www.xyz"),u, new Carpeta());
        servicios.addArchivo(a);
 */

        System.out.println("Usuarios" + servicios.getUsuarios());

        System.out.println("Archivos" + servicios.getArchivos());

        System.out.println("Carpetas" + servicios.getCarpetas());

        System.out.println("Comentarios de carpeta 1" + servicios.getComentarios("carpeta1"));

        System.out.println("Fin Add ");
        
    }
}
