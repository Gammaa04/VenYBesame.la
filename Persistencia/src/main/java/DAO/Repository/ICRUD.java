/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.Repository;

import Entity.Estudiante;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public interface ICRUD<T> {

    public T create(T entity) throws SQLException;

    public T read(long id) throws SQLException;

    public T update(T entity) throws SQLException;

    public void delete(long id) throws SQLException;

    List<T> findEntities() throws SQLException;

    List<T> findEntities(int maxResults, int firstResult) throws SQLException;

}
