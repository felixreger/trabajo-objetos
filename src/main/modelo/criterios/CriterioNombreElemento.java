package main.modelo.criterios;

import main.modelo.Elemento;

public class CriterioNombreElemento implements Criterio{
    private String nombre;

    public CriterioNombreElemento(String nombre) {
        this.nombre = nombre;
    }

	@Override
	public Boolean cumple(Elemento e) {
		return e.getNombre().equals(nombre);
	}
}
