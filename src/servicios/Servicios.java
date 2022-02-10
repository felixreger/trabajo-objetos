package servicios;

import java.util.List;

import modelo.Comentario;
import modelo.Elemento;
import modelo.Usuario;
import servicios.database.dao_impl.ComentarioDao;
import servicios.database.dao_impl.ElementoDao;
import servicios.database.dao_impl.UsuarioDao;

public class Servicios {

    private ElementoDao accesodbElemento = ElementoDao.getInstance();
    private ComentarioDao accesodbComentario = ComentarioDao.getInstance();
    private UsuarioDao accesodbUsuario = UsuarioDao.getInstance();

    private static Servicios servicio = null;

    private Servicios(){}

    public static Servicios getInstance(){
		if (servicio == null)
            servicio = new Servicios();
		return servicio;
	}

    public void addComentario(Comentario comentario){
        accesodbComentario.add(comentario);
    }

    public boolean deleteElemento(Elemento elemento){
        return accesodbElemento.delete(elemento.getNombre());
    }

    public List<Comentario> getComentarios(String nombreElem){
        return accesodbComentario.getComentarios(nombreElem);
    }

    public void addUsuario(Usuario usuario){
        accesodbUsuario.add(usuario);
    }

    public boolean deleteComentario(Comentario comentarioAResolver) {
        return accesodbComentario.delete(comentarioAResolver.getId());
    }

    public Usuario getUsuario(String mail) {
        return accesodbUsuario.get(mail);
    }

    public List<Usuario> getUsuarios() {
        return accesodbUsuario.getAll();
    }

}
