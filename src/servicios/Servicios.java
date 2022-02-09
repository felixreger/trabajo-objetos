package servicios;

import java.util.List;

import modelo.Comentario;
import modelo.Elemento;
import modelo.Usuario;
import servicios.database.comentario.ComentarioDao;
import servicios.database.comentario.IComentarioDao;
import servicios.database.elemento.ElementoDao;
import servicios.database.elemento.IElementoDao;
import servicios.database.usuario.IUsuarioDao;
import servicios.database.usuario.UsuarioDao;

public class Servicios {

    private IElementoDao accesodbElemento = ElementoDao.getInstance();
    private IComentarioDao accesodbComentario = ComentarioDao.getInstance();
    private IUsuarioDao accesodbUsuario = UsuarioDao.getInstance();

    private static Servicios servicio = null;

    private Servicios(){}

    public static Servicios getInstance(){
		if (servicio == null)
            servicio = new Servicios();
		return servicio;
	}

    public void addComentario(Comentario comentario){
        accesodbComentario.addComentario(comentario);
    }

    public boolean deleteElemento(Elemento elemento){
        return accesodbElemento.deleteElemento(elemento);
    }

    public List<Comentario> getComentarios(String nombreElem){
        return accesodbComentario.getComentarios(nombreElem);
    }

    public void addUsuario(Usuario usuario){
        accesodbUsuario.addUsuario(usuario);
    }

    public boolean deleteComentario(Comentario comentarioAResolver) {
        return accesodbComentario.deleteComentario(comentarioAResolver);
    }

    public Usuario getUsuario(String mail) {
        return accesodbUsuario.getUsuario(mail);
    }
     
}
