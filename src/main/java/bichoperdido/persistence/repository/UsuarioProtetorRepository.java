package bichoperdido.persistence.repository;

import bichoperdido.persistence.entities.UsuarioProtetorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @author Gabriel.
 */
@Component
public interface UsuarioProtetorRepository extends CrudRepository<UsuarioProtetorEntity, Integer> {

    UsuarioProtetorEntity findByUsuarioId(Integer usuarioId);
}
