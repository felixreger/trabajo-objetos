package main.demo;

import java.util.ArrayList;
import java.util.List;

import main.modelo.Archivo;
import main.modelo.Carpeta;
import main.modelo.Elemento;
import main.modelo.criterios.Criterio;
import main.modelo.criterios.CriterioContieneNombreElemento;
import main.modelo.criterios.CriterioTipo;
import main.servicios.Servicios;

public class Main {      
  
    public static void main(String[] args) {

        Servicios s = Servicios.getInstance();

        Elemento dir = s.getDirectorio("carpeta1");

        Criterio c = new CriterioTipo("archivo");

        List<Archivo> arch = dir.filtrar(c);

        System.out.println();
    }   
}
