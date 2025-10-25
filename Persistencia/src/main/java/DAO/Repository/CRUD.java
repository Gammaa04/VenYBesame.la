/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Repository;

import Entity.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public class CRUD<T> implements ICRUD {

    private EntityManager em;
    private Type type;

    public CRUD(EntityManager em) {
        this.em = em;
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public Object create(Object entity) throws SQLException {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();

            T find = em.find((Class<T>) type, entity);
            return find;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new SQLException(e);
        } finally {
            em.close();
        }

    }

    @Override
    public Object read(long id) throws SQLException {
        try {
            T find = em.find((Class<T>) type, id);
            if (find == null) {
                throw new Exception("Error: no fue encontrado la entidad con el id " + id);
            }
            return find;

        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public Object update(Object entity) throws SQLException {
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();

            T find = em.find((Class<T>) type, entity);
            return find;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new SQLException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        try {
            T find = em.find((Class<T>) type, id);
            if (find == null) {
                throw new Exception("Error: no fue encontrado la entidad con el id " + id);
            }
            em.remove(find);

        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object> findEntities() {
        return findEstudianteEntities(true, -1, -1);
    }

    @Override
    public List<Object> findEntities(int maxResults, int firstResult) {
        return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Object> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}
