package com.example.endpoints.auth.control;

import com.example.endpoints.auth.control.criterio.CriterioCredencial;
import com.example.endpoints.auth.control.criterio.CriterioEsAdmin;
import com.example.endpoints.auth.control.criterio.CriterioUsuarioContraseniaAdmin;
import com.example.endpoints.auth.control.criterio.CriterioUsuarioYContrasenia;
import com.example.modelo.criterios.Criterio;

public class FabricaCriterio {

    public CriterioCredencial getCriterioCredencial(String nombre, String us, String con, String autor){

        if(nombre.equalsIgnoreCase("CREDENCIALADMIN")){
            return new CriterioEsAdmin(us, con);

        } else if(nombre.equalsIgnoreCase("CREDENCIALSIMPLE")){
            return new CriterioUsuarioYContrasenia(us, con);

        } else if(nombre.equalsIgnoreCase("CREDENCIALCOMPUESTA")){
            return new CriterioUsuarioContraseniaAdmin(us, con, autor);
        }
        return null;
    }
}
