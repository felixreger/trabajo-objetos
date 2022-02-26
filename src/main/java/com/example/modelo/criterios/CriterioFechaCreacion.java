package com.example.modelo.criterios;

import com.example.modelo.Elemento;

import java.time.LocalDate;



public class CriterioFechaCreacion implements Criterio {
    private LocalDate fechaCreacion;

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public CriterioFechaCreacion(LocalDate fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }

	@Override
	public Boolean cumple(Elemento e) {
		return e.getFechaCreacion().isEqual(this.fechaCreacion);
	}
}
