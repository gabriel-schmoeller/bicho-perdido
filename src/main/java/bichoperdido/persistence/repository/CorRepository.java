package bichoperdido.persistence.repository;

import bichoperdido.persistence.entities.CorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @author Gabriel.
 */
@Component
public interface CorRepository extends CrudRepository<CorEntity, Integer> {

}
