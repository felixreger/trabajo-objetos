package main.modelo.criterios;

import java.time.LocalDate;

import main.modelo.Elemento;

public class FechaCreacion implements Criterio {
    private LocalDate fechaCreacion;

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public FechaCreacion(LocalDate fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }

	@Override
	public Boolean cumple(Elemento e) {
		return e.getFechaCreacion().isEqual(this.fechaCreacion);
	}
}
