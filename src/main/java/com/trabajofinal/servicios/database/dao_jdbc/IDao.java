package com.trabajofinal.servicios.database.dao_jdbc;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T, E>{
    
    List<T> getAll() throws Exception;
    T get(E id) throws Exception;
    void update(T elem) throws Exception;
    void delete(E id) throws Exception;
    void add(T elem) throws Exception;
    boolean exist(E id) throws Exception;

}