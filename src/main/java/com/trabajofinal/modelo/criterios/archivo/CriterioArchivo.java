package com.trabajofinal.modelo.criterios.archivo;

import com.trabajofinal.modelo.Archivo;
import com.trabajofinal.modelo.criterios.ICriterio;

public interface CriterioArchivo extends ICriterio<Archivo> {
    @Override
    Boolean cumple(Archivo elem);
}
