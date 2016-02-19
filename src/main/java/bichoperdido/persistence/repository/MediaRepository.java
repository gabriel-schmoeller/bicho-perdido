package bichoperdido.persistence.repository;

import bichoperdido.persistence.entities.MediaEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Gabriel.
 */
@Component
public interface MediaRepository extends CrudRepository<MediaEntity, Integer> {

    MediaEntity findByAnuncioIdAndCapa(Integer anuncioId, Boolean capa);

    List<MediaEntity> findByAnuncioIdOrderById(Integer anuncioId);

    @Query("SELECT count(m.id) FROM MediaEntity m WHERE m.anuncioId = :anuncio AND tipo = 'i'")
    Long countImagesByAnuncioId(@Param("anuncio") Integer anuncio);

    @Modifying
    @Query(value = "UPDATE media SET capa = FALSE WHERE anuncio_id IN (SELECT anuncio_id FROM media WHERE id = :id)",
            nativeQuery = true)
    void clearCapaOfAnuncioByImage(@Param("id")Integer id);

    @Modifying
    @Query("UPDATE MediaEntity SET capa = true WHERE id = :id AND tipo = 'i'")
    void setAsCapa(@Param("id")Integer id);
}
