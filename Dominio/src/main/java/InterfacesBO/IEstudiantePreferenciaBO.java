
package InterfacesBO;

import Entity.EstudiantePreferencia;
import Entity.Preferencia;
import java.util.List;

/**
 *
 * @author ferch
 */
public interface IEstudiantePreferenciaBO {
    
    EstudiantePreferencia agregarPreferenciaAEstudiante(Long idEstudiante, Long idPreferencia) throws IllegalArgumentException;
    void eliminarPreferenciaDeEstudiante(Long idEstudiante, Long idPreferencia) throws IllegalArgumentException;
    List<Preferencia> listarPreferenciasDeEstudiante(Long idEstudiante);
}
