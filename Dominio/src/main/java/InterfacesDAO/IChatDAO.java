
package InterfacesDAO;

import DAO.Repository.ICRUD;
import Entity.Chat;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ferch
 */
public interface IChatDAO extends ICRUD<Chat>{
    // Metodos complejos requeridos por InteraccionBO
    Optional<Chat> buscarMatchEntreEstudiantes(Long idEstudiante1, Long idEstudiante2)throws SQLException;
    List<Chat> buscarChatsPorEstudiante(Long idEstudiante)throws SQLException;
}
