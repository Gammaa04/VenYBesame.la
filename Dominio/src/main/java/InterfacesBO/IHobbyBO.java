
package InterfacesBO;

import DTO.Enum.TipoHobbies;
import Entity.Hobby;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ferch
 */
public interface IHobbyBO {
    Hobby crearHobby(Hobby hobby) throws IllegalArgumentException;
    Optional<Hobby> buscarHobbyPorId(Long id);
    Optional<Hobby> buscarHobbyPorTipo(TipoHobbies tipoHobby);
    List<Hobby> listarHobbies(int limit);
    Hobby actualizarHobby(Hobby hobby) throws IllegalArgumentException;
    void eliminarHobby(Long id) throws IllegalArgumentException;
}
