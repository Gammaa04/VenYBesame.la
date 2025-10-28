
package InterfacesDAO;

import DAO.Repository.ICRUD;
import DTO.Enum.Sexo;
import Entity.Preferencia;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author ferch
 */
public interface IPreferenciaDAO extends ICRUD<Preferencia>{
    
    Optional<Preferencia> buscarPorSexo(Sexo sexo)throws SQLException;
}
