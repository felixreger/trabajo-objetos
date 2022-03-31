package com.trabajofinal.servicios;

import com.trabajofinal.excepciones.ExcepcionServicio;
import com.trabajofinal.modelo.*;
import com.trabajofinal.modelo.comparadores.elementos.ComparadorDirectorio;
import com.trabajofinal.servicios.database.dao_jdbc.CatedraDao;
import com.trabajofinal.servicios.database.dao_jdbc.ComentarioDao;
import com.trabajofinal.servicios.database.dao_jdbc.UsuarioDao;
import com.trabajofinal.servicios.database.dao_jdbc.elementos.ArchivoDao;
import com.trabajofinal.servicios.database.dao_jdbc.elementos.CarpetaDao;
import com.trabajofinal.utils.servlets.endpoints.ArchivoBytes;

import java.sql.SQLException;
import java.util.List;

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

    public List<Usuario> getTopUsuarios(int top) throws ExcepcionServicio {
        try {
            return accesodbUsuario.getTop(top);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public boolean existeUsuario(String id) throws ExcepcionServicio {
        try {
            return accesodbUsuario.exist(id);
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

    public boolean existeComentario(Integer idComentario) throws ExcepcionServicio {
        try {
            return accesodbComentario.exist(idComentario);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public int getUltimoComentarioId() throws ExcepcionServicio {
        try {
            return accesodbComentario.getUltimoId();
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    // endregion

    // region Archivo
    public void deleteArchivo(String nombreArchivo) throws ExcepcionServicio {
        try {
            accesodbArchivo.delete(nombreArchivo);
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

    public ArchivoBytes getArchivoFuente(String pathArchivo) throws ExcepcionServicio {
        try {
            return accesodbArchivo.getArchivoFuente(pathArchivo);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }
    // endregion

    // region Carpeta

    private Boolean esPariente(Elemento elemento, String raiz) {
        return elemento.getPath().contains(raiz);
    }

    // Obtener referencia de padre a partir de un elemento
    private Elemento getPadreFromElementos(List<Elemento> elementos, Elemento elemento, int cont){
        String path = elemento.getPath();

        for (int index = cont; index < elementos.size(); index++) {
            Elemento elementoIndex = elementos.get(index);
            if(path.equals(elementoIndex.getPath() + ":" +elementoIndex.getNombre())){
                return elementoIndex;
            }
        }
        return new Carpeta();
    }

    // A partir de el nombre de un directorio dado por parametro,
    public Carpeta getDirectorio(String directorioBase) throws ExcepcionServicio {
        List<Elemento> elementos;
        try {
            elementos = accesodbCarpeta.getAll();
            elementos.addAll(accesodbArchivo.getAll());
        } catch (SQLException e) {
            throw new ExcepcionServicio(e.getMessage());
        }

        // Ordenar la lista de elementos por longitud de de path
        elementos.sort(new ComparadorDirectorio());

        // Se utiliza un contador para acotar la busqueda de padres
        int cont = 0;

        // Por cada elementro asigno hijos a padres
        for (Elemento elemento : elementos) {
            String tmp = elemento.getPath() + ":" +elemento.getNombre();
            if (tmp.equals(directorioBase)) {
                return (Carpeta) elemento;
            }
            // Revisa que el elemento que estoy mirando en la lista sea hijo o nieto, etc de la raiz
            // para ello, el nombre de la raiz debe estar contenido en el padre del elemento
            if (this.esPariente(elemento, directorioBase)) {
                Carpeta padre = (Carpeta) getPadreFromElementos(elementos, elemento, cont);
                padre.addElemento(elemento);
            }
            cont++;
        }

        return new Carpeta();
    }

    public void deleteCarpeta(String carpeta) throws ExcepcionServicio {
        try {
            accesodbCarpeta.delete(carpeta);
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

    public void deleteCatedra(String catedra) throws ExcepcionServicio {
        try {
            accesodbCatedra.delete(catedra);
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

    public boolean existeCatedra(String idCatedra) throws ExcepcionServicio {
        try {
            return accesodbCatedra.exist(idCatedra);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }
    // endregion

    public boolean existeDirectorio(String path) throws ExcepcionServicio {
        try {
            return accesodbCarpeta.exist(path);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    public boolean existeElemento(String path) throws ExcepcionServicio {
        return this.existeDirectorio(path);
    }

    // region desuso momentaneo
    public Elemento getArchivo(String id) throws ExcepcionServicio {
        try {
            return accesodbArchivo.get(id);
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

    public void updateCarpeta(Carpeta Carpeta) throws ExcepcionServicio {
        try {
            accesodbCarpeta.update(Carpeta);
        }catch (SQLException e){
            throw new ExcepcionServicio(e.getMessage());
        }
    }

    // endregion
}
