package main.modelo.criterios;

import main.modelo.Elemento;

public class CriterioContieneNombreElemento implements Criterio{
    private String nombre;

    public CriterioContieneNombreElemento(String nombre) {
        this.nombre = nombre;
    }

	@Override
	public Boolean cumple(Elemento e) {
		return e.getNombre().contains(nombre);
	}
}
