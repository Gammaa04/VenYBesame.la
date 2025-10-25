/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceConfiguration;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public class JpaUtil {
    private static final String PERSISTENCE_UNIT= "VenYBesamelaPU";
    private static EntityManager em;
    
    private JpaUtil(){
        
    }
    
    public static  EntityManager getEntityManager(){
        if (em==null) {
            em=Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();
        }
        return em;
        

    }
}
