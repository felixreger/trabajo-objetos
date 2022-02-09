package servicios.database.elemento;

import java.util.List;

import modelo.Elemento;

public interface IElementoDao {

    public List<Elemento> getElementos();
    public Elemento getElemento(String nombre);
    public void updateElemento(Elemento elem);
    public boolean deleteElemento(Elemento elem);
    public void addElemento(Elemento elem);
    
}
