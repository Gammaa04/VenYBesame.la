
package BO;

import DAO.Repository.IChatDAO;
import DAO.Repository.IEstudianteDAO;
import DAO.Repository.IMensajeDAO;
import DTO.MensajeDTO;
import Entity.Chat;
import Entity.Estudiante;
import Entity.Mensaje;
import InterfacesBO.IChatBO;
import Util.Mapper;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ferch
 */
public class ChatBO implements IChatBO{
   private final IChatDAO chatDAO;
    private final IMensajeDAO mensajeDAO; 
    private final IEstudianteDAO estudianteDAO; 

    public ChatBO(IChatDAO chatDAO, IMensajeDAO mensajeDAO, IEstudianteDAO estudianteDAO) {
        this.chatDAO = chatDAO;
        this.mensajeDAO = mensajeDAO;
        this.estudianteDAO = estudianteDAO;
    }
    
    public Chat crearChat(Long idEstudiante1, Long idEstudiante2) throws IllegalArgumentException {
        throw new UnsupportedOperationException("El Match (Chat) debe crearse a través del IInteraccionService.");
    }
    
    @Override
    public Mensaje enviarMensaje(Long idChat, Long idEmisor, MensajeDTO mensajeDTO) throws IllegalStateException, IllegalArgumentException {
        // 1. Validación de Parámetros y Campos
        if (idChat == null || idEmisor == null) {
            throw new IllegalArgumentException("El ID del Chat y del Emisor son obligatorios.");
        }
        if (mensajeDTO.contenido() == null || mensajeDTO.contenido().trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido del mensaje no puede estar vacío.");
        }
        
        // 2. Validación de Integridad (Regla de Negocio: Match/Chat debe existir)
        Chat chatExistente = null;
       try {
           chatExistente = chatDAO.read(idChat)
                   .orElseThrow(() -> new IllegalStateException("No se puede enviar el mensaje: el Chat asociado no existe."));
       } catch (SQLException ex) {
           Logger.getLogger(ChatBO.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        Estudiante emisor = null;
       try {
           emisor = estudianteDAO.read(idEmisor)
                   .orElseThrow(() -> new IllegalArgumentException("El Estudiante emisor no existe."));
       } catch (SQLException ex) {
           Logger.getLogger(ChatBO.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        // 3. Validación de que el emisor pertenece a este chat
        if (!chatExistente.getE1().getId().equals(idEmisor) && !chatExistente.getE2().getId().equals(idEmisor)) {
            throw new IllegalStateException("El estudiante no es participante de este chat.");
        }
        
        // 4. Mapeo y Asignación de referencias
        Mensaje nuevoMensaje = Mapper.toEntity(mensajeDTO);
        nuevoMensaje.setFechaMensaje(new Date()); 
        nuevoMensaje.setChat(chatExistente);
        nuevoMensaje.setEstudiante(emisor);
        
        // 5. Persistencia
        try {
            return mensajeDAO.create(nuevoMensaje);
        } catch (Exception e) {
            throw new RuntimeException("Error persistente al guardar el mensaje.", e);
        }
    }

    @Override
    public List<Mensaje> historialChat(Long idChat, int limit) {
        if (idChat == null) {
            throw new IllegalArgumentException("El ID del Chat es obligatorio.");
        }
        
       try {
           chatDAO.read(idChat)
                   .orElseThrow(() -> new IllegalArgumentException("El Chat no existe."));
       } catch (SQLException ex) {
           Logger.getLogger(ChatBO.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        int maxLimit = Math.min(limit, 100); 
        
        try {
            return mensajeDAO.buscarMensajesPorChat(idChat, maxLimit);
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el historial de chat.", e);
        }
    } 
}
