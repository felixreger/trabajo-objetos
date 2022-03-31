package com.trabajofinal.utils.criterios;

public interface IFabricaCriterio<E, T> {
    E getCriterio(T tipoCriterio);
}
