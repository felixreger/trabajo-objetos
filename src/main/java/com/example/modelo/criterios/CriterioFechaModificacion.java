package com.example.modelo.criterios;

import com.example.modelo.Elemento;

import java.time.LocalDate;



public class CriterioFechaModificacion implements Criterio {
    private LocalDate fechaModificacion;

    public LocalDate getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public CriterioFechaModificacion(LocalDate fechaModificacion){
        this.fechaModificacion = fechaModificacion;
    }

	@Override
	public Boolean cumple(Elemento e) {
        return e.getFechaModificacion().isEqual(this.fechaModificacion);
	}
}
