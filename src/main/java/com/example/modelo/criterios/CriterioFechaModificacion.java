package com.example.modelo.criterios;

import com.example.modelo.Archivo;
import com.example.modelo.Elemento;

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
