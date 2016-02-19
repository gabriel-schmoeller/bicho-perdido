package bichoperdido.persistence.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Gabriel.
 */
@Entity
@Table(name = "peculiaridade")
public class PeculiaridadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Integer id;
    @Column(name = "tipo_id")
    private Integer tipoId;
    @Column(name = "local_id")
    private Integer localId;
    @Column(name = "animal_id")
    private Integer animalId;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="local_id", referencedColumnName="id", insertable=false, updatable=false)
    private PeculiaridadeLocalEntity localEntity;
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="tipo_id", referencedColumnName="id", insertable=false, updatable=false)
    private PeculiaridadeTipoEntity tipoEntity;

    public PeculiaridadeEntity() {
    }

    public PeculiaridadeEntity(Integer tipoId, Integer localId, Integer animalId) {
        this.tipoId = tipoId;
        this.localId = localId;
        this.animalId = animalId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTipoId() {
        return tipoId;
    }

    public Integer getLocalId() {
        return localId;
    }

    public String getTipo() {
        return tipoEntity.getNome();
    }

    public String getLocal() {
        return localEntity.getNome();
    }

    public Integer getAnimalId() {
        return animalId;
    }

    public PeculiaridadeLocalEntity getLocalEntity() {
        return localEntity;
    }

    public PeculiaridadeTipoEntity getTipoEntity() {
        return tipoEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeculiaridadeEntity that = (PeculiaridadeEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tipoId, that.tipoId) &&
                Objects.equals(localId, that.localId) &&
                Objects.equals(animalId, that.animalId) &&
                Objects.equals(localEntity, that.localEntity) &&
                Objects.equals(tipoEntity, that.tipoEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoId, localId, animalId, localEntity, tipoEntity);
    }
}
