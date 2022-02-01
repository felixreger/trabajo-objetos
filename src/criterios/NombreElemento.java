package criterios;

import modelo.Elemento;

public class NombreElemento implements Criterio{
    private String nombre;

    public NombreElemento(String nombre) {
        this.nombre = nombre;
    }

	@Override
	public Boolean cumple(Elemento e) {
		return e.getNombre().equals(nombre);
	}

}
