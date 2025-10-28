/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.imp;

import DAO.PreferenciaJpaController;
import InterfacesDAO.IPreferenciaDAO;
import DTO.Enum.Sexo;
import Entity.Preferencia;
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
public class PreferenciaDAO implements IPreferenciaDAO{

    private final PreferenciaJpaController jpaController;

    public PreferenciaDAO(EntityManagerFactory emf) {
        this.jpaController = new PreferenciaJpaController(emf);
    }
    // Metodos Específicos de IPreferenciaDAO
    @Override
    public Optional<Preferencia> buscarPorSexo(Sexo sexo) throws SQLException {
       
        try(EntityManager em = JpaUtil.getEntityManager()) {
            String jpql = "SELECT p FROM Preferencia p WHERE p.sexo = :sexo";
            Query query = em.createQuery(jpql, Preferencia.class);
            query.setParameter("sexo", sexo);
            
            return Optional.ofNullable((Preferencia) query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        } catch (Exception ex) {
            throw new SQLException("Error al buscar Preferencia por sexo.", ex);
        } 
    }

    //Metodos de ICRUD
    @Override
    public Preferencia create(Preferencia entity) throws SQLException {
         try {
            jpaController.create(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al crear la Preferencia.", e);
        }
    }

    @Override
    public Optional<Preferencia> read(long id) throws SQLException {
       return Optional.ofNullable(jpaController.findPreferencia(id));
    }

    @Override
    public Preferencia update(Preferencia entity) throws SQLException {
         try {
            jpaController.edit(entity);
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error al actualizar la Preferencia.", e);
        }
    }

    @Override
    public void delete(long id) throws SQLException {
       try {
            jpaController.destroy(id);
        } catch (Exception e) {
            throw new SQLException("Error al eliminar la Preferencia.", e);
        }
    }

    @Override
    public List<Preferencia> findEntities() throws SQLException {
         return jpaController.findPreferenciaEntities();
     }

    @Override
    public List<Preferencia> findEntities(int maxResults, int firstResult) throws SQLException {
          return jpaController.findPreferenciaEntities(maxResults, firstResult);
    }
    
}
