package com.trabajofinal.utils.criterios;

public interface ICriterio<E> {
    Boolean cumple(E elem);
}
