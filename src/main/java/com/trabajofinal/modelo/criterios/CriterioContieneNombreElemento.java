package com.trabajofinal.modelo.criterios;

import com.trabajofinal.modelo.Archivo;

public class CriterioContieneNombreElemento implements Criterio{
    private String nombre;

    public CriterioContieneNombreElemento(String nombre) {
        this.nombre = nombre;
    }

	@Override
	public Boolean cumple(Archivo e) {
		return e.getNombre().contains(nombre);
	}
}
