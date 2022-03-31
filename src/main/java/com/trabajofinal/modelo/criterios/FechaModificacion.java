package com.trabajofinal.modelo.criterios;

import com.trabajofinal.modelo.Archivo;

import java.time.LocalDate;

public class FechaModificacion implements CriterioArchivo {
    private final LocalDate fechaModificacion;

    public FechaModificacion(LocalDate fechaModificacion){
        this.fechaModificacion = fechaModificacion;
    }

	@Override
	public Boolean cumple(Archivo e) {
        return e.getFechaModificacion().isEqual(this.fechaModificacion);
	}
}
