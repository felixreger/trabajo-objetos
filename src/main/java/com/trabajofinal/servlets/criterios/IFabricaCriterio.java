package com.trabajofinal.servlets.criterios;

import com.trabajofinal.modelo.criterios.ICriterio;

public interface IFabricaCriterio<T> {
    ICriterio getCriterio(T tipoCriterio);
}
