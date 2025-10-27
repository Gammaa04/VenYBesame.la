/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.Repository.IChatDAO;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Entity.Interaccion;
import java.util.HashSet;
import java.util.Set;
import Entity.Chat;
import Entity.Estudiante;
import Entity.EstudiantePreferencia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
    public class EstudianteJpaController  {

    public EstudianteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante estudiante) {
        if (estudiante.getInteraccion() == null) {
            estudiante.setInteraccion(new HashSet<Interaccion>());
        }
        if (estudiante.getMatchs() == null) {
            estudiante.setMatchs(new HashSet<Chat>());
        }
        if (estudiante.getPreferencias() == null) {
            estudiante.setPreferencias(new HashSet<EstudiantePreferencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Interaccion> attachedInteraccion = new HashSet<Interaccion>();
            for (Interaccion interaccionInteraccionToAttach : estudiante.getInteraccion()) {
                interaccionInteraccionToAttach = em.getReference(interaccionInteraccionToAttach.getClass(), interaccionInteraccionToAttach.getId());
                attachedInteraccion.add(interaccionInteraccionToAttach);
            }
            estudiante.setInteraccion(attachedInteraccion);
            Set<Chat> attachedMatchs = new HashSet<Chat>();
            for (Chat matchsChatToAttach : estudiante.getMatchs()) {
                matchsChatToAttach = em.getReference(matchsChatToAttach.getClass(), matchsChatToAttach.getId());
                attachedMatchs.add(matchsChatToAttach);
            }
            estudiante.setMatchs(attachedMatchs);
            Set<EstudiantePreferencia> attachedPreferencias = new HashSet<EstudiantePreferencia>();
            for (EstudiantePreferencia preferenciasEstudiantePreferenciaToAttach : estudiante.getPreferencias()) {
                preferenciasEstudiantePreferenciaToAttach = em.getReference(preferenciasEstudiantePreferenciaToAttach.getClass(), preferenciasEstudiantePreferenciaToAttach.getId());
                attachedPreferencias.add(preferenciasEstudiantePreferenciaToAttach);
            }
            estudiante.setPreferencias(attachedPreferencias);
            em.persist(estudiante);
            for (Interaccion interaccionInteraccion : estudiante.getInteraccion()) {
                Estudiante oldEstudianteOfInteraccionInteraccion = interaccionInteraccion.getEstudiante();
                interaccionInteraccion.setEstudiante(estudiante);
                interaccionInteraccion = em.merge(interaccionInteraccion);
                if (oldEstudianteOfInteraccionInteraccion != null) {
                    oldEstudianteOfInteraccionInteraccion.getInteraccion().remove(interaccionInteraccion);
                    oldEstudianteOfInteraccionInteraccion = em.merge(oldEstudianteOfInteraccionInteraccion);
                }
            }
            for (Chat matchsChat : estudiante.getMatchs()) {
                Estudiante oldE1OfMatchsChat = matchsChat.getE1();
                matchsChat.setE1(estudiante);
                matchsChat = em.merge(matchsChat);
                if (oldE1OfMatchsChat != null) {
                    oldE1OfMatchsChat.getMatchs().remove(matchsChat);
                    oldE1OfMatchsChat = em.merge(oldE1OfMatchsChat);
                }
            }
            for (EstudiantePreferencia preferenciasEstudiantePreferencia : estudiante.getPreferencias()) {
                Estudiante oldEstudianteOfPreferenciasEstudiantePreferencia = preferenciasEstudiantePreferencia.getEstudiante();
                preferenciasEstudiantePreferencia.setEstudiante(estudiante);
                preferenciasEstudiantePreferencia = em.merge(preferenciasEstudiantePreferencia);
                if (oldEstudianteOfPreferenciasEstudiantePreferencia != null) {
                    oldEstudianteOfPreferenciasEstudiantePreferencia.getPreferencias().remove(preferenciasEstudiantePreferencia);
                    oldEstudianteOfPreferenciasEstudiantePreferencia = em.merge(oldEstudianteOfPreferenciasEstudiantePreferencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiante estudiante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante persistentEstudiante = em.find(Estudiante.class, estudiante.getId());
            Set<Interaccion> interaccionOld = persistentEstudiante.getInteraccion();
            Set<Interaccion> interaccionNew = estudiante.getInteraccion();
            Set<Chat> matchsOld = persistentEstudiante.getMatchs();
            Set<Chat> matchsNew = estudiante.getMatchs();
            Set<EstudiantePreferencia> preferenciasOld = persistentEstudiante.getPreferencias();
            Set<EstudiantePreferencia> preferenciasNew = estudiante.getPreferencias();
            List<String> illegalOrphanMessages = null;
            for (Interaccion interaccionOldInteraccion : interaccionOld) {
                if (!interaccionNew.contains(interaccionOldInteraccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Interaccion " + interaccionOldInteraccion + " since its estudiante field is not nullable.");
                }
            }
            for (Chat matchsOldChat : matchsOld) {
                if (!matchsNew.contains(matchsOldChat)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Chat " + matchsOldChat + " since its e1 field is not nullable.");
                }
            }
            for (EstudiantePreferencia preferenciasOldEstudiantePreferencia : preferenciasOld) {
                if (!preferenciasNew.contains(preferenciasOldEstudiantePreferencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EstudiantePreferencia " + preferenciasOldEstudiantePreferencia + " since its estudiante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<Interaccion> attachedInteraccionNew = new HashSet<Interaccion>();
            for (Interaccion interaccionNewInteraccionToAttach : interaccionNew) {
                interaccionNewInteraccionToAttach = em.getReference(interaccionNewInteraccionToAttach.getClass(), interaccionNewInteraccionToAttach.getId());
                attachedInteraccionNew.add(interaccionNewInteraccionToAttach);
            }
            interaccionNew = attachedInteraccionNew;
            estudiante.setInteraccion(interaccionNew);
            Set<Chat> attachedMatchsNew = new HashSet<Chat>();
            for (Chat matchsNewChatToAttach : matchsNew) {
                matchsNewChatToAttach = em.getReference(matchsNewChatToAttach.getClass(), matchsNewChatToAttach.getId());
                attachedMatchsNew.add(matchsNewChatToAttach);
            }
            matchsNew = attachedMatchsNew;
            estudiante.setMatchs(matchsNew);
            Set<EstudiantePreferencia> attachedPreferenciasNew = new HashSet<EstudiantePreferencia>();
            for (EstudiantePreferencia preferenciasNewEstudiantePreferenciaToAttach : preferenciasNew) {
                preferenciasNewEstudiantePreferenciaToAttach = em.getReference(preferenciasNewEstudiantePreferenciaToAttach.getClass(), preferenciasNewEstudiantePreferenciaToAttach.getId());
                attachedPreferenciasNew.add(preferenciasNewEstudiantePreferenciaToAttach);
            }
            preferenciasNew = attachedPreferenciasNew;
            estudiante.setPreferencias(preferenciasNew);
            estudiante = em.merge(estudiante);
            for (Interaccion interaccionNewInteraccion : interaccionNew) {
                if (!interaccionOld.contains(interaccionNewInteraccion)) {
                    Estudiante oldEstudianteOfInteraccionNewInteraccion = interaccionNewInteraccion.getEstudiante();
                    interaccionNewInteraccion.setEstudiante(estudiante);
                    interaccionNewInteraccion = em.merge(interaccionNewInteraccion);
                    if (oldEstudianteOfInteraccionNewInteraccion != null && !oldEstudianteOfInteraccionNewInteraccion.equals(estudiante)) {
                        oldEstudianteOfInteraccionNewInteraccion.getInteraccion().remove(interaccionNewInteraccion);
                        oldEstudianteOfInteraccionNewInteraccion = em.merge(oldEstudianteOfInteraccionNewInteraccion);
                    }
                }
            }
            for (Chat matchsNewChat : matchsNew) {
                if (!matchsOld.contains(matchsNewChat)) {
                    Estudiante oldE1OfMatchsNewChat = matchsNewChat.getE1();
                    matchsNewChat.setE1(estudiante);
                    matchsNewChat = em.merge(matchsNewChat);
                    if (oldE1OfMatchsNewChat != null && !oldE1OfMatchsNewChat.equals(estudiante)) {
                        oldE1OfMatchsNewChat.getMatchs().remove(matchsNewChat);
                        oldE1OfMatchsNewChat = em.merge(oldE1OfMatchsNewChat);
                    }
                }
            }
            for (EstudiantePreferencia preferenciasNewEstudiantePreferencia : preferenciasNew) {
                if (!preferenciasOld.contains(preferenciasNewEstudiantePreferencia)) {
                    Estudiante oldEstudianteOfPreferenciasNewEstudiantePreferencia = preferenciasNewEstudiantePreferencia.getEstudiante();
                    preferenciasNewEstudiantePreferencia.setEstudiante(estudiante);
                    preferenciasNewEstudiantePreferencia = em.merge(preferenciasNewEstudiantePreferencia);
                    if (oldEstudianteOfPreferenciasNewEstudiantePreferencia != null && !oldEstudianteOfPreferenciasNewEstudiantePreferencia.equals(estudiante)) {
                        oldEstudianteOfPreferenciasNewEstudiantePreferencia.getPreferencias().remove(preferenciasNewEstudiantePreferencia);
                        oldEstudianteOfPreferenciasNewEstudiantePreferencia = em.merge(oldEstudianteOfPreferenciasNewEstudiantePreferencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = estudiante.getId();
                if (findEstudiante(id) == null) {
                    throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.");
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
            Estudiante estudiante;
            try {
                estudiante = em.getReference(Estudiante.class, id);
                estudiante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Interaccion> interaccionOrphanCheck = estudiante.getInteraccion();
            for (Interaccion interaccionOrphanCheckInteraccion : interaccionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the Interaccion " + interaccionOrphanCheckInteraccion + " in its interaccion field has a non-nullable estudiante field.");
            }
            Set<Chat> matchsOrphanCheck = estudiante.getMatchs();
            for (Chat matchsOrphanCheckChat : matchsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the Chat " + matchsOrphanCheckChat + " in its matchs field has a non-nullable e1 field.");
            }
            Set<EstudiantePreferencia> preferenciasOrphanCheck = estudiante.getPreferencias();
            for (EstudiantePreferencia preferenciasOrphanCheckEstudiantePreferencia : preferenciasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the EstudiantePreferencia " + preferenciasOrphanCheckEstudiantePreferencia + " in its preferencias field has a non-nullable estudiante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudiante> findEstudianteEntities() {
        return findEstudianteEntities(true, -1, -1);
    }

    public List<Estudiante> findEstudianteEntities(int maxResults, int firstResult) {
        return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Estudiante> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
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

    public Estudiante findEstudiante(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante> rt = cq.from(Estudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }


}
