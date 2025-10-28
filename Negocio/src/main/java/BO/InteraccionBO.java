/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import InterfacesDAO.IChatDAO;
import InterfacesDAO.IEstudianteDAO;
import InterfacesDAO.IInteraccionDAO;
import DTO.Enum.Reaccion;
import Entity.Chat;
import Entity.Estudiante;
import Entity.Interaccion;
import InterfacesBO.IInteraccionBO;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ferch
 */
public class InteraccionBO implements IInteraccionBO{
    private final IInteraccionDAO interaccionDAO;
    private final IEstudianteDAO estudianteDAO;
    private final IChatDAO chatDAO;

    public InteraccionBO(IInteraccionDAO interaccionDAO, IEstudianteDAO estudianteDAO, IChatDAO chatDAO) {
        this.interaccionDAO = interaccionDAO;
        this.estudianteDAO = estudianteDAO;
        this.chatDAO = chatDAO;
    }

    @Override
    public List<Estudiante> explorarCandidatos(Long idEstudiante, int limit) {
        if (idEstudiante == null) {
            throw new IllegalArgumentException("ID de estudiante origen no puede ser nulo.");
        }
        try {
            estudianteDAO.read(idEstudiante)
                    .orElseThrow(() -> new IllegalArgumentException("Estudiante origen no existe."));
        } catch (SQLException ex) {
            Logger.getLogger(InteraccionBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int maxLimit = Math.min(limit, 100); 
        try {
            return estudianteDAO.buscarCandidatos(idEstudiante, maxLimit); 
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar candidatos para explorar.", e);
        }
    }

    @Override
    public Optional<Chat> registrarInteraccionYBuscarMatch(Long idEstudianteOrigen, Long idEstudianteDestino, Reaccion reaccion) throws IllegalArgumentException {
        if (idEstudianteOrigen == null || idEstudianteDestino == null || reaccion == null) {
            throw new IllegalArgumentException("Todos los parámetros de interacción son obligatorios.");
        }
        if (idEstudianteOrigen.equals(idEstudianteDestino)) {
            throw new IllegalArgumentException("Un estudiante no puede interactuar consigo mismo.");
        }
        
        Estudiante origen = null;
        try {
            origen = estudianteDAO.read(idEstudianteOrigen)
                    .orElseThrow(() -> new IllegalArgumentException("Estudiante origen no existe."));
        } catch (SQLException ex) {
            Logger.getLogger(InteraccionBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        Estudiante destino = null;
        try {
            destino = estudianteDAO.read(idEstudianteDestino)
                    .orElseThrow(() -> new IllegalArgumentException("Estudiante destino no existe."));
        } catch (SQLException ex) {
            Logger.getLogger(InteraccionBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (interaccionDAO.yaExisteInteraccion(idEstudianteOrigen, idEstudianteDestino)) {
                throw new IllegalArgumentException("Ya existe una interaccion registrada entre estos estudiantes.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar la unicidad de la Interaccion.", e);
        }


        // Crear y persistir la nueva Interaccion
        Interaccion nuevaInteraccion = new Interaccion(); 
        nuevaInteraccion.setEstudiante(origen);
        nuevaInteraccion.setEstudianteDestino(destino); 
        nuevaInteraccion.setReaccion(reaccion);
        nuevaInteraccion.setFecha(new Date()); 

        try {
            interaccionDAO.create(nuevaInteraccion); 
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar la Interaccion.", e);
        }

        //Logica de match solo si la reaccion es like
        if (reaccion == Reaccion.LIKE) {
            try {
                // Chequeo de reciprocidad
                Optional<Interaccion> interaccionReciproca = interaccionDAO.buscarInteraccionReciproca(idEstudianteDestino, idEstudianteOrigen);

                if (interaccionReciproca.isPresent()) {
                    // Chequeo final de duplicidad del Chat
                    if (chatDAO.buscarMatchEntreEstudiantes(idEstudianteOrigen, idEstudianteDestino).isEmpty()) {
                        
                        Chat match = new Chat();
                        match.setE1(origen);
                        match.setE2(destino);
                        
                        // Persistencia del Chat
                        Chat chatCreado = chatDAO.create(match); 
                        return Optional.of(chatCreado);
                    }
                }
            } catch (SQLException e) {
                // Fallo transaccional si el DAO no logra el commit.
                throw new RuntimeException("Error al verificar o crear el Match (Chat).", e);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Chat> obtenerMatchesDeEstudiante(Long idEstudiante) {
        if (idEstudiante == null) {
            throw new IllegalArgumentException("ID de estudiante no puede ser nulo.");
        }
        try {
            return chatDAO.buscarChatsPorEstudiante(idEstudiante);
        } catch (SQLException e) {
             throw new RuntimeException("Error al obtener matches del estudiante.", e);
        }
    }
}
