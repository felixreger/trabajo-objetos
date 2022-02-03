package modelo.criterios;

import java.time.LocalDate;

public class FechaCreacion {
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
}
