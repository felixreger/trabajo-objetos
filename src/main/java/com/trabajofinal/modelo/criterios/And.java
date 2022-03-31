package com.trabajofinal.modelo.criterios;


import com.trabajofinal.modelo.Archivo;

public class And implements CriterioArchivo {
    private final CriterioArchivo criterio1;
    private final CriterioArchivo criterio2;

    public And(CriterioArchivo c1, CriterioArchivo c2){
        criterio1 = c1;
        criterio2 = c2;
    }

	@Override
	public Boolean cumple(Archivo e) {
		return criterio1.cumple(e) && criterio2.cumple(e);
	}
}
