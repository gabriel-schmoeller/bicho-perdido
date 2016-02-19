package bichoperdido.persistence.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Gabriel.
 */
@Entity
@Table(name = "media")
public class MediaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Integer id;
    private String nome;
    private String caminho;
    private Boolean capa;
    private Character tipo;
    @Column(name = "anuncio_id")
    private Integer anuncioId;

    public MediaEntity() {
    }

    public MediaEntity(String nome, String caminho, Boolean capa, Character tipo, Integer anuncioId) {
        this.nome = nome;
        this.caminho = caminho;
        this.capa = capa;
        this.tipo = tipo;
        this.anuncioId = anuncioId;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCaminho() {
        return caminho;
    }

    public Boolean getCapa() {
        return capa;
    }

    public Character getTipo() {
        return tipo;
    }

    public Integer getAnuncioId() {
        return anuncioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MediaEntity)) return false;
        MediaEntity mediaEntity = (MediaEntity) o;
        return Objects.equals(id, mediaEntity.id) &&
                Objects.equals(nome, mediaEntity.nome) &&
                Objects.equals(caminho, mediaEntity.caminho) &&
                Objects.equals(capa, mediaEntity.capa) &&
                Objects.equals(tipo, mediaEntity.tipo) &&
                Objects.equals(anuncioId, mediaEntity.anuncioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, caminho, capa, tipo, anuncioId);
    }
}
