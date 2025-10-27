
package DAO.Repository;

import DAO.repository.ICRUD;
import Entity.EstudiantePreferencia;
import Entity.Preferencia;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ferch
 */
public interface IEstudiantePreferenciaDAO extends ICRUD<EstudiantePreferencia>{
    void eliminarPorEstudiante(Long idEstudiante)throws SQLException; // Para la limpieza en EstudianteBO
    List<Preferencia> buscarPreferenciasPorEstudiante(Long idEstudiante)throws SQLException; 
    void eliminarPorRelacion(Long idEstudiante, Long idPreferencia)throws SQLException; // Añadido para eliminar un enlace específico
    // El metodo eliminarPreferenciaDeEstudiante() en el BO requiere un metodo DAO para eliminar por IDs de relacion.
}
