package com.trabajofinal.modelo.criterios.archivo;

import com.trabajofinal.modelo.Archivo;

public class ContieneNombreElemento implements CriterioArchivo {
    private final String nombre;

    public ContieneNombreElemento(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Verifica que el en el nombre del archivo este incluido el nombre dado.
     */
	@Override
	public Boolean cumple(Archivo e) {
		return e.getNombre().contains(nombre);
	}
}
