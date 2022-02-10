package demo;

import modelo.Usuario;
import servicios.Servicios;

public class Main {
    public static void main(String[] args) {
        Servicios servicios = Servicios.getInstance();

        Usuario juan = servicios.getUsuario("juanchidure@outlook.com");
        System.out.println(juan.toString());

        //servicios.addUsuario(new Usuario("felixregert@gmail.com", "Felix Regert", 10));
        
        for (Usuario u : servicios.getUsuarios()) {
            System.out.println(u.toString());
        }
    }
    
}
