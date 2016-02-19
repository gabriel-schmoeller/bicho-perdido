package bichoperdido.persistence.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author Gabriel.
 */
@Entity
@Table(name = "usuario_token")
public class UsuarioTokenEntity {

    @Id
    private String token;
    @Column(name = "usuario_id")
    private int usuarioId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="tempo_limite")
    private Calendar tempoLimite;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="usuario_id", referencedColumnName="id", insertable=false, updatable=false)
    private UsuarioEntity usuarioEntity;

    public UsuarioTokenEntity() {
    }

    public UsuarioTokenEntity(String token, int usuarioId, Calendar tempoLimite) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.tempoLimite = tempoLimite;
    }

    public String getToken() {
        return token;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public Calendar getTempoLimite() {
        return tempoLimite;
    }

    public UsuarioEntity getUsuarioEntity() {
        return usuarioEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioTokenEntity)) return false;
        UsuarioTokenEntity that = (UsuarioTokenEntity) o;
        return Objects.equals(usuarioId, that.usuarioId) &&
                Objects.equals(token, that.token) &&
                Objects.equals(tempoLimite, that.tempoLimite) &&
                Objects.equals(usuarioEntity, that.usuarioEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, usuarioId, tempoLimite, usuarioEntity);
    }
}
