package com.trabajofinal.modelo.criterios;

import com.trabajofinal.modelo.Archivo;

import java.time.LocalDate;



public class CriterioFechaCreacion implements Criterio {
    private LocalDate fechaCreacion;

    public CriterioFechaCreacion(LocalDate fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }

	@Override
	public Boolean cumple(Archivo e) {
		return e.getFechaCreacion().isEqual(this.fechaCreacion);
	}
}
