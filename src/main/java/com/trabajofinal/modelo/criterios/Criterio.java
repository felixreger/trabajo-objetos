package com.trabajofinal.modelo.criterios;

import com.trabajofinal.modelo.Archivo;

public interface Criterio{

    Boolean cumple(Archivo e);
}