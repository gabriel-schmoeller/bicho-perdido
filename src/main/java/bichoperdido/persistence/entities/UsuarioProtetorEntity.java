package bichoperdido.persistence.entities;

import javax.persistence.*;

/**
 * @author Gabriel.
 */
@Entity
@Table(name="usuario_protetor")
public class UsuarioProtetorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Integer id;
    @Column(name="usuario_id")
    private Integer usuarioId;
    private Double raio;
    private Double longitude;
    private Double latitude;

    public UsuarioProtetorEntity() {
    }

    public UsuarioProtetorEntity(Integer usuarioId, Double raio, Double longitude, Double latitude) {
        this.usuarioId = usuarioId;
        this.raio = raio;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public Double getRaio() {
        return raio;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
