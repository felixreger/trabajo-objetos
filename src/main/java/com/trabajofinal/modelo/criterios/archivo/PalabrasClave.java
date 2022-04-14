package com.trabajofinal.modelo.criterios.archivo;

import com.trabajofinal.modelo.Archivo;

import java.util.Set;

public class PalabrasClave implements CriterioArchivo{

    private final Set<String> palabrasClave;

    public PalabrasClave(Set<String> palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    /**
     * Verifica que alguna palabra clave este dentro de el archivo.
     */
    @Override
    public Boolean cumple(Archivo elem) {

        for (String tmp: elem.getPalabrasClave()){
            if(this.palabrasClave.contains(tmp))
                return true;
        }
        return false;
    }
}
