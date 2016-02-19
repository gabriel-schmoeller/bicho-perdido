package bichoperdido.persistence.repository;

import bichoperdido.persistence.entities.AnuncioSemelhancaEntity;
import bichoperdido.persistence.entities.AnunciosKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @author Gabriel.
 */
@Component
public interface AnuncioSemelhancaRepository extends CrudRepository<AnuncioSemelhancaEntity, AnunciosKey> {

}
