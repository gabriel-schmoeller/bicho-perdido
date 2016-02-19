package bichoperdido.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Gabriel.
 */
@Entity
@Table(name = "anuncio_distancia")
public class AnuncioDistanciaEntity implements Serializable {

    @EmbeddedId
    private AnunciosKey anunciosKey;
    private Double distancia;

    public AnuncioDistanciaEntity() {
    }

    public AnuncioDistanciaEntity(Integer anuncio1Id, Integer anuncio2Id, Double distancia) {
        this.anunciosKey = new AnunciosKey(anuncio1Id, anuncio2Id);
        this.distancia = distancia;
    }

    public AnunciosKey getAnunciosKey() {
        return anunciosKey;
    }

    public Double getDistancia() {
        return distancia;
    }

}
