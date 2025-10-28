
package InterfacesDAO;

import DAO.Repository.ICRUD;
import Entity.Mensaje;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ferch
 */
public interface IMensajeDAO extends ICRUD<Mensaje>{
    // Metodos complejos requeridos por ChatBO
    List<Mensaje> buscarMensajesPorChat(Long idChat, int limit)throws SQLException;
}
