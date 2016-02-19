package bichoperdido.persistence.repository;

import bichoperdido.persistence.entities.RacaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @author Gabriel.
 */
@Component
public interface RacaRepository extends CrudRepository<RacaEntity, Integer> {

}
