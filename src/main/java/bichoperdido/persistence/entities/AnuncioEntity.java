package bichoperdido.persistence.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

/**
 * @author Gabriel.
 */
@Entity
@Table(name = "anuncio")
public class AnuncioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Integer id;
    @Column(name="usuario_id")
    private Integer usuarioId;
    private Character natureza;
    @Column(name="animal_id")
    private Integer animalId;
    @Column(name="data_hora")
    private Calendar dataHora;
    @Column(name="data_hora_resolvido")
    private Calendar dataHoraResolvido;
    @Column(name="local_longitude")
    private Double localLongitude;
    @Column(name="local_latitude")
    private Double localLatitude;
    @Column(name="local_endereco")
    private String localEndereco;
    private String detalhes;
    private Character status;
    private boolean compared;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="usuario_id", referencedColumnName="id", insertable=false, updatable=false)
    private UsuarioEntity usuarioEntity;

    @OneToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="animal_id", referencedColumnName="id", insertable=false, updatable=false)
    private AnimalEntity animalEntity;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="anuncio_id", referencedColumnName="id", insertable=false, updatable=false)
    private Set<MediaEntity> mediaEntity;

    public AnuncioEntity() {
    }

    public AnuncioEntity(Integer usuarioId, Character natureza, Integer animalId, Calendar dataHora, Double localLongitude, Double localLatitude, String localEndereco, String detalhes, Character status) {
        this.usuarioId = usuarioId;
        this.natureza = natureza;
        this.animalId = animalId;
        this.dataHora = dataHora;
        this.localLongitude = localLongitude;
        this.localLatitude = localLatitude;
        this.localEndereco = localEndereco;
        this.detalhes = detalhes;
        this.status = status;
        this.compared = false;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public Character getNatureza() {
        return natureza;
    }

    public Integer getAnimalId() {
        return animalId;
    }

    public Calendar getDataHora() {
        return dataHora;
    }

    public Calendar getDataHoraResolvido() {
        return dataHoraResolvido;
    }

    public Double getLocalLongitude() {
        return localLongitude;
    }

    public Double getLocalLatitude() {
        return localLatitude;
    }

    public String getLocalEndereco() {
        return localEndereco;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public Character getStatus() {
        return status;
    }

    public UsuarioEntity getUsuarioEntity() {
        return usuarioEntity;
    }

    public AnimalEntity getAnimalEntity() {
        return animalEntity;
    }

    public void setAnimalEntity(AnimalEntity animalEntity) {
        this.animalEntity = animalEntity;
    }

    public Set<MediaEntity> getMediaEntity() {
        return mediaEntity;
    }

    public boolean isCompared() {
        return compared;
    }
}
