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
    private static EntityManagerFactory emf;
    
    private JpaUtil(){
        
    }
    
    public static  EntityManagerFactory getEntityManagerFactory(){
       
        if (emf==null) {
            emf=Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }
        return emf;

    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            emf = null;
        }
    }

}
