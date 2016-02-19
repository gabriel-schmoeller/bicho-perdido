package bichoperdido.business.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import bichoperdido.business.authentication.domain.UserAuthentication;
import bichoperdido.business.authentication.domain.UserCredentials;
import bichoperdido.business.authentication.domain.UserIdentification;
import bichoperdido.business.authentication.exception.BadCredentialsException;
import bichoperdido.business.authentication.exception.InvalidTokenException;
import bichoperdido.persistence.entities.UsuarioEntity;
import bichoperdido.persistence.repository.UsuarioRepository;

/**
 * @author Gabriel.
 */
@Component
public class UserAuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenAuthService tokenAuthService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String authUser(UserCredentials credentials) throws BadCredentialsException {
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(credentials.getEmail());

        if (usuarioEntity != null && passwordEncoder.matches(credentials.getSenha(), usuarioEntity.getSenha())) {
            return tokenAuthService.createToken(usuarioEntity.getId());
        }

        throw new BadCredentialsException("Invalid mail or password.");
    }

    public UserIdentification authUser(String token) throws InvalidTokenException {
        UsuarioEntity usuarioEntity = tokenAuthService.retrieveOwner(token);

        return new UserIdentification(usuarioEntity.getId(), usuarioEntity.getNome(), usuarioEntity.getEmail());
    }

    public void setAuthUser(UserIdentification user, String token) {
        SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(user, token));
    }

    public UserIdentification getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthentication) {
            return ((UserAuthentication) authentication).getPrincipal();
        }

        return null;
    }

    public String getAuthUserToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthentication) {
            return ((UserAuthentication) authentication).getCredentials();
        }

        return null;
    }

    public void logout() {
        UserAuthentication auth = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();

        tokenAuthService.remove(auth.getCredentials());
    }
}
