package com.example.endpoints.auth.control;

import com.example.endpoints.auth.control.criterio.*;
import com.example.endpoints.auth.control.utils.UtilsControl;

public class FabricaCriterio {

    public CriterioCredencial getCriterioCredencial(String nombre, String us, String con, String autor){

        if(nombre.equalsIgnoreCase(UtilsControl.CREDENCIAL_ADMIN)){
            return new CriterioEsAdmin(us, con);

        } else if(nombre.equalsIgnoreCase(UtilsControl.CREDENCIAL_SIMPLE)){
            return new CriterioUsuarioYContrasenia(us, con);

        } else if(nombre.equalsIgnoreCase(UtilsControl.CREDENCIAL_COMPUESTA)){
            return new CriterioUsuarioContraseniaAdmin(us, con, autor);

        } else if(nombre.equalsIgnoreCase(UtilsControl.CREDENCIAL_SIMPLE_UNICA)){
            return new CriterioUsuarioAutor(us, con, autor);
        }
        return null;
    }
}
