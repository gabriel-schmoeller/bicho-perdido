package bichoperdido.persistence.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import bichoperdido.persistence.entities.PeculiaridadeEntity;

/**
 * @author Gabriel.
 */
@Component
public interface PeculiaridadeRepository extends CrudRepository<PeculiaridadeEntity, Integer> {

    @Modifying
    @Query("DELETE PeculiaridadeEntity WHERE animalId = :animalId")
    void deleteByAnimal(@Param("animalId") Integer animalId);
}
