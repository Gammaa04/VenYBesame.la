/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.imp;

import DAO.MensajeJpaController;
import InterfacesDAO.IMensajeDAO;
import Entity.Mensaje;
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
public class MensajeDAO implements IMensajeDAO {

    private final MensajeJpaController jpaController;

    public MensajeDAO(EntityManagerFactory emf) {
        this.jpaController = new MensajeJpaController(emf);
    }

    // Metodos Específicos de IMensajeDAO
    @Override
    public List<Mensaje> buscarMensajesPorChat(Long idChat, int limit) throws SQLException {
        
        try(EntityManager em = JpaUtil.getEntityManager()) {
            String jpql = "SELECT m FROM Mensaje m WHERE m.chat.id = :idChat ORDER BY m.fechaMensaje ASC";

            return em.createQuery(jpql, Mensaje.class)
                    .setParameter("idChat", idChat)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (Exception ex) {
            throw new SQLException("Error al buscar mensajes por chat.", ex);
        } 

    }

    // Metodos de ICRUD
    @Override
    public Mensaje create(Mensaje entity) throws SQLException {
       try {
            jpaController.create(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al crear el Mensaje.", e);
        }
    }

    @Override
    public Optional<Mensaje> read(long id) throws SQLException {
         return Optional.ofNullable(jpaController.findMensaje(id));
    }

    @Override
    public Mensaje update(Mensaje entity) throws SQLException {
         try {
            jpaController.edit(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al actualizar el Mensaje.", e);
        }
    }
 
    @Override
    public void delete(long id) throws SQLException {
       try {
            jpaController.destroy(id);
        } catch (Exception e) {
            throw new SQLException("Error al eliminar el Mensaje.", e);
        }
    }

    @Override
    public List<Mensaje> findEntities() throws SQLException {
       return jpaController.findMensajeEntities();
    }

    @Override
    public List<Mensaje> findEntities(int maxResults, int firstResult) throws SQLException {
       return jpaController.findMensajeEntities(maxResults, firstResult);
    }

   
}
