package com.trabajofinal.modelo.criterios;

import com.trabajofinal.modelo.Archivo;
import com.trabajofinal.utils.criterios.ICriterio;

public interface CriterioArchivo extends ICriterio<Archivo> {
    @Override
    Boolean cumple(Archivo elem);
}
