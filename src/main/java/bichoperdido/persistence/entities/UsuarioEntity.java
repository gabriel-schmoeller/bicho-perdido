package bichoperdido.persistence.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Gabriel.
 */
@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Integer id;
    private String nome;
    private String telefone;
    private String email;
    private String senha;

    public UsuarioEntity() {
    }

    public UsuarioEntity(String nome, String telefone, String email, String senha) {
        this(null, nome, telefone, email, senha);
    }

    public UsuarioEntity(Integer id, String nome, String telefone, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioEntity)) return false;
        UsuarioEntity usuarioEntity = (UsuarioEntity) o;
        return Objects.equals(id, usuarioEntity.id) &&
                Objects.equals(telefone, usuarioEntity.telefone) &&
                Objects.equals(nome, usuarioEntity.nome) &&
                Objects.equals(email, usuarioEntity.email) &&
                Objects.equals(senha, usuarioEntity.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, telefone, email, senha);
    }
}
