package com.trabajofinal.servlets.autentificacion.criterios;

import com.trabajofinal.utils.servlets.autentificacion.ConstantesFilter;

public class FabricaCriterio {

    public CriterioCredencial getCriterioCredencial(String nombre, String us, String con, String autor){

        if(nombre.equalsIgnoreCase(ConstantesFilter.CREDENCIAL_ADMIN)){
            return new Admin(us, con);

        } else if(nombre.equalsIgnoreCase(ConstantesFilter.CREDENCIAL_SIMPLE)){
            return new CredencialSimple(us, con);

        } else if(nombre.equalsIgnoreCase(ConstantesFilter.CREDENCIAL_COMPUESTA)){
            return new AdminCredencialSimple(us, con, autor);

        } else if(nombre.equalsIgnoreCase(ConstantesFilter.CREDENCIAL_SIMPLE_UNICA)){
            return new Autoria(us, con, autor);
        }
        return null;
    }
}
