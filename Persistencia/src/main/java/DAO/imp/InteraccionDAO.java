
package DAO.imp;

import DAO.InteraccionJpaController;
import DAO.Repository.IInteraccionDAO;
import Entity.Interaccion;
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
public class InteraccionDAO implements IInteraccionDAO{

    private final InteraccionJpaController jpaController;
    private final EntityManagerFactory emf;

    public InteraccionDAO(EntityManagerFactory emf) {
        this.emf = emf;
        this.jpaController = new InteraccionJpaController(emf);
    }
    
    // Metodos Específicos de IInteraccionDAO (Consultas Complejas)
    @Override
    public Optional<Interaccion> buscarInteraccionReciproca(Long idEstudianteA, Long idEstudianteB) throws SQLException {
       EntityManager em = emf.createEntityManager();
        try {
            // Busca si B (idEstudianteB) le dio LIKE a A (idEstudianteA)
            String jpql = "SELECT i FROM Interaccion i WHERE i.estudiante.id = :idB AND i.estudianteDestino.id = :idA AND i.reaccion = 'LIKE'";
            
            Query query = em.createQuery(jpql, Interaccion.class);
            query.setParameter("idA", idEstudianteA);
            query.setParameter("idB", idEstudianteB);
            
            return Optional.ofNullable((Interaccion) query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        } catch (Exception ex) {
            throw new SQLException("Error al buscar Interaccion recíproca.", ex);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public boolean yaExisteInteraccion(Long idEstudianteOrigen, Long idEstudianteDestino) throws SQLException {
       EntityManager em = emf.createEntityManager();
        try {
            // Verifica si el Origen ya interactuo con el Destino
            String jpql = "SELECT COUNT(i) FROM Interaccion i WHERE i.estudiante.id = :idOrigen AND i.estudianteDestino.id = :idDestino";
            
            Query query = em.createQuery(jpql);
            query.setParameter("idOrigen", idEstudianteOrigen);
            query.setParameter("idDestino", idEstudianteDestino);
            
            return (Long) query.getSingleResult() > 0;
        } catch (Exception ex) {
            throw new SQLException("Error al verificar existencia de interacion.", ex);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void eliminarInteraccionesPorEstudiante(Long idEstudiante) throws SQLException {
        EntityManager em = emf.createEntityManager();
        try {
            // Transaccion necesaria para DELETE
            em.getTransaction().begin();
            // Elimina todas las interacciones donde el estudiante es origen o destino
            String jpql = "DELETE FROM Interaccion i WHERE i.estudiante.id = :id OR i.estudianteDestino.id = :id";
            em.createQuery(jpql)
              .setParameter("id", idEstudiante)
              .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new SQLException("Error al eliminar interacciones del estudiante.", ex);
        } finally {
            if (em != null) em.close();
        }
     }

    // Implementacion de ICRU
    @Override
    public Interaccion create(Interaccion entity) throws SQLException {

        try {
            jpaController.create(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al crear la Interaccion.", e);
        }
    }

    @Override
    public Optional<Interaccion> read(long id) throws SQLException {
        return Optional.ofNullable(jpaController.findInteraccion(id));
    }

    @Override
    public Interaccion update(Interaccion entity) throws SQLException {
         try {
            jpaController.edit(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al actualizar la Interaccion.", e);
        }
    }

    @Override
    public void delete(long id) throws SQLException {
         try {
            jpaController.destroy(id);
        } catch (Exception e) {
            throw new SQLException("Error al eliminar la Interaccion.", e);
        }
     }

    @Override
    public List<Interaccion> findEntities() throws SQLException {
      return jpaController.findInteraccionEntities();
    }

    @Override
    public List<Interaccion> findEntities(int maxResults, int firstResult) throws SQLException {
      return jpaController.findInteraccionEntities(maxResults, firstResult);
    }
    
}
