package com.trabajofinal.modelo.criterios;

import com.trabajofinal.modelo.Archivo;

import java.time.LocalDate;



public class CriterioFechaModificacion implements Criterio {
    private LocalDate fechaModificacion;

    public CriterioFechaModificacion(LocalDate fechaModificacion){
        this.fechaModificacion = fechaModificacion;
    }

	@Override
	public Boolean cumple(Archivo e) {
        return e.getFechaModificacion().isEqual(this.fechaModificacion);
	}
}
