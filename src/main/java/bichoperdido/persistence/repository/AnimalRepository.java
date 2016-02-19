package bichoperdido.persistence.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import bichoperdido.persistence.entities.AnimalEntity;

/**
 * @author Gabriel.
 */
@Component
public interface AnimalRepository extends CrudRepository<AnimalEntity, Integer> {

    @Modifying
    @Query("UPDATE AnimalEntity SET outros = :outros, racaId = :raca WHERE id = :id")
    void update(@Param("outros") String outros, @Param("raca") Integer raca, @Param("id") Integer id);
}
