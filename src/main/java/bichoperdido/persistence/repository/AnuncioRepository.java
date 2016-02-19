package bichoperdido.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import bichoperdido.persistence.entities.AnuncioEntity;

import javax.transaction.Transactional;

/**
 * @author Gabriel.
 */
@Component
public interface AnuncioRepository extends CrudRepository<AnuncioEntity, Integer> {

    String INSERT_DISTANCE_VALUES = "INSERT INTO anuncio_distancia (anuncio1_id, anuncio2_id, distancia)\n" +
            "  SELECT an2.id, an1.id, (point(an2.local_longitude, an2.local_latitude) <@> point(an1.local_longitude, an1.local_latitude)) * 1.609344\n" +
            "  FROM anuncio as an1 INNER JOIN anuncio as an2 ON an2.id = ?1 WHERE an1.id < an2.id;";

    List<AnuncioEntity> findByUsuarioIdOrderByDataHoraDesc(Integer usuarioId);

    @Modifying
    @Query(value = INSERT_DISTANCE_VALUES, nativeQuery = true)
    void insertAnuncioDistances(Integer anuncioId);

    void deleteByIdAndUsuarioId(int id, int usuarioId);

    @Modifying
    @Query("UPDATE AnuncioEntity a SET a.status = :s WHERE a.id = :id AND a.usuarioId = :uId")
    void updateStatus(@Param("s") Character status, @Param("id") Integer id, @Param("uId") Integer usuarioId);

    @Modifying
    @Transactional
    @Query("UPDATE AnuncioEntity a SET a.compared = :c WHERE a.id = :id")
    void updateCompared(@Param("c") Boolean compared, @Param("id") Integer id);

    @Modifying
    @Query("UPDATE AnuncioEntity a SET a.detalhes = :detalhes WHERE a.id = :id")
    void update(@Param("detalhes") String detalhes, @Param("id") Integer id);

    @Query("SELECT a.animalId FROM AnuncioEntity a WHERE a.id = :id")
    Integer getAnimalId(@Param("id") Integer id);
}
