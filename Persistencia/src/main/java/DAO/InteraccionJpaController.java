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
import Entity.Interaccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public class InteraccionJpaController implements Serializable {

    public InteraccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Interaccion interaccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante = interaccion.getEstudiante();
            if (estudiante != null) {
                estudiante = em.getReference(estudiante.getClass(), estudiante.getId());
                interaccion.setEstudiante(estudiante);
            }
            em.persist(interaccion);
            if (estudiante != null) {
                estudiante.getInteraccion().add(interaccion);
                estudiante = em.merge(estudiante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Interaccion interaccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interaccion persistentInteraccion = em.find(Interaccion.class, interaccion.getId());
            Estudiante estudianteOld = persistentInteraccion.getEstudiante();
            Estudiante estudianteNew = interaccion.getEstudiante();
            if (estudianteNew != null) {
                estudianteNew = em.getReference(estudianteNew.getClass(), estudianteNew.getId());
                interaccion.setEstudiante(estudianteNew);
            }
            interaccion = em.merge(interaccion);
            if (estudianteOld != null && !estudianteOld.equals(estudianteNew)) {
                estudianteOld.getInteraccion().remove(interaccion);
                estudianteOld = em.merge(estudianteOld);
            }
            if (estudianteNew != null && !estudianteNew.equals(estudianteOld)) {
                estudianteNew.getInteraccion().add(interaccion);
                estudianteNew = em.merge(estudianteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = interaccion.getId();
                if (findInteraccion(id) == null) {
                    throw new NonexistentEntityException("The interaccion with id " + id + " no longer exists.");
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
            Interaccion interaccion;
            try {
                interaccion = em.getReference(Interaccion.class, id);
                interaccion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The interaccion with id " + id + " no longer exists.", enfe);
            }
            Estudiante estudiante = interaccion.getEstudiante();
            if (estudiante != null) {
                estudiante.getInteraccion().remove(interaccion);
                estudiante = em.merge(estudiante);
            }
            em.remove(interaccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Interaccion> findInteraccionEntities() {
        return findInteraccionEntities(true, -1, -1);
    }

    public List<Interaccion> findInteraccionEntities(int maxResults, int firstResult) {
        return findInteraccionEntities(false, maxResults, firstResult);
    }

    private List<Interaccion> findInteraccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Interaccion.class));
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

    public Interaccion findInteraccion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Interaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getInteraccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Interaccion> rt = cq.from(Interaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
