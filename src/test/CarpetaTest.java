package test;

import main.modelo.Elemento;
import main.servicios.Servicios;

public class CarpetaTest {
    
    public static void main(String[] args) {
        Servicios servicio = Servicios.getInstance();

        Elemento el = servicio.getDirectorio("carpeta1");

        System.out.println(el);

    } 
}
