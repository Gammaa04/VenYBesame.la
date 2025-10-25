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
import Entity.EstudianteHobby;
import Entity.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public class EstudianteHobbyJpaController implements Serializable {

    public EstudianteHobbyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstudianteHobby estudianteHobby) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante = estudianteHobby.getEstudiante();
            if (estudiante != null) {
                estudiante = em.getReference(estudiante.getClass(), estudiante.getId());
                estudianteHobby.setEstudiante(estudiante);
            }
            Hobby hobby = estudianteHobby.getHobby();
            if (hobby != null) {
                hobby = em.getReference(hobby.getClass(), hobby.getId());
                estudianteHobby.setHobby(hobby);
            }
            em.persist(estudianteHobby);
            if (estudiante != null) {
                estudiante.getHobbys().add(estudianteHobby);
                estudiante = em.merge(estudiante);
            }
            if (hobby != null) {
                hobby.getEstudiante().add(estudianteHobby);
                hobby = em.merge(hobby);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstudianteHobby estudianteHobby) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstudianteHobby persistentEstudianteHobby = em.find(EstudianteHobby.class, estudianteHobby.getId());
            Estudiante estudianteOld = persistentEstudianteHobby.getEstudiante();
            Estudiante estudianteNew = estudianteHobby.getEstudiante();
            Hobby hobbyOld = persistentEstudianteHobby.getHobby();
            Hobby hobbyNew = estudianteHobby.getHobby();
            if (estudianteNew != null) {
                estudianteNew = em.getReference(estudianteNew.getClass(), estudianteNew.getId());
                estudianteHobby.setEstudiante(estudianteNew);
            }
            if (hobbyNew != null) {
                hobbyNew = em.getReference(hobbyNew.getClass(), hobbyNew.getId());
                estudianteHobby.setHobby(hobbyNew);
            }
            estudianteHobby = em.merge(estudianteHobby);
            if (estudianteOld != null && !estudianteOld.equals(estudianteNew)) {
                estudianteOld.getHobbys().remove(estudianteHobby);
                estudianteOld = em.merge(estudianteOld);
            }
            if (estudianteNew != null && !estudianteNew.equals(estudianteOld)) {
                estudianteNew.getHobbys().add(estudianteHobby);
                estudianteNew = em.merge(estudianteNew);
            }
            if (hobbyOld != null && !hobbyOld.equals(hobbyNew)) {
                hobbyOld.getEstudiante().remove(estudianteHobby);
                hobbyOld = em.merge(hobbyOld);
            }
            if (hobbyNew != null && !hobbyNew.equals(hobbyOld)) {
                hobbyNew.getEstudiante().add(estudianteHobby);
                hobbyNew = em.merge(hobbyNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = estudianteHobby.getId();
                if (findEstudianteHobby(id) == null) {
                    throw new NonexistentEntityException("The estudianteHobby with id " + id + " no longer exists.");
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
            EstudianteHobby estudianteHobby;
            try {
                estudianteHobby = em.getReference(EstudianteHobby.class, id);
                estudianteHobby.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudianteHobby with id " + id + " no longer exists.", enfe);
            }
            Estudiante estudiante = estudianteHobby.getEstudiante();
            if (estudiante != null) {
                estudiante.getHobbys().remove(estudianteHobby);
                estudiante = em.merge(estudiante);
            }
            Hobby hobby = estudianteHobby.getHobby();
            if (hobby != null) {
                hobby.getEstudiante().remove(estudianteHobby);
                hobby = em.merge(hobby);
            }
            em.remove(estudianteHobby);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstudianteHobby> findEstudianteHobbyEntities() {
        return findEstudianteHobbyEntities(true, -1, -1);
    }

    public List<EstudianteHobby> findEstudianteHobbyEntities(int maxResults, int firstResult) {
        return findEstudianteHobbyEntities(false, maxResults, firstResult);
    }

    private List<EstudianteHobby> findEstudianteHobbyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstudianteHobby.class));
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

    public EstudianteHobby findEstudianteHobby(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstudianteHobby.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteHobbyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstudianteHobby> rt = cq.from(EstudianteHobby.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
