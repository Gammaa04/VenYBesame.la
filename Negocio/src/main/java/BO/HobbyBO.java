
package BO;

import InterfacesDAO.IHobbyDAO;
import DTO.Enum.TipoHobbies;
import Entity.Hobby;
import InterfacesBO.IHobbyBO;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ferch
 */
public class HobbyBO implements IHobbyBO{

    private final IHobbyDAO hobbyDAO;

    public HobbyBO(IHobbyDAO hobbyDAO) {
        this.hobbyDAO = hobbyDAO;
    }
    
    @Override
    public Hobby crearHobby(Hobby hobby) throws IllegalArgumentException {
      if (hobby == null || hobby.getHobbie() == null) {
            throw new IllegalArgumentException("El Tipo de Hobby es obligatorio.");
        }
        
        try {
            if (hobbyDAO.buscarPorTipo(hobby.getHobbie()).isPresent()) {
                throw new IllegalArgumentException("El Hobby '" + hobby.getHobbie() + "' ya existe en el catálogo.");
            }
            return hobbyDAO.create(hobby);
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear el Hobby.", e);
        }
    }

    @Override
    public Optional<Hobby> buscarHobbyPorId(Long id) {
         if (id == null) {
            throw new IllegalArgumentException("El ID de Hobby no puede ser nulo.");
        }
        try {
             return hobbyDAO.read(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar Hobby por ID.", e);
        }
    }
 
    @Override
    public Optional<Hobby> buscarHobbyPorTipo(TipoHobbies tipoHobby) {
       if (tipoHobby == null) {
            throw new IllegalArgumentException("El tipo de Hobby no puede ser nulo.");
        }
        try {
            return hobbyDAO.buscarPorTipo(tipoHobby);
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar Hobby por Tipo.", e);
        }
    }

    @Override
    public List<Hobby> listarHobbies(int limit) {
        int maxLimit = Math.min(limit, 100); 
        try {
            return hobbyDAO.findEntities(maxLimit, 0);
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar Hobbies.", e);
        }
     }

    @Override
    public Hobby actualizarHobby(Hobby hobby) throws IllegalArgumentException {
         if (hobby == null || hobby.getId() == null || hobby.getHobbie() == null) {
            throw new IllegalArgumentException("ID y Tipo de Hobby son obligatorios para actualizar.");
        }
        try {
            hobbyDAO.read(hobby.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Hobby a actualizar no encontrado."));
        } catch (SQLException ex) {
            Logger.getLogger(HobbyBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            return hobbyDAO.update(hobby);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el Hobby.", e);
        }
     }

    @Override
    public void eliminarHobby(Long id) throws IllegalArgumentException {
         if (id == null) {
            throw new IllegalArgumentException("El ID de Hobby es obligatorio para eliminar.");
        }
        try {
            hobbyDAO.read(id)
                    .orElseThrow(() -> new IllegalArgumentException("Hobby a eliminar no encontrado."));
        } catch (SQLException ex) {
            Logger.getLogger(HobbyBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            hobbyDAO.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el Hobby. Verifique dependencias.", e);
        }
    }
    
}
