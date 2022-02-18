package main.modelo;

import java.util.ArrayList;
import java.util.List;

import main.servicios.Servicios;

public class Administrador extends Usuario {

    private Servicios servicio = Servicios.getInstance();

    public Administrador(String mail, String nombre, int puntaje){
        super(mail, nombre, puntaje);
    }
    
    public void eliminarElemento(Elemento elemento){
        if (!servicio.deleteElemento(elemento)){
            System.out.println("Fallo el borrado de " + elemento.toString());
            return;
        }
        System.out.println("Elemento " + elemento.toString() +", eliminado correctamente");
    }

    public List<String> verComentarios(Elemento elemento){
        List<String> res = new ArrayList<String>();
        List<Comentario> comentarios = servicio.getComentarios(elemento.getNombre());

        for (Comentario comentario : comentarios) {
            res.add(comentario.toString());
        }
        
        return res;
    }

    public void resolverComentario(Comentario comentarioAResolver){
        if (!servicio.deleteComentario(comentarioAResolver.getId())){
            System.out.println("Fallo el borrado de " + comentarioAResolver.toString());
            return;
        }
        System.out.println("Comentario " + comentarioAResolver + " resuelto correctamente");
    }

    public void agregarUsuario(Usuario usuario){
        if (usuario == null)
            return;

        if (!servicio.getUsuario(usuario.getMail()).isValid()){
            System.out.println("El usuario " + usuario.toString() + " ya existe");
            return;
        }

        servicio.addUsuario(usuario);
            
    }
}