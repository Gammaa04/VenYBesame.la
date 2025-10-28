
package InterfacesBO;

import Entity.EstudianteHobby;
import Entity.Hobby;
import java.util.List;

/**
 *
 * @author ferch
 */
public interface IEstudianteHobbyBO {
    EstudianteHobby agregarHobbyAEstudiante(Long idEstudiante, Long idHobby) throws IllegalArgumentException;
    void eliminarHobbyDeEstudiante(Long idEstudiante, Long idHobby) throws IllegalArgumentException;
    List<Hobby> listarHobbiesDeEstudiante(Long idEstudiante);
}
