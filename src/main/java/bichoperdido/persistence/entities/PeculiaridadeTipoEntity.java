package bichoperdido.persistence.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Gabriel.
 */
@Entity
@Table(name = "peculiaridade_tipo")
public class PeculiaridadeTipoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Integer id;
    private String nome;

    public PeculiaridadeTipoEntity() {
    }

    public PeculiaridadeTipoEntity(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeculiaridadeTipoEntity that = (PeculiaridadeTipoEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
