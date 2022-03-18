package com.trabajofinal.servlets.autentificacion.criterios;

import com.trabajofinal.modelo.Usuario;

public interface CriterioCredencial {

    Boolean cumple(Usuario u);

}
