package bichoperdido.business.authentication.service;

import bichoperdido.business.authentication.exception.InvalidTokenException;
import bichoperdido.persistence.entities.UsuarioEntity;
import bichoperdido.persistence.entities.UsuarioTokenEntity;
import bichoperdido.persistence.repository.UsuarioTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author Gabriel
 */
@Component
public class TokenAuthService {

    public static final int TIME_LIMIT = 1;

    @Autowired
    private UsuarioTokenRepository tokenRepository;

    public String createToken(int userId) {
        String token = UUID.randomUUID().toString();
        Calendar tempoLimite = generateNextTempoLimite();

        tokenRepository.save(new UsuarioTokenEntity(token, userId, tempoLimite));

        return token;
    }

    public UsuarioEntity retrieveOwner(String token) throws InvalidTokenException {
        UsuarioTokenEntity usuarioTokenEntity =
                tokenRepository.findByTokenAndTempoLimiteGreaterThan(token, Calendar.getInstance());

        if (usuarioTokenEntity != null) {
            return usuarioTokenEntity.getUsuarioEntity();
        }

        throw new InvalidTokenException("Invalid token.");
    }

    public void remove(String token) {
        tokenRepository.delete(token);
    }

    public void updateToken(String token, Integer userId) {
        tokenRepository.save(new UsuarioTokenEntity(token, userId, generateNextTempoLimite()));
    }

    private Calendar generateNextTempoLimite() {
        Calendar tempoLimite = Calendar.getInstance();
        tempoLimite.add(Calendar.HOUR, TIME_LIMIT);

        return tempoLimite;
    }

    @Transactional
    public void removeExpiredTokens() {
        tokenRepository.deleteByTempoLimiteLessThan(Calendar.getInstance());
    }
}
