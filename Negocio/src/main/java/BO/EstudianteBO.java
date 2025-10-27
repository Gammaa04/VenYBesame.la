package BO;

import DAO.Repository.IChatDAO;
import DAO.Repository.IEstudianteDAO;
import DAO.Repository.IEstudianteHobbyDAO;
import DAO.Repository.IEstudiantePreferenciaDAO;
import DAO.Repository.IInteraccionDAO;
import DTO.EstudianteDTO;
import Entity.Estudiante;
import InterfacesBO.IEstudianteBO;
import Util.Mapper;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author ferch
 */
public class EstudianteBO implements IEstudianteBO {

    private final IEstudianteDAO estudianteDAO;
    private final IInteraccionDAO interaccionDAO;
    private final IEstudianteHobbyDAO estudianteHobbyDAO;
    private final IEstudiantePreferenciaDAO estudiantePreferenciaDAO;
    private final IChatDAO chatDAO;

    public EstudianteBO(IEstudianteDAO estudianteDAO, IInteraccionDAO interaccionDAO, IEstudianteHobbyDAO estudianteHobbyDAO, IEstudiantePreferenciaDAO estudiantePreferenciaDAO, IChatDAO chatDAO) {
        this.estudianteDAO = estudianteDAO;
        this.interaccionDAO = interaccionDAO;
        this.estudianteHobbyDAO = estudianteHobbyDAO;
        this.estudiantePreferenciaDAO = estudiantePreferenciaDAO;
        this.chatDAO = chatDAO;
    }

    private void validarCamposEstudiante(EstudianteDTO dto) throws IllegalArgumentException {
        if (dto.nombre() == null || dto.nombre().trim().isEmpty()
                || dto.correo() == null || dto.correo().trim().isEmpty()
                || dto.contraseña() == null || dto.contraseña().length() < 6
                || dto.carrera() == null) {
            throw new IllegalArgumentException("Nombre, correo, contraseña (mínimo 6 caracteres) y carrera son obligatorios.");
        }
        if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@potros\\.itson\\.edu\\.mx$", dto.correo())) {
            throw new IllegalArgumentException("El correo debe ser institucional (@potros.itson.edu.mx).");
        }
    }

    @Override
    public Estudiante registrarUsuario(EstudianteDTO estudianteDTO) throws IllegalArgumentException {
        validarCamposEstudiante(estudianteDTO);

        try {
            if (estudianteDAO.buscarPorCorreo(estudianteDTO.correo()).isPresent()) {
                throw new IllegalArgumentException("El correo institucional ya está registrado.");
            }

            Estudiante nuevoEstudiante = Mapper.toEntity(estudianteDTO);
            return estudianteDAO.create(nuevoEstudiante);
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar el usuario en la base de datos.", e);
        }
    }

    @Override
    public Estudiante iniciarSesion(String correo, String contrasena) throws IllegalArgumentException {
        if (correo == null || correo.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            throw new IllegalArgumentException("Correo y contraseña son obligatorios.");
        }
        try {
            return estudianteDAO.buscarPorCredenciales(correo, contrasena)
                    .orElseThrow(() -> new IllegalArgumentException("Credenciales de acceso invalidas."));
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexion al intentar iniciar sesion.", e);
        }
    }

    @Override
    public Estudiante actualizarPerfil(Long idEstudiante, EstudianteDTO estudianteDTO) throws IllegalArgumentException {
        if (idEstudiante == null) {
            throw new IllegalArgumentException("El ID del estudiante es necesario para actualizar.");
        }
        validarCamposEstudiante(estudianteDTO);

        Estudiante existente = null;
        try {
            existente = estudianteDAO.read(idEstudiante)
                    .orElseThrow(() -> new IllegalArgumentException("El perfil del estudiante no existe."));
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (!existente.getCorreo().equals(estudianteDTO.correo())) {
                if (estudianteDAO.buscarPorCorreo(estudianteDTO.correo()).isPresent()) {
                    throw new IllegalArgumentException("El nuevo correo institucional ya esta registrado por otro usuario.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexion al verificar el correo.", e);
        }

        Estudiante datosNuevos = Mapper.toEntity(estudianteDTO);

        existente.setCarrera(datosNuevos.getCarrera());
        existente.setDescripcion(datosNuevos.getDescripcion());
        existente.setFoto(datosNuevos.getFoto());
        existente.setNombre(datosNuevos.getNombre());
        existente.setApPaterno(datosNuevos.getApPaterno());
        existente.setApMaterno(datosNuevos.getApMaterno());
        existente.setCorreo(datosNuevos.getCorreo());
        existente.setContraseña(datosNuevos.getContraseña());

        try {
            return estudianteDAO.update(existente);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el perfil en la base de datos.", e);
        }
    }

    @Override
    public void eliminarCuenta(Long idEstudiante) throws IllegalArgumentException, IllegalStateException {
        if (idEstudiante == null) {
            throw new IllegalArgumentException("ID de estudiante no puede ser nulo.");
        }

        try {
            estudianteDAO.read(idEstudiante)
                    .orElseThrow(() -> new IllegalArgumentException("El estudiante a eliminar no existe."));
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Orquestación de limpieza (se asume que esta secuencia es transaccional en la capa DAO)
            estudianteHobbyDAO.eliminarPorEstudiante(idEstudiante);
            estudiantePreferenciaDAO.eliminarPorEstudiante(idEstudiante);
            interaccionDAO.eliminarInteraccionesPorEstudiante(idEstudiante);

            estudianteDAO.delete(idEstudiante);
        } catch (Exception e) {
            throw new RuntimeException("Error fatal al eliminar la cuenta y sus dependencias.", e);
        }
    }

    @Override
    public Estudiante buscarPorId(Long idEstudiante) throws IllegalArgumentException {
        try {
            return estudianteDAO.read(idEstudiante)
                    .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado con ID: " + idEstudiante));
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar estudiante.", e);
        }
    }

    @Override
    public List<Estudiante> listarEstudiantes(int limit) {
        int maxLimit = Math.min(limit, 100);
        try {
            return estudianteDAO.findEntities(maxLimit, 0);
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar estudiantes.", e);
        }
    }

}
