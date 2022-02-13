package main.servicios;

import java.util.List;

import main.modelo.Archivo;
import main.modelo.Carpeta;
import main.modelo.Catedra;
import main.modelo.Comentario;
import main.modelo.Elemento;
import main.modelo.Usuario;
import main.servicios.database.dao_impl.CatedraDao;
import main.servicios.database.dao_impl.ComentarioDao;
import main.servicios.database.dao_impl.UsuarioDao;
import main.servicios.database.dao_impl.elementos.ArchivoDao;
import main.servicios.database.dao_impl.elementos.CarpetaDao;

import java.util.ArrayList;

public class Servicios {

    private CarpetaDao accesodbCarpeta = CarpetaDao.getInstance();
    private ArchivoDao accesodbArchivo = ArchivoDao.getInstance();
    private ComentarioDao accesodbComentario = ComentarioDao.getInstance();
    private UsuarioDao accesodbUsuario = UsuarioDao.getInstance();
    private CatedraDao accesodbCatedra = CatedraDao.getInstance();

    private static Servicios servicio = null;

    private Servicios(){}

    public static Servicios getInstance(){
		if (servicio == null)
            servicio = new Servicios();
		return servicio;
	}

    //region Usuario
    public List<Usuario> getUsuarios() {
        return accesodbUsuario.getAll();
    }
    
    public Usuario getUsuario(String mail) {
        return accesodbUsuario.get(mail);
    }
    
    public void addUsuario(Usuario usuario){
        accesodbUsuario.add(usuario);
    }
    
    public boolean deleteUsuario(String mail){
        return accesodbUsuario.delete(mail);
    }

    public void updateUsuario(Usuario usuario){
        accesodbUsuario.update(usuario);
    }
    //endregion
        
   
    //region Comentario
    public Comentario getComentario(Integer nombre){
        return accesodbComentario.get(nombre);
    }

    public List<Comentario> getComentarios(String nombreElem){
        return accesodbComentario.getComentarios(nombreElem);
    }

    public void addComentario(Comentario comentario){
        accesodbComentario.add(comentario);
    }

    public void updateComentario(Comentario comentario){
        accesodbComentario.update(comentario);
    }    
    
    public boolean deleteComentario(Integer idCom){
        return accesodbComentario.delete(idCom);
    }
    //endregion
    

    //region Elemento
    public List<Elemento> getElementos(){
        List<Elemento> elementos = new ArrayList<>(getArchivos());
        elementos.addAll(getCarpetas());
        return elementos;
    }

    public boolean deleteElemento(Elemento elemento){
        return accesodbCarpeta.delete(elemento.getNombre());
    }

    //endregion


    //region Archivo
    public List<Elemento> getArchivos(){
        return accesodbArchivo.getAll();
    }
    
    public Elemento getArchivo(String id){
        return accesodbArchivo.get(id);
    }
    
    public boolean deleteArchivo(Archivo archivo){
        return accesodbArchivo.delete(archivo.getNombre());
    }

    public void updateArchivo(Archivo archivo){
        accesodbArchivo.update(archivo);
    }

    public void addArchivo(Archivo archivo){
        accesodbArchivo.add(archivo);
    }
    //endregion
    
    
    //region Carpeta
    public List<Elemento> getCarpetas(){
        return accesodbCarpeta.getAll();
    }
    
    public Elemento getCarpeta(String id){
        return accesodbCarpeta.get(id);
    }
    
    public boolean deleteCarpeta(Carpeta carpeta){
        return accesodbCarpeta.delete(carpeta.getNombre());
    }

    public void updateCarpeta(Carpeta Carpeta){
        accesodbCarpeta.update(Carpeta);
    }

    public void addCarpeta(Carpeta Carpeta){
        accesodbCarpeta.add(Carpeta);
    }

    //endregion

    //region Catedra
    public void addCatedra(Catedra catedra){
        accesodbCatedra.add(catedra);
    }

    public void deleteCatedra(Catedra catedra){
        accesodbCatedra.delete(catedra.getNombre());
    }
    
    public void updateCatedra(Catedra catedra){
        accesodbCatedra.update(catedra);
    }

    public Catedra getCatedra(String nombre){
        return accesodbCatedra.get(nombre);
    }

    public List<Catedra> getCatedras(){
        return accesodbCatedra.getAll();
    }
    //endregion
}
