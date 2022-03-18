package com.trabajofinal.servicios.database.dao_jdbc;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T, E>{
    
    List<T> getAll() throws SQLException;
    T get(E id) throws SQLException;
    void update(T elem) throws SQLException;
    void delete(E id) throws SQLException;
    void add(T elem) throws SQLException;
    boolean exist(E id) throws SQLException;

}