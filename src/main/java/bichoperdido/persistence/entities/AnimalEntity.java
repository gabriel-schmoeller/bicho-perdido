package bichoperdido.persistence.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Gabriel.
 */
@Entity
@Table(name = "animal")
public class AnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Integer id;
    private String nome;
    private Character genero;
    private Character especie;
    @Column(name="raca_id")
    private Integer racaId;
    private Character porte;
    private Character pelagem;
    private String outros;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="raca_id", referencedColumnName="id", insertable=false, updatable=false)
    private RacaEntity racaEntity;
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="animal_id", referencedColumnName="id", insertable=false, updatable=false)
    private Set<CorEntity> cores;
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="animal_id", referencedColumnName="id", insertable=false, updatable=false)
    private Set<PeculiaridadeEntity> peculiaridadeEntities;

    public AnimalEntity() {
    }

    public AnimalEntity(String nome, Character genero, Character especie, Integer racaId, Character porte, Character pelagem, String outros) {
        this.nome = nome;
        this.genero = genero;
        this.especie = especie;
        this.racaId = racaId;
        this.porte = porte;
        this.pelagem = pelagem;
        this.outros = outros;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Character getGenero() {
        return genero;
    }

    public Character getEspecie() {
        return especie;
    }

    public Integer getRacaId() {
        return racaId;
    }

    public Character getPorte() {
        return porte;
    }

    public Character getPelagem() {
        return pelagem;
    }

    public String getOutros() {
        return outros;
    }

    public RacaEntity getRacaEntity() {
        return racaEntity;
    }

    public Set<CorEntity> getCores() {
        return cores;
    }

    public Set<PeculiaridadeEntity> getPeculiaridadeEntities() {
        return peculiaridadeEntities;
    }
}
