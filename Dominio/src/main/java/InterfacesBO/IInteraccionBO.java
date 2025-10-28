
package InterfacesBO;

import DTO.Enum.Reaccion;
import Entity.Chat;
import Entity.Estudiante;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ferch
 */
public interface IInteraccionBO {
    List<Estudiante> explorarCandidatos(Long idEstudiante, int limit);
    Optional<Chat> registrarInteraccionYBuscarMatch(Long idEstudianteOrigen, Long idEstudianteDestino, Reaccion reaccion) throws IllegalArgumentException;
    List<Chat> obtenerMatchesDeEstudiante(Long idEstudiante);
}
