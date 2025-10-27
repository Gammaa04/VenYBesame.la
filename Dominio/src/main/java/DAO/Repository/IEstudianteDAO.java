
package DAO.Repository;

import DAO.repository.ICRUD;
import Entity.Estudiante;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ferch
 */
public interface IEstudianteDAO extends ICRUD<Estudiante>{
    
    // Metodos complejos requeridos por EstudianteBO y InteraccionBO
    Optional<Estudiante> buscarPorCorreo(String correo)throws SQLException; 
    Optional<Estudiante> buscarPorCredenciales(String correo, String contrasena)throws SQLException; 
    List<Estudiante> buscarCandidatos(Long idEstudianteActual, int limit)throws SQLException;
    // ICRUD ya proporciona read(id), create(entity), update(entity), delete(id), findEntities

}
