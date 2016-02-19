package bichoperdido.persistence.repository;

import bichoperdido.persistence.entities.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @author Gabriel.
 */
@Component
public interface UsuarioRepository extends CrudRepository<UsuarioEntity, Integer> {

    UsuarioEntity findByEmail(String email);
}
