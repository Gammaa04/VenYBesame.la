
package DAO.imp;

import DAO.ChatJpaController;
import InterfacesDAO.IChatDAO;
import Entity.Chat;
import JPAUtil.JpaUtil;
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
public class ChatDAO implements IChatDAO{
    private final ChatJpaController jpaController;
   
    public ChatDAO(EntityManagerFactory emf) {
        this.jpaController = new ChatJpaController(emf);
    }
    
    // Implementacion de ICRUD
    
    @Override
    public Chat create(Chat entity) throws SQLException {
        try {
            jpaController.create(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al crear el Chat.", e);
        }
    }

    @Override
    public Chat update(Chat entity) throws SQLException {
        try {
            jpaController.edit(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al actualizar el Chat.", e);
        }
    }

   
    
    @Override
    public List<Chat> findEntities() throws SQLException {
        return jpaController.findChatEntities(); 
    }
    
    @Override
    public List<Chat> findEntities(int maxResults, int firstResult) throws SQLException {
        return jpaController.findChatEntities(maxResults, firstResult); 
    }

    // Metodos Específicos de IChatDAO
    
    @Override
    public Optional<Chat> buscarMatchEntreEstudiantes(Long idEstudiante1, Long idEstudiante2) throws SQLException {
        
        try(EntityManager em = JpaUtil.getEntityManager()) {
            String jpql = "SELECT c FROM Chat c WHERE " +
                          "(c.e1.id = :id1 AND c.e2.id = :id2) OR " +
                          "(c.e1.id = :id2 AND c.e2.id = :id1)";
            
            Query query = em.createQuery(jpql, Chat.class);
            query.setParameter("id1", idEstudiante1);
            query.setParameter("id2", idEstudiante2);
            
            return Optional.ofNullable((Chat) query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        } catch (Exception ex) {
            throw new SQLException("Error al buscar Match entre estudiantes.", ex);
        } 
    }

    @Override
    public List<Chat> buscarChatsPorEstudiante(Long idEstudiante) throws SQLException {
        
        try(EntityManager em = JpaUtil.getEntityManager()){
            String jpql = "SELECT c FROM Chat c WHERE c.e1.id = :id OR c.e2.id = :id";
            
            return em.createQuery(jpql, Chat.class)
                .setParameter("id", idEstudiante)
                .getResultList();
        } catch (Exception ex) {
            throw new SQLException("Error al listar chats del estudiante.", ex);
        } 
    }

    @Override
    public Optional<Chat> read(long id) throws SQLException {
        return Optional.ofNullable(jpaController.findChat(id));
    }

    @Override
    public void delete(long id) throws SQLException {
        try {
            jpaController.destroy(id);
        } catch (Exception e) {
            throw new SQLException("Error al eliminar el Chat.", e);
        }
    }
}
