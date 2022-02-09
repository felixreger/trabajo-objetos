package servicios.database.usuario;

import java.util.List;

import modelo.Usuario;

public interface IUsuarioDao {

    public List<Usuario> getUsuarios();
    public Usuario getUsuario(String mail);
    public void updateUsuario(Usuario usuario);
    public boolean deleteUsuario(String mail);
    public void addUsuario(Usuario a);
    
}
