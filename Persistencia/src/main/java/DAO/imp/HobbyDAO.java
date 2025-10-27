/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.imp;

import DAO.HobbyJpaController;
import DAO.Repository.IHobbyDAO;
import DTO.Enum.TipoHobbies;
import Entity.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ferch
 */
public class HobbyDAO implements IHobbyDAO{

    private final HobbyJpaController jpaController;
    private final EntityManagerFactory emf;

    public HobbyDAO(EntityManagerFactory emf) {
        this.emf = emf;
        this.jpaController = new HobbyJpaController(emf);
    }
    // Metodos Específicos de IHobbyDAO
    @Override
    public Optional<Hobby> buscarPorTipo(TipoHobbies tipoHobby) throws SQLException {
       EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT h FROM Hobby h WHERE h.hobbie = :tipo";
            Query query = em.createQuery(jpql, Hobby.class);
            query.setParameter("tipo", tipoHobby);
            
            return Optional.ofNullable((Hobby) query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        } catch (Exception ex) {
            throw new SQLException("Error al buscar Hobby por tipo.", ex);
        } finally {
            if (em != null) em.close();
        }
    }

    // // Metodos de ICRUD
    @Override
    public Hobby create(Hobby entity) throws SQLException {
        try {
            jpaController.create(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al crear el Hobby.", e);
        }
    }

    @Override
    public Optional<Hobby> read(long id) throws SQLException {
        return Optional.ofNullable(jpaController.findHobby(id));
    }

    @Override
    public Hobby update(Hobby entity) throws SQLException {
      
        try {
            jpaController.edit(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al actualizar el Hobby.", e);
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        try {
            jpaController.destroy(id);
        } catch (Exception e) {
            throw new SQLException("Error al eliminar el Hobby.", e);
        }
    }

    @Override
    public List<Hobby> findEntities() throws SQLException {
        return jpaController.findHobbyEntities();
    }

    @Override
    public List<Hobby> findEntities(int maxResults, int firstResult) throws SQLException {
       return jpaController.findHobbyEntities(maxResults, firstResult);
    }
    
}
