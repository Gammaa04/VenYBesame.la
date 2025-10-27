
package InterfacesBO;

import DTO.MensajeDTO;
import Entity.Mensaje;
import java.util.List;

/**
 *
 * @author ferch
 */
public interface IChatBO {
    Mensaje enviarMensaje(Long idChat, Long idEmisor, MensajeDTO mensajeDTO) throws IllegalStateException, IllegalArgumentException;
    List<Mensaje> historialChat(Long idChat, int limit); 
}
