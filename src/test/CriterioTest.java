package test;

import main.modelo.Archivo;
import main.modelo.Elemento;
import main.modelo.comparadores.elementos.ComparadorTamanio;
import main.modelo.criterios.Criterio;
import main.modelo.criterios.CriterioNombreElemento;
import main.servicios.Servicios;
import java.util.List;

public class CriterioTest {
 
    public static void main(String[] args) {
        Criterio cr1 = new CriterioNombreElemento("carpeta 2021");
        Servicios s = Servicios.getInstance();

        //Elemento c1 = s.getCarpetas().get(0);

        //List<Archivo> lista = c1.filtrar(cr1);

        //lista.sort(new ComparadorTamanio());
        /* Collection.sort(lista,(Comparator) com1); */
    }
}
