package bichoperdido.persistence.repository;

import bichoperdido.persistence.entities.PeculiaridadeTipoEntity;
import bichoperdido.persistence.entities.RacaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @author Gabriel.
 */
@Component
public interface PeculiaridadeTipoRepository extends CrudRepository<PeculiaridadeTipoEntity, Integer> {

}
