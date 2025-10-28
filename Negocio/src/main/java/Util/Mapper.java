package Util;

import DTO.ChatDTO;
import DTO.Enum.Carrera;
import DTO.Enum.Reaccion;
import DTO.Enum.TipoHobbies;
import DTO.EstudianteDTO;
import DTO.EstudianteHobbyDTO;
import DTO.EstudiantePreferenciaDTO;
import DTO.HobbyDTO;
import DTO.InteraccionDTO;
import DTO.MensajeDTO;
import DTO.PreferenciaDTO;
import Entity.Chat;
import Entity.Estudiante;
import Entity.EstudianteHobby;
import Entity.EstudiantePreferencia;
import Entity.Hobby;
import Entity.Interaccion;
import Entity.Mensaje;
import Entity.Preferencia;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author ferch
 */
public class Mapper {
    //Mapeo DTO -> ENTITY

    public static Estudiante toEntity(EstudianteDTO dto) {
        if (dto == null) {
            return null;
        }

        Estudiante entity = new Estudiante();

        entity.setCarrera(dto.carrera());
        entity.setDescripcion(dto.descripcion());
        entity.setFoto(dto.foto());
        entity.setNombre(dto.nombre());
        entity.setApPaterno(dto.apPaterno());
        entity.setApMaterno(dto.apMaterno());
        entity.setCorreo(dto.correo());
        entity.setContraseña(dto.contraseña());

        return entity;
    }

    public static Mensaje toEntity(MensajeDTO dto) {
        if (dto == null) {
            return null;
        }

        Mensaje entity = new Mensaje();

        entity.setContenido(dto.contenido());
        entity.setFechaMensaje(dto.fechaMensaje());

        return entity;
    }

    public static Chat toEntity(ChatDTO dto) {
        if (dto == null) {
            return null;
        }

        Chat entity = new Chat();

        return entity;
    }

    public static Interaccion toEntity(InteraccionDTO dto) {
        if (dto == null) {
            return null;
        }
        Interaccion entity = new Interaccion();

        entity.setReaccion(dto.reaccion());
        entity.setFecha(dto.fecha());

        return entity;
    }

    public static Hobby toEntity(HobbyDTO dto) {
        if (dto == null) {
            return null;
        }
        Hobby entity = new Hobby();
        entity.setHobbie(dto.hobbie());
        return entity;
    }

    public static Preferencia toEntity(PreferenciaDTO dto) {
        if (dto == null) {
            return null;
        }
        Preferencia entity = new Preferencia();
        entity.setSexo(dto.sexo());
        entity.setContenido(dto.contenido());
        return entity;
    }

    public static EstudianteHobby toEntity(EstudianteHobbyDTO dto) {
        if (dto == null) {
            return null;
        }
        EstudianteHobby entity = new EstudianteHobby();

        return entity;
    }

    public static EstudiantePreferencia toEntity(EstudiantePreferenciaDTO dto) {
        if (dto == null) {
            return null;
        }
        EstudiantePreferencia entity = new EstudiantePreferencia();

        return entity;
    }

    // Mapeo ENTITY -> DTO 
    public static EstudianteDTO toDTO(Estudiante entity) {
        if (entity == null) {
            return null;
        }

        return new EstudianteDTO.BuiderEstudiante()
                .carrera((Carrera) entity.getCarrera())
                .descripcion(entity.getDescripcion())
                .foto(entity.getFoto())
                .nombre(entity.getNombre())
                .apPaterno(entity.getApPaterno())
                .apMaterno(entity.getApMaterno())
                .correo(entity.getCorreo())
                .contraseña(entity.getContraseña())
                .build();
    }

    public static MensajeDTO toDTO(Mensaje entity) {
        if (entity == null) {
            return null;
        }

        return new MensajeDTO(
                entity.getFechaMensaje(),
                entity.getContenido(),
                toDTO(entity.getEstudiante()),
                toDTO(entity.getChat())
        );
    }

    public static ChatDTO toDTO(Chat entity) {
        if (entity == null) {
            return null;
        }

        // Mapea la lista de mensajes de forma recursiva
        List<MensajeDTO> mensajesDTO = entity.getMensajes() != null
                ? entity.getMensajes().stream().map(Mapper::toDTO).collect(Collectors.toList()) : List.of();

        return new ChatDTO(
                toDTO(entity.getE1()),
                toDTO(entity.getE2()),
                mensajesDTO
        );
    }

    public static InteraccionDTO toDTO(Interaccion entity) {
        if (entity == null) {
            return null;
        }

        return new InteraccionDTO(
                (Reaccion) entity.getReaccion(),
                entity.getFecha(),
                toDTO(entity.getEstudiante())
        );
    }

    public static HobbyDTO toDTO(Hobby entity) {
        if (entity == null) {
            return null;
        }
        return new HobbyDTO(
                (TipoHobbies) entity.getHobbie(),
                entity.getEstudiante() != null
                ? entity.getEstudiante().stream().map(Mapper::toDTO).collect(Collectors.toSet()) : new HashSet<>()
        );
    }

    public static PreferenciaDTO toDTO(Preferencia entity) {
        if (entity == null) {
            return null;
        }
        return new PreferenciaDTO(
                entity.getSexo(),
                entity.getContenido(),
                entity.getPreferencias() != null
                ? entity.getPreferencias().stream().map(Mapper::toDTO).collect(Collectors.toSet()) : new HashSet<>()
        );
    }

    public static EstudianteHobbyDTO toDTO(EstudianteHobby entity) {
        if (entity == null) {
            return null;
        }
        return new EstudianteHobbyDTO(
                toDTO(entity.getEstudiante()),
                toDTO(entity.getHobby())
        );
    }

    public static EstudiantePreferenciaDTO toDTO(EstudiantePreferencia entity) {
        if (entity == null) {
            return null;
        }
        return new EstudiantePreferenciaDTO(
                toDTO(entity.getEstudiante()),
                toDTO(entity.getPreferencia())
        );
    }
}
