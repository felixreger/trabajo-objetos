package demo;

import modelo.Usuario;
import servicios.Servicios;

public class Main {
    public static void main(String[] args) {
        Servicios servicios = Servicios.getInstance();

        System.out.println("-----------------------------------------------------------");
        System.out.println("servicios.getUsuarios()");
        System.out.println(servicios.getUsuarios());
                System.out.println("-----------------------------------------------------------");

        System.out.println(servicios.getArchivos());
                System.out.println("-----------------------------------------------------------");

        System.out.println(servicios.getCarpetas());
        //System.out.println(servicios.getComentarios());
        System.out.println("-----------------------------------------------------------");
    }
}
