/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Entity.EstudianteHobby;
import Entity.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public class HobbyJpaController implements Serializable {

    public HobbyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hobby hobby) {
        if (hobby.getEstudiante() == null) {
            hobby.setEstudiante(new HashSet<EstudianteHobby>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<EstudianteHobby> attachedEstudiante = new HashSet<EstudianteHobby>();
            for (EstudianteHobby estudianteEstudianteHobbyToAttach : hobby.getEstudiante()) {
                estudianteEstudianteHobbyToAttach = em.getReference(estudianteEstudianteHobbyToAttach.getClass(), estudianteEstudianteHobbyToAttach.getId());
                attachedEstudiante.add(estudianteEstudianteHobbyToAttach);
            }
            hobby.setEstudiante(attachedEstudiante);
            em.persist(hobby);
            for (EstudianteHobby estudianteEstudianteHobby : hobby.getEstudiante()) {
                Hobby oldHobbyOfEstudianteEstudianteHobby = estudianteEstudianteHobby.getHobby();
                estudianteEstudianteHobby.setHobby(hobby);
                estudianteEstudianteHobby = em.merge(estudianteEstudianteHobby);
                if (oldHobbyOfEstudianteEstudianteHobby != null) {
                    oldHobbyOfEstudianteEstudianteHobby.getEstudiante().remove(estudianteEstudianteHobby);
                    oldHobbyOfEstudianteEstudianteHobby = em.merge(oldHobbyOfEstudianteEstudianteHobby);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hobby hobby) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hobby persistentHobby = em.find(Hobby.class, hobby.getId());
            Set<EstudianteHobby> estudianteOld = persistentHobby.getEstudiante();
            Set<EstudianteHobby> estudianteNew = hobby.getEstudiante();
            List<String> illegalOrphanMessages = null;
            for (EstudianteHobby estudianteOldEstudianteHobby : estudianteOld) {
                if (!estudianteNew.contains(estudianteOldEstudianteHobby)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EstudianteHobby " + estudianteOldEstudianteHobby + " since its hobby field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<EstudianteHobby> attachedEstudianteNew = new HashSet<EstudianteHobby>();
            for (EstudianteHobby estudianteNewEstudianteHobbyToAttach : estudianteNew) {
                estudianteNewEstudianteHobbyToAttach = em.getReference(estudianteNewEstudianteHobbyToAttach.getClass(), estudianteNewEstudianteHobbyToAttach.getId());
                attachedEstudianteNew.add(estudianteNewEstudianteHobbyToAttach);
            }
            estudianteNew = attachedEstudianteNew;
            hobby.setEstudiante(estudianteNew);
            hobby = em.merge(hobby);
            for (EstudianteHobby estudianteNewEstudianteHobby : estudianteNew) {
                if (!estudianteOld.contains(estudianteNewEstudianteHobby)) {
                    Hobby oldHobbyOfEstudianteNewEstudianteHobby = estudianteNewEstudianteHobby.getHobby();
                    estudianteNewEstudianteHobby.setHobby(hobby);
                    estudianteNewEstudianteHobby = em.merge(estudianteNewEstudianteHobby);
                    if (oldHobbyOfEstudianteNewEstudianteHobby != null && !oldHobbyOfEstudianteNewEstudianteHobby.equals(hobby)) {
                        oldHobbyOfEstudianteNewEstudianteHobby.getEstudiante().remove(estudianteNewEstudianteHobby);
                        oldHobbyOfEstudianteNewEstudianteHobby = em.merge(oldHobbyOfEstudianteNewEstudianteHobby);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = hobby.getId();
                if (findHobby(id) == null) {
                    throw new NonexistentEntityException("The hobby with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hobby hobby;
            try {
                hobby = em.getReference(Hobby.class, id);
                hobby.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hobby with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<EstudianteHobby> estudianteOrphanCheck = hobby.getEstudiante();
            for (EstudianteHobby estudianteOrphanCheckEstudianteHobby : estudianteOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hobby (" + hobby + ") cannot be destroyed since the EstudianteHobby " + estudianteOrphanCheckEstudianteHobby + " in its estudiante field has a non-nullable hobby field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(hobby);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Hobby> findHobbyEntities() {
        return findHobbyEntities(true, -1, -1);
    }

    public List<Hobby> findHobbyEntities(int maxResults, int firstResult) {
        return findHobbyEntities(false, maxResults, firstResult);
    }

    private List<Hobby> findHobbyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hobby.class));
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

    public Hobby findHobby(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hobby.class, id);
        } finally {
            em.close();
        }
    }

    public int getHobbyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hobby> rt = cq.from(Hobby.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
