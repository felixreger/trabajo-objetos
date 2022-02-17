package main.demo;

import java.util.ArrayList;
import java.util.List;

import main.modelo.Archivo;
import main.modelo.Carpeta;
import main.modelo.Elemento;

public class Main {      
  
    public static void main(String[] args) {

        Elemento a = new Archivo();
        Elemento b = new Carpeta();

        List<Elemento> c = new ArrayList<>();

        c.add(a);
        c.add(b);

        Archivo d = (Archivo)c.get(0);

        System.out.println(a);
        System.out.println(d);

    }   
}
