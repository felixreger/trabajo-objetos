package com.example.modelo.criterios;

import com.example.modelo.Archivo;
import com.example.modelo.Elemento;

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
