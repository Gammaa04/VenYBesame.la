
package InterfacesBO;

import DTO.Enum.Sexo;
import Entity.Preferencia;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ferch
 */
public interface IPreferenciaBO {
    Preferencia crearPreferencia(Preferencia preferencia) throws IllegalArgumentException;
    Optional<Preferencia> buscarPreferenciaPorId(Long id);
    Optional<Preferencia> buscarPreferenciaPorSexo(Sexo sexo);
    List<Preferencia> listarPreferencias(int limit);
    Preferencia actualizarPreferencia(Preferencia preferencia) throws IllegalArgumentException;
    void eliminarPreferencia(Long id) throws IllegalArgumentException;
}
