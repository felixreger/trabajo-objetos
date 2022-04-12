package com.trabajofinal.modelo.criterios;

public interface ICriterio<E> {
    Boolean cumple(E elem);
}
