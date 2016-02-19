package bichoperdido.business.authentication.domain;

import java.util.Objects;

/**
 * @author Gabriel.
 */
public class UserCredentials {

    private String email;
    private String senha;

    public UserCredentials() {
    }

    public UserCredentials(String email, String senha) {
        this.email = email;
        this.senha = senha;
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
        if (!(o instanceof UserCredentials)) return false;
        UserCredentials that = (UserCredentials) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(senha, that.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, senha);
    }
}
