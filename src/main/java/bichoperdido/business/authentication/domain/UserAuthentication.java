package bichoperdido.business.authentication.domain;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

/**
 * @author Gabriel.
 */
public class UserAuthentication implements Authentication {

    private static final UserRole userRole = UserRole.USER;
    private UserIdentification user;
    private String token;
    private boolean authenticated;

    public UserAuthentication(UserIdentification user, String token) {
        this.user = user;
        this.token = token;
        authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(userRole.toString());
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public String getDetails() {
        return user.getMail();
    }

    @Override
    public UserIdentification getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return user.getName();
    }
}
