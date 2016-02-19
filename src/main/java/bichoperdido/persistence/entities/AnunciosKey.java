package bichoperdido.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Gabriel.
 */
@Embeddable
public class AnunciosKey implements Serializable {

    @Column(name = "anuncio1_id")
    private Integer anuncio1Id;
    @Column(name = "anuncio2_id")
    private Integer anuncio2Id;

    public AnunciosKey() {
    }

    public AnunciosKey(Integer anuncio1Id, Integer anuncio2Id) {
        this.anuncio1Id = anuncio1Id;
        this.anuncio2Id = anuncio2Id;
    }

    public Integer getAnuncio1Id() {
        return anuncio1Id;
    }

    public Integer getAnuncio2Id() {
        return anuncio2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnunciosKey)) return false;
        AnunciosKey that = (AnunciosKey) o;
        return Objects.equals(anuncio1Id, that.anuncio1Id) &&
                Objects.equals(anuncio2Id, that.anuncio2Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anuncio1Id, anuncio2Id);
    }

    @Override
    public String toString() {
        return "AnunciosKey{" +
                "anuncio1Id=" + anuncio1Id +
                ", anuncio2Id=" + anuncio2Id +
                '}';
    }
}
