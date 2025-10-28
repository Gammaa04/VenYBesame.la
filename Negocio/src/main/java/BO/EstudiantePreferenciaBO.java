
package BO;

import InterfacesDAO.IEstudianteDAO;
import InterfacesDAO.IEstudiantePreferenciaDAO;
import InterfacesDAO.IPreferenciaDAO;
import Entity.Estudiante;
import Entity.EstudiantePreferencia;
import Entity.Preferencia;
import InterfacesBO.IEstudiantePreferenciaBO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ferch
 */
public class EstudiantePreferenciaBO implements IEstudiantePreferenciaBO{
    private final IEstudiantePreferenciaDAO estudiantePreferenciaDAO;
    private final IEstudianteDAO estudianteDAO;
    private final IPreferenciaDAO preferenciaDAO;

    public EstudiantePreferenciaBO(IEstudiantePreferenciaDAO estudiantePreferenciaDAO, IEstudianteDAO estudianteDAO, IPreferenciaDAO preferenciaDAO) {
        this.estudiantePreferenciaDAO = estudiantePreferenciaDAO;
        this.estudianteDAO = estudianteDAO;
        this.preferenciaDAO = preferenciaDAO;
    }

    @Override
    public EstudiantePreferencia agregarPreferenciaAEstudiante(Long idEstudiante, Long idPreferencia) throws IllegalArgumentException {
         if (idEstudiante == null || idPreferencia == null) {
            throw new IllegalArgumentException("IDs de Estudiante y Preferencia son obligatorios.");
        }
        
        Estudiante estudiante = null;
        try {
            estudiante = estudianteDAO.read(idEstudiante)
                    .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado."));
        } catch (SQLException ex) {
            Logger.getLogger(EstudiantePreferenciaBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        Preferencia preferencia = null;
        try {
            preferencia = preferenciaDAO.read(idPreferencia)
                    .orElseThrow(() -> new IllegalArgumentException("Preferencia no encontrada."));
        } catch (SQLException ex) {
            Logger.getLogger(EstudiantePreferenciaBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        EstudiantePreferencia enlace = new EstudiantePreferencia();
        enlace.setEstudiantes(estudiante);
        enlace.setPreferencia(preferencia);
        
        try {
            return estudiantePreferenciaDAO.create(enlace);
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar Preferencia al Estudiante.", e);
        }
    }

    @Override
    public void eliminarPreferenciaDeEstudiante(Long idEstudiante, Long idPreferencia) throws IllegalArgumentException {
      if (idEstudiante == null || idPreferencia == null) {
            throw new IllegalArgumentException("IDs de Estudiante y Preferencia son obligatorios.");
        }
        
        try {
             estudiantePreferenciaDAO.eliminarPorRelacion(idEstudiante, idPreferencia);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar Preferencia del Estudiante.", e);
        }
    }

    @Override
    public List<Preferencia> listarPreferenciasDeEstudiante(Long idEstudiante) {
        if (idEstudiante == null) {
            throw new IllegalArgumentException("ID de Estudiante es obligatorio.");
        }
        try {
            return estudiantePreferenciaDAO.buscarPreferenciasPorEstudiante(idEstudiante);
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar Preferencias del Estudiante.", e);
        }
    }
    
}
