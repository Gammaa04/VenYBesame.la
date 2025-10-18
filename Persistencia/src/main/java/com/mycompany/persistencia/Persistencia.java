/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public class Persistencia {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("VenYBesamelaPU");
        EntityManager em= emf.createEntityManager();
    }
}
