package com.trabajofinal.servlets.autentificacion.criterios;

import com.trabajofinal.utils.criterios.IFabricaCriterio;
import com.trabajofinal.utils.servlets.autentificacion.ConstantesFilter;

public class FabCriterioCredencial implements IFabricaCriterio<CriterioCredencial, String> {

    private final String user;
    private final String password;
    private final String autor;

    public FabCriterioCredencial(String user, String password, String autor) {
        this.user = user;
        this.password = password;
        this.autor = autor;
    }

    @Override
    public CriterioCredencial getCriterio(String nombre) {
        if(nombre.equalsIgnoreCase(ConstantesFilter.CREDENCIAL_ADMIN)){
            return new Admin(user, password);

        } else if(nombre.equalsIgnoreCase(ConstantesFilter.CREDENCIAL_SIMPLE)){
            return new CredencialSimple(user, password);

        } else if(nombre.equalsIgnoreCase(ConstantesFilter.CREDENCIAL_COMPUESTA)){
            return new AdminCredencialSimple(user, password, autor);

        } else if(nombre.equalsIgnoreCase(ConstantesFilter.CREDENCIAL_SIMPLE_UNICA)){
            return new Autoria(user, password, autor);
        }
        return null;
    }
}
