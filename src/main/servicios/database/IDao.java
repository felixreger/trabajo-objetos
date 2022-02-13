package main.servicios.database;

import java.util.List;

public interface IDao<T, E>{
    
    List<T> getAll();
    T get(E id);
    void update(T elem);
    boolean delete(E id);
    void add(T elem);
     
}