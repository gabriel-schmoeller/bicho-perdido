package bichoperdido.persistence.repository;

import bichoperdido.persistence.entities.AnuncioDistanciaEntity;
import bichoperdido.persistence.entities.AnunciosKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Gabriel.
 */
@Component
public interface AnuncioDistanciaRepository extends CrudRepository<AnuncioDistanciaEntity, AnunciosKey> {

    @Query("SELECT dist FROM AnuncioDistanciaEntity dist \n" +
            "WHERE (dist.anunciosKey.anuncio1Id = :anuncioId AND dist.anunciosKey.anuncio2Id < dist.anunciosKey.anuncio1Id) " +
            "OR (dist.anunciosKey.anuncio2Id = :anuncioId) AND dist.anunciosKey.anuncio1Id < dist.anunciosKey.anuncio2Id " +
            "ORDER BY dist.distancia ASC")
    List<AnuncioDistanciaEntity> findByAnuncioIdFast(@Param("anuncioId") Integer anuncioId, Pageable pageable);

}
