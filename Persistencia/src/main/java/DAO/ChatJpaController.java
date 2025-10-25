/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import Entity.Chat;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Entity.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public class ChatJpaController implements Serializable {

    public ChatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Chat chat) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante e1 = chat.getE1();
            if (e1 != null) {
                e1 = em.getReference(e1.getClass(), e1.getId());
                chat.setE1(e1);
            }
            Estudiante e2 = chat.getE2();
            if (e2 != null) {
                e2 = em.getReference(e2.getClass(), e2.getId());
                chat.setE2(e2);
            }
            em.persist(chat);
            if (e1 != null) {
                e1.getMatchs().add(chat);
                e1 = em.merge(e1);
            }
            if (e2 != null) {
                e2.getMatchs().add(chat);
                e2 = em.merge(e2);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Chat chat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Chat persistentChat = em.find(Chat.class, chat.getId());
            Estudiante e1Old = persistentChat.getE1();
            Estudiante e1New = chat.getE1();
            Estudiante e2Old = persistentChat.getE2();
            Estudiante e2New = chat.getE2();
            if (e1New != null) {
                e1New = em.getReference(e1New.getClass(), e1New.getId());
                chat.setE1(e1New);
            }
            if (e2New != null) {
                e2New = em.getReference(e2New.getClass(), e2New.getId());
                chat.setE2(e2New);
            }
            chat = em.merge(chat);
            if (e1Old != null && !e1Old.equals(e1New)) {
                e1Old.getMatchs().remove(chat);
                e1Old = em.merge(e1Old);
            }
            if (e1New != null && !e1New.equals(e1Old)) {
                e1New.getMatchs().add(chat);
                e1New = em.merge(e1New);
            }
            if (e2Old != null && !e2Old.equals(e2New)) {
                e2Old.getMatchs().remove(chat);
                e2Old = em.merge(e2Old);
            }
            if (e2New != null && !e2New.equals(e2Old)) {
                e2New.getMatchs().add(chat);
                e2New = em.merge(e2New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = chat.getId();
                if (findChat(id) == null) {
                    throw new NonexistentEntityException("The chat with id " + id + " no longer exists.");
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
            Chat chat;
            try {
                chat = em.getReference(Chat.class, id);
                chat.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The chat with id " + id + " no longer exists.", enfe);
            }
            Estudiante e1 = chat.getE1();
            if (e1 != null) {
                e1.getMatchs().remove(chat);
                e1 = em.merge(e1);
            }
            Estudiante e2 = chat.getE2();
            if (e2 != null) {
                e2.getMatchs().remove(chat);
                e2 = em.merge(e2);
            }
            em.remove(chat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Chat> findChatEntities() {
        return findChatEntities(true, -1, -1);
    }

    public List<Chat> findChatEntities(int maxResults, int firstResult) {
        return findChatEntities(false, maxResults, firstResult);
    }

    private List<Chat> findChatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Chat.class));
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

    public Chat findChat(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Chat.class, id);
        } finally {
            em.close();
        }
    }

    public int getChatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Chat> rt = cq.from(Chat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
