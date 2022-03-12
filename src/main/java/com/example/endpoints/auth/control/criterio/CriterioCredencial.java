package com.example.endpoints.auth.control.criterio;

import com.example.modelo.Usuario;

public interface CriterioCredencial {

    Boolean cumple(Usuario u);

}
