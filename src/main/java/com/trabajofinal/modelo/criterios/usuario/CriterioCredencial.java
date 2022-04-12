package com.trabajofinal.modelo.criterios.usuario;

import com.trabajofinal.modelo.Usuario;
import com.trabajofinal.modelo.criterios.ICriterio;

public interface CriterioCredencial extends ICriterio<Usuario> {
    @Override
    Boolean cumple(Usuario elem);
}
