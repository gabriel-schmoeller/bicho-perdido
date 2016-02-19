package bichoperdido.persistence.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Gabriel.
 */
@Entity
@Table(name = "raca")
public class RacaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Integer id;
    private Character especie;
    private String nome;

    public RacaEntity() {
    }

    public RacaEntity(Character especie, String nome) {
        this.especie = especie;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public Character getEspecie() {
        return especie;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RacaEntity)) return false;
        RacaEntity racaEntity = (RacaEntity) o;
        return Objects.equals(id, racaEntity.id) &&
                Objects.equals(especie, racaEntity.especie) &&
                Objects.equals(nome, racaEntity.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, especie, nome);
    }
}
