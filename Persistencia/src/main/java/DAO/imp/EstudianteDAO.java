
package DAO.imp;

import DAO.EstudianteJpaController;
import DAO.Repository.IEstudianteDAO;
import Entity.Estudiante;
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
public class EstudianteDAO implements IEstudianteDAO{
    private final EstudianteJpaController jpaController;
    private final EntityManagerFactory emf;

    public EstudianteDAO(EntityManagerFactory emf) {
        this.emf = emf;
        this.jpaController = new EstudianteJpaController(emf);
    }
    
    // Implementacion de ICRUD 
    
    @Override
    public Estudiante create(Estudiante entity) throws SQLException {
        try {
            jpaController.create(entity); // Llama al metodo create del JpaController (void)
            // Se asume que la entidad ahora tiene el ID asignado
            return entity; 
        } catch (Exception e) {
            throw new SQLException("Error al crear el estudiante.", e);
        }
    }

    @Override
    public Estudiante update(Estudiante entity) throws SQLException {
        try {
            jpaController.edit(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al actualizar el estudiante.", e);
        }
    }

  
    @Override
    public List<Estudiante> findEntities() throws SQLException {
        // Llama al método sin parametros del JpaController
        return jpaController.findEstudianteEntities(); 
    }
    
    @Override
    public List<Estudiante> findEntities(int maxResults, int firstResult) throws SQLException {
        // Llama al metodo con parametros del JpaController
        return jpaController.findEstudianteEntities(maxResults, firstResult);
    }

    // Metodos Específicos de IEstudianteDAO (Consultas Complejas)

    @Override
    public Optional<Estudiante> buscarPorCorreo(String correo) throws SQLException {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM Estudiante e WHERE e.correo = :correo";
            Query query = em.createQuery(jpql, Estudiante.class);
            query.setParameter("correo", correo);
            
            return Optional.ofNullable((Estudiante) query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        } catch (Exception ex) {
            throw new SQLException("Error al buscar estudiante por correo.", ex);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public Optional<Estudiante> buscarPorCredenciales(String correo, String contrasena) throws SQLException {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM Estudiante e WHERE e.correo = :correo AND e.contraseña = :contrasena";
            Query query = em.createQuery(jpql, Estudiante.class);
            query.setParameter("correo", correo);
            query.setParameter("contrasena", contrasena);
            
            return Optional.ofNullable((Estudiante) query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        } catch (Exception ex) {
            throw new SQLException("Error al buscar estudiante por credenciales.", ex);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public List<Estudiante> buscarCandidatos(Long idEstudianteActual, int limit) throws SQLException {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM Estudiante e WHERE e.id != :idActual AND e.id NOT IN " +
                          "(SELECT i.estudianteDestino.id FROM Interaccion i WHERE i.estudiante.id = :idActual)";
            
            return em.createQuery(jpql, Estudiante.class)
                .setParameter("idActual", idEstudianteActual)
                .setMaxResults(limit)
                .getResultList();
        } catch (Exception ex) {
            throw new SQLException("Error al buscar candidatos.", ex);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public Optional<Estudiante> read(long id) throws SQLException {
        // Adaptacion del metodo T read(long id) a Optional<T>
        return Optional.ofNullable(jpaController.findEstudiante(id));
    }

    @Override
    public void delete(long id) throws SQLException {
       try {
            jpaController.destroy(id);
        } catch (Exception e) {
            throw new SQLException("Error al eliminar el estudiante.", e);
        } 
    }
}
