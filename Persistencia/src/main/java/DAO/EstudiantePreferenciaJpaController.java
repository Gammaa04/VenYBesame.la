/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Entity.Estudiante;
import Entity.EstudiantePreferencia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public class EstudiantePreferenciaJpaController implements Serializable {

    public EstudiantePreferenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstudiantePreferencia estudiantePreferencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante = estudiantePreferencia.getEstudiante();
            if (estudiante != null) {
                estudiante = em.getReference(estudiante.getClass(), estudiante.getId());
                estudiantePreferencia.setEstudiante(estudiante);
            }
            em.persist(estudiantePreferencia);
            if (estudiante != null) {
                estudiante.getPreferencias().add(estudiantePreferencia);
                estudiante = em.merge(estudiante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstudiantePreferencia estudiantePreferencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstudiantePreferencia persistentEstudiantePreferencia = em.find(EstudiantePreferencia.class, estudiantePreferencia.getId());
            Estudiante estudianteOld = persistentEstudiantePreferencia.getEstudiante();
            Estudiante estudianteNew = estudiantePreferencia.getEstudiante();
            if (estudianteNew != null) {
                estudianteNew = em.getReference(estudianteNew.getClass(), estudianteNew.getId());
                estudiantePreferencia.setEstudiante(estudianteNew);
            }
            estudiantePreferencia = em.merge(estudiantePreferencia);
            if (estudianteOld != null && !estudianteOld.equals(estudianteNew)) {
                estudianteOld.getPreferencias().remove(estudiantePreferencia);
                estudianteOld = em.merge(estudianteOld);
            }
            if (estudianteNew != null && !estudianteNew.equals(estudianteOld)) {
                estudianteNew.getPreferencias().add(estudiantePreferencia);
                estudianteNew = em.merge(estudianteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = estudiantePreferencia.getId();
                if (findEstudiantePreferencia(id) == null) {
                    throw new NonexistentEntityException("The estudiantePreferencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstudiantePreferencia estudiantePreferencia;
            try {
                estudiantePreferencia = em.getReference(EstudiantePreferencia.class, id);
                estudiantePreferencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiantePreferencia with id " + id + " no longer exists.", enfe);
            }
            Estudiante estudiante = estudiantePreferencia.getEstudiante();
            if (estudiante != null) {
                estudiante.getPreferencias().remove(estudiantePreferencia);
                estudiante = em.merge(estudiante);
            }
            em.remove(estudiantePreferencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstudiantePreferencia> findEstudiantePreferenciaEntities() {
        return findEstudiantePreferenciaEntities(true, -1, -1);
    }

    public List<EstudiantePreferencia> findEstudiantePreferenciaEntities(int maxResults, int firstResult) {
        return findEstudiantePreferenciaEntities(false, maxResults, firstResult);
    }

    private List<EstudiantePreferencia> findEstudiantePreferenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstudiantePreferencia.class));
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

    public EstudiantePreferencia findEstudiantePreferencia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstudiantePreferencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudiantePreferenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstudiantePreferencia> rt = cq.from(EstudiantePreferencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
