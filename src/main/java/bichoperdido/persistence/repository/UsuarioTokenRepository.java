package bichoperdido.persistence.repository;

import bichoperdido.persistence.entities.UsuarioTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Calendar;

/**
 * @author Gabriel.
 */
public interface UsuarioTokenRepository extends CrudRepository<UsuarioTokenEntity, String> {

    UsuarioTokenEntity findByTokenAndTempoLimiteGreaterThan(String token, Calendar now);

    void deleteByTempoLimiteLessThan(Calendar now);
}
