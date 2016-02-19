package bichoperdido.persistence.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Gabriel.
 */
@Entity
@Table(name = "cor")
public class CorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Integer id;
    @Column(name = "animal_id")
    private Integer animalId;
    private Double l;
    private Double a;
    private Double b;

    public CorEntity() {
    }

    public CorEntity(Integer animalId, Double l, Double a, Double b) {
        this.animalId = animalId;
        this.l = l;
        this.a = a;
        this.b = b;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAnimalId() {
        return animalId;
    }

    public Double getL() {
        return l;
    }

    public Double getA() {
        return a;
    }

    public Double getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CorEntity)) return false;
        CorEntity corEntity = (CorEntity) o;
        return Objects.equals(id, corEntity.id) &&
                Objects.equals(animalId, corEntity.animalId) &&
                Objects.equals(l, corEntity.l) &&
                Objects.equals(a, corEntity.a) &&
                Objects.equals(b, corEntity.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, animalId, l, a, b);
    }
}
