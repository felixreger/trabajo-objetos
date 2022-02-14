package test;

import main.modelo.Elemento;
import main.modelo.criterios.Criterio;
import main.modelo.criterios.CriterioNombreElemento;
import main.servicios.Servicios;
import java.util.ArrayList;
import java.util.List;

public class CriterioTest {
 
    public static void main(String[] args) {
        Criterio c1 = new CriterioNombreElemento("carpeta");
        Servicios s = Servicios.getInstance();

        List<Elemento> elementos = s.getElementos(); 

        System.out.println(elementos);
    }
}
