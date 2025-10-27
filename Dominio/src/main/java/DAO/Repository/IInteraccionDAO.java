
package DAO.Repository;

import DAO.repository.ICRUD;
import Entity.Interaccion;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author ferch
 */
public interface IInteraccionDAO extends ICRUD<Interaccion>{
    
    // Metodos complejos requeridos por InteraccionBO
    Optional<Interaccion> buscarInteraccionReciproca(Long idEstudianteA, Long idEstudianteB)throws SQLException;
    boolean yaExisteInteraccion(Long idEstudianteOrigen, Long idEstudianteDestino)throws SQLException;
    void eliminarInteraccionesPorEstudiante(Long idEstudiante)throws SQLException; // Para la limpieza en EstudianteBO
    
}
