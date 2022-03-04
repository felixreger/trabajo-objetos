package com.example.servicios;

import com.example.exceptions.ExcepcionServicio;
import com.example.modelo.*;
import com.example.modelo.comparadores.elementos.ComparadorDirectorio;
import com.example.servicios.database.dao_impl.CatedraDao;
import com.example.servicios.database.dao_impl.ComentarioDao;
import com.example.servicios.database.dao_impl.UsuarioDao;
import com.example.servicios.database.dao_impl.elementos.ArchivoDao;
import com.example.servicios.database.dao_impl.elementos.CarpetaDao;

import java.sql.SQLException;
import java.util.List;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Servicios {

    private final CarpetaDao accesodbCarpeta = CarpetaDao.getInstance();
    private final ArchivoDao accesodbArchivo = ArchivoDao.getInstance();
    private final ComentarioDao accesodbComentario = ComentarioDao.getInstance();
    private final UsuarioDao accesodbUsuario = UsuarioDao.getInstance();
    private final CatedraDao accesodbCatedra = CatedraDao.getInstance();

    private static Servicios servicio = null;

    private Servicios() {
    }

    public static Servicios getInstance() {
        if (servicio == null)
            servicio = new Servicios();
        return servicio;
    }

    // region Usuario
    public List<Usuario> getUsuarios() throws ExcepcionServicio {
        try {
            return accesodbUsuario.getAll();
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public Usuario getUsuario(String mail) throws ExcepcionServicio {
        try {
            return accesodbUsuario.get(mail);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public void addUsuario(Usuario usuario) throws ExcepcionServicio {
        try {
            accesodbUsuario.add(usuario);
        }catch (Exception e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public void deleteUsuario(String mail) throws ExcepcionServicio {
        try {
            accesodbUsuario.delete(mail);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }
    public void updateUsuario(Usuario usuario) throws ExcepcionServicio {
        try {
            accesodbUsuario.update(usuario);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    // endregion

    // region Comentario
    public Comentario getComentario(Integer nombre) throws ExcepcionServicio {
        try {
            return accesodbComentario.get(nombre);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public List<Comentario> getComentarios(String nombreElem) throws ExcepcionServicio {
        try {
            return accesodbComentario.getComentarios(nombreElem);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public void addComentario(Comentario comentario) throws ExcepcionServicio {
        try {
            accesodbComentario.add(comentario);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public void updateComentario(Comentario comentario) throws ExcepcionServicio {
        try {
            accesodbComentario.update(comentario);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public void deleteComentario(Integer idCom) throws ExcepcionServicio {
        try {
            accesodbComentario.delete(idCom);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }
    // endregion

    // region Elemento

    public void deleteElemento(Elemento elemento) throws ExcepcionServicio {
        try {
            accesodbCarpeta.delete(elemento.getNombre());
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    // endregion

    // region Archivo
    public List<Elemento> getArchivos() throws ExcepcionServicio {
        try {
            return accesodbArchivo.getAll();
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public Elemento getArchivo(String id) throws ExcepcionServicio {
        try {
            return accesodbArchivo.get(id);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public void deleteArchivo(String nombreArchivo) throws ExcepcionServicio {
        try {
            accesodbArchivo.delete(nombreArchivo);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public void updateArchivo(Archivo archivo) throws ExcepcionServicio {
        try {
            accesodbArchivo.update(archivo);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public void addArchivo(Archivo archivo) throws ExcepcionServicio {
        try {
        accesodbArchivo.add(archivo);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    // endregion

    // region Carpeta
    // Si es hijo n enesimo de alguien
    private Boolean esPariente(Elemento elemento, String raiz) {

        // a b c d e f g

        // ruta: es el path anterior a mi.
        String[] path = elemento.getPadre().split(":");
        List<String> ruta = new ArrayList<String>(Arrays.asList(path));

        // el nombre de la raiz debe estar contenido en el padre del elemento
        return ruta.contains(raiz);
    }

    // Obtener referencia de padre a partir de un elemento
    // cont = Optimiza busqueda acotando el rango
    private Elemento getPadreFromElementos(List<Elemento> elementos, Elemento elemento, int cont){

        // GetPadre de hijo retorna A:B:C:D --> El padre de D es C

        String[] path = elemento.getPadre().split(":");

        String nombrePadre = path[path.length -2];
        
        Elemento padre = null;
        
        for (int index = cont; index < elementos.size(); index++) {

            Elemento elementoIndex = elementos.get(index);

            if(elementoIndex.getNombre().equals(nombrePadre)){
                padre = elementoIndex;
            }
        }

        if (padre == null)
            return new Carpeta();
        else
            return padre;
    }

    // A partir de el nombre de un directorio dado por parametro,
    // se retorna el subdirectorio asociado.
    public Carpeta getDirectorio(String raiz) throws ExcepcionServicio {
        List<Elemento> elementos;

        try {
            elementos = accesodbCarpeta.getAll();
            elementos.addAll(accesodbArchivo.getAll());
        } catch (SQLException e) {
            throw new ExcepcionServicio(e.getMessage());
        }

        // Ordenar la lista de elementos por longitud de nombre
        elementos.sort(new ComparadorDirectorio());

        // Se utiliza un contador para acotar la busqueda de padres
        int cont = 0;

        // Por cada elementro asigno hijos a padres
        for (Elemento elemento : elementos) {

            if (elemento.getNombre().equals(raiz)) {
                return (Carpeta) elemento;
            }

            // Revisa que el elemento que estoy mirando en la lista sea
            // hijo o nieto, etc de la raiz
            // para ello, el nombre de la raiz debe estar contenido en el padre del elemento
            if (this.esPariente(elemento, raiz)) {

                Carpeta padre = (Carpeta) getPadreFromElementos(elementos, elemento, cont);

                padre.addElemento(Collections.singletonList(elemento));

            }
            cont++;
        }

        return null;
    }

    // padre y elementoRaiz deben referenciar a la misma direccion de memoria
    public void deleteCarpeta(Carpeta carpeta) throws ExcepcionServicio {
        try {
            accesodbCarpeta.delete(carpeta.getNombre());
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public void updateCarpeta(Carpeta Carpeta) throws ExcepcionServicio {
        try {
            accesodbCarpeta.update(Carpeta);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public void addCarpeta(Carpeta Carpeta) throws ExcepcionServicio {
        try {
            accesodbCarpeta.add(Carpeta);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    // endregion

    // region Catedra
    public void addCatedra(Catedra catedra) throws ExcepcionServicio {
        try {
            accesodbCatedra.add(catedra);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public void deleteCatedra(Catedra catedra) throws ExcepcionServicio {
        try {
            accesodbCatedra.delete(catedra.getNombre());
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public void updateCatedra(Catedra catedra) throws ExcepcionServicio {
        try {
            accesodbCatedra.update(catedra);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public Catedra getCatedra(String nombre) throws ExcepcionServicio {
        try {
            return accesodbCatedra.get(nombre);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public List<Catedra> getCatedras() throws ExcepcionServicio {
        try {
            return accesodbCatedra.getAll();
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public boolean existeUsuario(String id) throws ExcepcionServicio {
        try {
            return accesodbUsuario.get(id).isValid();
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    // endregion
}
