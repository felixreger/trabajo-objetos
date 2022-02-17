package main.servicios;

import java.util.List;

import main.modelo.Archivo;
import main.modelo.Carpeta;
import main.modelo.Catedra;
import main.modelo.Comentario;
import main.modelo.Elemento;
import main.modelo.Usuario;
import main.modelo.comparadores.elementos.ComparadorDirectorio;
import main.servicios.database.dao_impl.CatedraDao;
import main.servicios.database.dao_impl.ComentarioDao;
import main.servicios.database.dao_impl.UsuarioDao;
import main.servicios.database.dao_impl.elementos.ArchivoDao;
import main.servicios.database.dao_impl.elementos.CarpetaDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Servicios {

    private CarpetaDao accesodbCarpeta = CarpetaDao.getInstance();
    private ArchivoDao accesodbArchivo = ArchivoDao.getInstance();
    private ComentarioDao accesodbComentario = ComentarioDao.getInstance();
    private UsuarioDao accesodbUsuario = UsuarioDao.getInstance();
    private CatedraDao accesodbCatedra = CatedraDao.getInstance();

    private static Servicios servicio = null;

    private Servicios() {
    }

    public static Servicios getInstance() {
        if (servicio == null)
            servicio = new Servicios();
        return servicio;
    }

    // region Usuario
    public List<Usuario> getUsuarios() {
        return accesodbUsuario.getAll();
    }

    public Usuario getUsuario(String mail) {
        return accesodbUsuario.get(mail);
    }

    public void addUsuario(Usuario usuario) {
        accesodbUsuario.add(usuario);
    }

    public boolean deleteUsuario(String mail) {
        return accesodbUsuario.delete(mail);
    }

    public void updateUsuario(Usuario usuario) {
        accesodbUsuario.update(usuario);
    }
    // endregion

    // region Comentario
    public Comentario getComentario(Integer nombre) {
        return accesodbComentario.get(nombre);
    }

    public List<Comentario> getComentarios(String nombreElem) {
        return accesodbComentario.getComentarios(nombreElem);
    }

    public void addComentario(Comentario comentario) {
        accesodbComentario.add(comentario);
    }

    public void updateComentario(Comentario comentario) {
        accesodbComentario.update(comentario);
    }

    public boolean deleteComentario(Integer idCom) {
        return accesodbComentario.delete(idCom);
    }
    // endregion

    // region Elemento
    public List<Elemento> getElementos() { // todo: puede no estar
        List<Elemento> elementos = new ArrayList<>(getArchivos());
        // elementos.addAll(getCarpetas());
        return elementos;
    }

    public boolean deleteElemento(Elemento elemento) {
        return accesodbCarpeta.delete(elemento.getNombre());
    }

    // endregion

    // region Archivo
    public List<Elemento> getArchivos() {
        return accesodbArchivo.getAll();
    }

    public Elemento getArchivo(String id) {
        return accesodbArchivo.get(id);
    }

    public boolean deleteArchivo(Archivo archivo) {
        return accesodbArchivo.delete(archivo.getNombre());
    }

    public void updateArchivo(Archivo archivo) {
        accesodbArchivo.update(archivo);
    }

    public void addArchivo(Archivo archivo) {
        accesodbArchivo.add(archivo);
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
    public Carpeta getDirectorio(String raiz) {

        // Cargar en una lista de elementos todos los elementos que sean Carpetas 
        List<Elemento> elementos = accesodbCarpeta.getAll();

        // Cargar en la lista de elementos todos los archivos
        elementos.addAll(accesodbArchivo.getAll());

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
    public boolean deleteCarpeta(Carpeta carpeta) {
        return accesodbCarpeta.delete(carpeta.getNombre());
    }

    public void updateCarpeta(Carpeta Carpeta) {
        accesodbCarpeta.update(Carpeta);
    }

    public void addCarpeta(Carpeta Carpeta) {
        accesodbCarpeta.add(Carpeta);
    }

    // endregion

    // region Catedra
    public void addCatedra(Catedra catedra) {
        accesodbCatedra.add(catedra);
    }

    public void deleteCatedra(Catedra catedra) {
        accesodbCatedra.delete(catedra.getNombre());
    }

    public void updateCatedra(Catedra catedra) {
        accesodbCatedra.update(catedra);
    }

    public Catedra getCatedra(String nombre) {
        return accesodbCatedra.get(nombre);
    }

    public List<Catedra> getCatedras() {
        return accesodbCatedra.getAll();
    }
    // endregion
}
