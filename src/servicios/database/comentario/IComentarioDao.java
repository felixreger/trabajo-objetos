package servicios.database.comentario;

import java.util.List;

import modelo.Comentario;

public interface IComentarioDao {

    public List<Comentario> getComentarios(String nombre_elem);
    public Comentario getComentario(String nombre);
    public void updateComentario(Comentario opinion);
    public boolean deleteComentario(Comentario opinion);
    public void addComentario(Comentario opinion);
    
}