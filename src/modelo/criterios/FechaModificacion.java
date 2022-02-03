package modelo.criterios;

import java.time.LocalDate;


public class FechaModificacion {
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
}
