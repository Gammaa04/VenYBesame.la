/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.persistencia;

import DAO.EstudianteDAO;
import DTO.EstudianteDTO;
import Entity.Enum.Carrera;
import Entity.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public class Persistencia {

    public static void main(String[] args) {
        EstudianteDAO estudianteDAO= new EstudianteDAO();
        EstudianteDTO dTO = null;
        
        Estudiante e= new Estudiante(Long.MIN_VALUE, Carrera.ING_CIVIL, descripcion, foto, nombre, apPaterno, apMaterno, correo, contraseña, interaccion, matchs, Hobbys, preferencias);
        
        
        
    }
}
