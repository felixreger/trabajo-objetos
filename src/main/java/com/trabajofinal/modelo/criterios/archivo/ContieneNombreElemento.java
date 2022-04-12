package com.trabajofinal.modelo.criterios.archivo;

import com.trabajofinal.modelo.Archivo;

public class ContieneNombreElemento implements CriterioArchivo {
    private final String nombre;

    public ContieneNombreElemento(String nombre) {
        this.nombre = nombre;
    }

	@Override
	public Boolean cumple(Archivo e) {
		return e.getNombre().contains(nombre);
	}
}
