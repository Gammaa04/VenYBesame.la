/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.imp;

import DAO.EstudiantePreferenciaJpaController;
import InterfacesDAO.IEstudiantePreferenciaDAO;
import Entity.EstudiantePreferencia;
import Entity.Preferencia;
import JPAUtil.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ferch
 */
public class EstudiantePreferenciaDAO implements IEstudiantePreferenciaDAO{
    private final EstudiantePreferenciaJpaController jpaController;

    public EstudiantePreferenciaDAO(EntityManagerFactory emf) {
        this.jpaController = new EstudiantePreferenciaJpaController(emf);
    }
    // Metodos Específicos de IEstudiantePreferenciaDAO
    @Override
    public void eliminarPorEstudiante(Long idEstudiante) throws SQLException {
          EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // Elimina todas las preferencias asociadas a un estudiante
            String jpql = "DELETE FROM EstudiantePreferencia ep WHERE ep.estudiante.id = :idEstudiante";
            em.createQuery(jpql)
              .setParameter("idEstudiante", idEstudiante)
              .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new SQLException("Error al eliminar enlaces de preferencia por estudiante.", ex);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public List<Preferencia> buscarPreferenciasPorEstudiante(Long idEstudiante) throws SQLException {
      EntityManager em = JpaUtil.getEntityManager();
        try {
            // Consulta que une EstudiantePreferencia y Preferencia para obtener la lista de preferencias
            String jpql = "SELECT ep.preferencia FROM EstudiantePreferencia ep WHERE ep.estudiante.id = :idEstudiante";
            
            return em.createQuery(jpql, Preferencia.class)
                .setParameter("idEstudiante", idEstudiante)
                .getResultList();
        } catch (Exception ex) {
            throw new SQLException("Error al listar preferencias del estudiante.", ex);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void eliminarPorRelacion(Long idEstudiante, Long idPreferencia) throws SQLException {
       EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // Busca y elimina el enlace específico
            String jpql = "DELETE FROM EstudiantePreferencia ep WHERE ep.estudiante.id = :idEstudiante AND ep.preferencia.id = :idPreferencia";
            int count = em.createQuery(jpql)
                        .setParameter("idEstudiante", idEstudiante)
                        .setParameter("idPreferencia", idPreferencia)
                        .executeUpdate();
            em.getTransaction().commit();
            if (count == 0) {
                throw new SQLException("El enlace Estudiante-Preferencia no existe para eliminar.");
            }
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new SQLException("Error al eliminar el enlace específico de preferencia.", ex);
        } finally {
            if (em != null) em.close();
        }
    }
   // Metodos de ICRUD
    @Override
    public EstudiantePreferencia create(EstudiantePreferencia entity) throws SQLException {
      try {
            jpaController.create(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al crear el enlace EstudiantePreferencia.", e);
        }
    }

    @Override
    public Optional<EstudiantePreferencia> read(long id) throws SQLException {
        return Optional.ofNullable(jpaController.findEstudiantePreferencia(id));
    }

    @Override
    public EstudiantePreferencia update(EstudiantePreferencia entity) throws SQLException {
      try {
            jpaController.edit(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al actualizar el enlace EstudiantePreferencia.", e);
        }
    }

    @Override
    public void delete(long id) throws SQLException {
         try {
            jpaController.destroy(id);
        } catch (Exception e) {
            throw new SQLException("Error al eliminar el enlace EstudiantePreferencia.", e);
        }
    }

    @Override
    public List<EstudiantePreferencia> findEntities() throws SQLException {
         return jpaController.findEstudiantePreferenciaEntities();
    }

    @Override
    public List<EstudiantePreferencia> findEntities(int maxResults, int firstResult) throws SQLException {
       return jpaController.findEstudiantePreferenciaEntities(maxResults, firstResult);
    }
    
}
