package com.trabajofinal.servlets.autentificacion.criterios;

import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.utils.criterios.ICriterio;

public interface CriterioCredencial extends ICriterio<Usuario> {
    @Override
    Boolean cumple(Usuario elem);
}
