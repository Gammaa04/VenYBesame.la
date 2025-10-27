package BO;

import DAO.Repository.IPreferenciaDAO;
import DTO.Enum.Sexo;
import Entity.Preferencia;
import InterfacesBO.IPreferenciaBO;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ferch
 */
public class PreferenciaBO implements IPreferenciaBO {

    private final IPreferenciaDAO preferenciaDAO;

    public PreferenciaBO(IPreferenciaDAO preferenciaDAO) {
        this.preferenciaDAO = preferenciaDAO;
    }

    @Override
    public Preferencia crearPreferencia(Preferencia preferencia) throws IllegalArgumentException {
        if (preferencia == null || preferencia.getSexo() == null || preferencia.getContenido() == null) {
            throw new IllegalArgumentException("El Sexo y el Contenido de la preferencia son obligatorios.");
        }

        try {
            if (preferenciaDAO.buscarPorSexo(preferencia.getSexo()).isPresent()) {
                throw new IllegalArgumentException("La Preferencia para el sexo '" + preferencia.getSexo() + "' ya existe.");
            }
            return preferenciaDAO.create(preferencia);
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la Preferencia.", e);
        }
    }

    @Override
    public Optional<Preferencia> buscarPreferenciaPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de Preferencia no puede ser nulo.");
        }
        try {
            return preferenciaDAO.read(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar Preferencia por ID.", e);
        }
    }

    @Override
    public Optional<Preferencia> buscarPreferenciaPorSexo(Sexo sexo) {
        if (sexo == null) {
            throw new IllegalArgumentException("El sexo de preferencia no puede ser nulo.");
        }
        try {
            return preferenciaDAO.buscarPorSexo(sexo);
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar Preferencia por Sexo.", e);
        }
    }

    @Override
    public List<Preferencia> listarPreferencias(int limit) {
        int maxLimit = Math.min(limit, 100);
        try {
            return preferenciaDAO.findEntities(maxLimit, 0);
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar Preferencias.", e);
        }
    }

    @Override
    public Preferencia actualizarPreferencia(Preferencia preferencia) throws IllegalArgumentException {
        if (preferencia == null || preferencia.getId() == null) {
            throw new IllegalArgumentException("El ID de Preferencia es obligatorio para actualizar.");
        }
        try {
            preferenciaDAO.read(preferencia.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Preferencia a actualizar no encontrada."));
        } catch (SQLException ex) {
            Logger.getLogger(PreferenciaBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            return preferenciaDAO.update(preferencia);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la Preferencia.", e);
        }
    }

    @Override
    public void eliminarPreferencia(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("El ID de Preferencia es obligatorio para eliminar.");
        }
        try {
            preferenciaDAO.read(id)
                    .orElseThrow(() -> new IllegalArgumentException("Preferencia a eliminar no encontrada."));
        } catch (SQLException ex) {
            Logger.getLogger(PreferenciaBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            preferenciaDAO.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la Preferencia. Verifique dependencias.", e);
        }
    }

}
