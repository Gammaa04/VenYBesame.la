/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import InterfacesDAO.IEstudianteDAO;
import InterfacesDAO.IEstudianteHobbyDAO;
import InterfacesDAO.IHobbyDAO;
import Entity.Estudiante;
import Entity.EstudianteHobby;
import Entity.Hobby;
import InterfacesBO.IEstudianteHobbyBO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ferch
 */
public class EstudianteHobbyBO implements IEstudianteHobbyBO {

    private final IEstudianteHobbyDAO estudianteHobbyDAO;
    private final IEstudianteDAO estudianteDAO;
    private final IHobbyDAO hobbyDAO;

    public EstudianteHobbyBO(IEstudianteHobbyDAO estudianteHobbyDAO, IEstudianteDAO estudianteDAO, IHobbyDAO hobbyDAO) {
        this.estudianteHobbyDAO = estudianteHobbyDAO;
        this.estudianteDAO = estudianteDAO;
        this.hobbyDAO = hobbyDAO;
    }

    @Override
    public EstudianteHobby agregarHobbyAEstudiante(Long idEstudiante, Long idHobby) throws IllegalArgumentException {
        if (idEstudiante == null || idHobby == null) {
            throw new IllegalArgumentException("IDs de Estudiante y Hobby son obligatorios.");
        }

        Estudiante estudiante = null;
        try {
            estudiante = estudianteDAO.read(idEstudiante)
                    .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado."));
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteHobbyBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        Hobby hobby = null;
        try {
            hobby = hobbyDAO.read(idHobby)
                    .orElseThrow(() -> new IllegalArgumentException("Hobby no encontrado."));
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteHobbyBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        EstudianteHobby enlace = new EstudianteHobby();
        enlace.setEstudiantes(estudiante);
        enlace.setHobby(hobby);

        try {
            return estudianteHobbyDAO.create(enlace);
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar Hobby al Estudiante.", e);
        }
    }

    @Override
    public void eliminarHobbyDeEstudiante(Long idEstudiante, Long idHobby) throws IllegalArgumentException {
        if (idEstudiante == null || idHobby == null) {
            throw new IllegalArgumentException("IDs de Estudiante y Hobby son obligatorios.");
        }

        try {
            estudianteHobbyDAO.eliminarPorRelacion(idEstudiante, idHobby);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar Hobby del Estudiante.", e);
        }
    }

    @Override
    public List<Hobby> listarHobbiesDeEstudiante(Long idEstudiante) {
        if (idEstudiante == null) {
            throw new IllegalArgumentException("ID de Estudiante es obligatorio.");
        }
        try {
            return estudianteHobbyDAO.buscarHobbiesPorEstudiante(idEstudiante);
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar Hobbies del Estudiante.", e);
        }
    }

}
