package bichoperdido.business.authentication.domain;

import java.util.Objects;

/**
 * @author Gabriel.
 */
public class UserIdentification {

    private Integer id;
    private final String name;
    private final String mail;

    public UserIdentification(Integer id, String name, String mail) {
        this.id = id;
        this.name = name;
        this.mail = mail;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserIdentification)) return false;
        UserIdentification that = (UserIdentification) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(mail, that.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mail);
    }
}
