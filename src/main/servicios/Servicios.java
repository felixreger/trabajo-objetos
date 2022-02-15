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
    public List<Elemento> getElementos(){ // todo: puede no estar
        List<Elemento> elementos = new ArrayList<>(getArchivos());
        //elementos.addAll(getCarpetas());
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
    // Si es hijo n enesimo de alguien
    private Boolean esPariente (Elemento padre, String raiz){
        List<String> ruta = new ArrayList<String>(Arrays.asList(raiz.split(":")));
        return ruta.contains(padre.getNombre());
    }

    // Obtener referencia de padre a partir de un elemento
    // cont = Optimiza busqueda acotando el rango
    private Elemento getPadreFromElementos(List<Elemento> elementos, Elemento elemento, int cont){

        // GetPadre de hijo retorna A:B:C:D --> El padre de D es C
        String nombrePadre = elemento.getNombrePadre();
        
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
    public Elemento getDirectorio(String raiz){

        //Tengo todos los elementos
        List<Elemento> elementos = accesodbCarpeta.getAll();
        
        elementos.sort( new ComparadorDirectorio());

        Elemento raizElemento = accesodbCarpeta.get(raiz); // Obtengo referencia de la raiz

        // Se utiliza un contador para acotar la busqueda de padres
        int cont = 0;
        
        // Por cada elementro asigno hijos a padres
        for (Elemento elemento : elementos) {
            
            if (this.esPariente(elemento, raiz)){
                
                Carpeta padre = (Carpeta)this.getPadreFromElementos(elementos,elemento, cont);
                padre.addElemento(Collections.singletonList(elemento)) ;

            }
            cont++;   
        }

        // A B C D F E Ordenado de mayor a menor longitud
    
        // F E D C B A 

        /*
                A
            B       C
        D     F          E
        
        D seria A:B:D
        B seria A:B
        C seria A:C
        E seria A:C:E
        F seria A:B:F
        
        -- Ordenado por longitud

        B seria A:B
        C seria A:C
        D seria A:B:D
        E seria A:C:E
        F seria A:B:F


        1ero: tengo que encontrar A. 
        2do: encontrar a B y C o C y B
        3ro a: tomo a B 



        */

        return raizElemento;
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
