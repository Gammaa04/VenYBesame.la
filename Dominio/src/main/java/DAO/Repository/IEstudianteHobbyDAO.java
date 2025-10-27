
package DAO.Repository;

import DAO.repository.ICRUD;
import Entity.EstudianteHobby;
import Entity.Hobby;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ferch
 */
public interface IEstudianteHobbyDAO extends ICRUD<EstudianteHobby>{
    
    void eliminarPorEstudiante(Long idEstudiante)throws SQLException; // Para la limpieza en EstudianteoBO
    List<Hobby> buscarHobbiesPorEstudiante(Long idEstudiante)throws SQLException; 
    void eliminarPorRelacion(Long idEstudiante, Long idHobby)throws SQLException; // Añadido para eliminar un enlace específico
    // El metodo eliminarHobbyDeEstudiante() en el BO requiere un metodo DAO para eliminar por IDs de relacion.
    
}
