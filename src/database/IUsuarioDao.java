package database;

import java.util.List;

import modelo.Usuario;

public interface IUsuarioDao {

    public List<Usuario> getUsuarios();
    public Usuario getUsuario(String mail);
    public void updateUsuario(Usuario usuario);
    public void deleteUsuario(String mail);
    public void addUsuario(Usuario a);
    
}
