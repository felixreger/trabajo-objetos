package main.modelo.criterios;

import java.time.LocalDate;

import main.modelo.Elemento;


public class FechaModificacion implements Criterio {
    private LocalDate fechaModificacion;

    public LocalDate getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public FechaModificacion(LocalDate fechaModificacion){
        this.fechaModificacion = fechaModificacion;
    }

	@Override
	public Boolean cumple(Elemento e) {
		// TODO Auto-generated method stub
		return null;
	}
}
