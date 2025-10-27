
package InterfacesBO;

import DTO.EstudianteDTO;
import Entity.Estudiante;
import java.util.List;

/**
 *
 * @author ferch
 */
public interface IEstudianteBO {
    Estudiante registrarUsuario(EstudianteDTO estudianteDTO) throws IllegalArgumentException;
    Estudiante iniciarSesion(String correo, String contrasena) throws IllegalArgumentException;
    Estudiante actualizarPerfil(Long idEstudiante, EstudianteDTO estudianteDTO) throws IllegalArgumentException;
    void eliminarCuenta(Long idEstudiante) throws IllegalArgumentException, IllegalStateException;
    Estudiante buscarPorId(Long idEstudiante) throws IllegalArgumentException;
    List<Estudiante> listarEstudiantes(int limit);
}
