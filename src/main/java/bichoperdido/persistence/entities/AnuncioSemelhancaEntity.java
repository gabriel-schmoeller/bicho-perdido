package bichoperdido.persistence.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Gabriel.
 */
@Entity
@Table(name = "anuncio_semelhanca")
public class AnuncioSemelhancaEntity {

    @EmbeddedId
    private AnunciosKey anunciosKey;
    private Double grau;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="anuncio1_id", referencedColumnName="id", insertable=false, updatable=false)
    private AnuncioEntity anuncio1Entity;
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="anuncio2_id", referencedColumnName="id", insertable=false, updatable=false)
    private AnuncioEntity anuncio2Entity;

    public AnuncioSemelhancaEntity() {
    }

    public AnuncioSemelhancaEntity(Integer anuncio1Id, Integer anuncio2Id, Double grau) {
        this.anunciosKey = new AnunciosKey(anuncio1Id, anuncio2Id);
        this.grau = grau;
    }

    public AnunciosKey getAnunciosKey() {
        return anunciosKey;
    }

    public Double getGrau() {
        return grau;
    }

    public AnuncioEntity getAnuncio1Entity() {
        return anuncio1Entity;
    }

    public AnuncioEntity getAnuncio2Entity() {
        return anuncio2Entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnuncioSemelhancaEntity)) return false;
        AnuncioSemelhancaEntity that = (AnuncioSemelhancaEntity) o;
        return Objects.equals(anunciosKey, that.anunciosKey) &&
                Objects.equals(grau, that.grau);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anunciosKey, grau);
    }

    @Override
    public String toString() {
        return "AnuncioSemelhancaEntity{" +
                "anunciosKey=" + anunciosKey +
                ", grau=" + grau +
                '}';
    }
}
