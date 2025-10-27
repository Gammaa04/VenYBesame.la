/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.imp;

import DAO.EstudianteHobbyJpaController;
import DAO.Repository.IEstudianteDAO;
import DAO.Repository.IEstudianteHobbyDAO;
import Entity.Estudiante;
import Entity.EstudianteHobby;
import Entity.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ferch
 */
public class EstudianteHobbyDAO implements IEstudianteHobbyDAO{

    private final EstudianteHobbyJpaController jpaController;
    private final EntityManagerFactory emf;

    public EstudianteHobbyDAO(EntityManagerFactory emf) {
        this.emf = emf;
        this.jpaController = new EstudianteHobbyJpaController(emf);
    } 
    
    // Metodos Específicos de IEstudianteHobbyDAO
    @Override
    public void eliminarPorEstudiante(Long idEstudiante) throws SQLException {
       EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // Elimina todos los hobbies asociados a un estudiante
            String jpql = "DELETE FROM EstudianteHobby eh WHERE eh.estudiante.id = :idEstudiante";
            em.createQuery(jpql)
              .setParameter("idEstudiante", idEstudiante)
              .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new SQLException("Error al eliminar enlaces de hobby por estudiante.", ex);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public List<Hobby> buscarHobbiesPorEstudiante(Long idEstudiante) throws SQLException {
     EntityManager em = emf.createEntityManager();
        try {
            // Consulta que une EstudianteHobby y Hobby para obtener la lista de hobbies
            String jpql = "SELECT eh.hobby FROM EstudianteHobby eh WHERE eh.estudiante.id = :idEstudiante";
            
            return em.createQuery(jpql, Hobby.class)
                .setParameter("idEstudiante", idEstudiante)
                .getResultList();
        } catch (Exception ex) {
            throw new SQLException("Error al listar hobbies del estudiante.", ex);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void eliminarPorRelacion(Long idEstudiante, Long idHobby) throws SQLException {
      EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // Busca y elimina el enlace específico
            String jpql = "DELETE FROM EstudianteHobby eh WHERE eh.estudiante.id = :idEstudiante AND eh.hobby.id = :idHobby";
            int count = em.createQuery(jpql)
                        .setParameter("idEstudiante", idEstudiante)
                        .setParameter("idHobby", idHobby)
                        .executeUpdate();
            em.getTransaction().commit();
             if (count == 0) {
                throw new SQLException("El enlace Estudiante-Hobby no existe para eliminar.");
            }
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new SQLException("Error al eliminar el enlace específico de hobby.", ex);
        } finally {
            if (em != null) em.close();
        }
    }

    
    
      // Metodos de ICRUD
    @Override
    public EstudianteHobby create(EstudianteHobby entity) throws SQLException {
      try {
            jpaController.create(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al crear el enlace EstudianteHobby.", e);
        }
    }

    @Override
    public Optional<EstudianteHobby> read(long id) throws SQLException {
       return Optional.ofNullable(jpaController.findEstudianteHobby(id));
    }

    @Override
    public EstudianteHobby update(EstudianteHobby entity) throws SQLException {
       try {
            jpaController.edit(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al actualizar el enlace EstudianteHobby.", e);
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        try {
            jpaController.destroy(id);
        } catch (Exception e) {
            throw new SQLException("Error al eliminar el enlace EstudianteHobby.", e);
        }
    }

    @Override
    public List<EstudianteHobby> findEntities() throws SQLException {
           return jpaController.findEstudianteHobbyEntities();
    }

    @Override
    public List<EstudianteHobby> findEntities(int maxResults, int firstResult) throws SQLException {
          return jpaController.findEstudianteHobbyEntities(maxResults, firstResult);
    }

    
    
}
