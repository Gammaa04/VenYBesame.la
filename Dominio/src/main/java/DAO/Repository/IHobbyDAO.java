
package DAO.Repository;

import DAO.repository.ICRUD;
import DTO.Enum.TipoHobbies;
import Entity.Hobby;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author ferch
 */
public interface IHobbyDAO extends ICRUD<Hobby>{
    
    Optional<Hobby> buscarPorTipo(TipoHobbies tipoHobby)throws SQLException;
}
