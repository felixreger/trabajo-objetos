package com.trabajofinal.modelo.criterios;

import com.trabajofinal.modelo.Archivo;

import java.time.LocalDate;

public class FechaCreacion implements CriterioArchivo {
    private final LocalDate fechaCreacion;

    public FechaCreacion(LocalDate fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }

	@Override
	public Boolean cumple(Archivo e) {
		return e.getFechaCreacion().isEqual(this.fechaCreacion);
	}
}
