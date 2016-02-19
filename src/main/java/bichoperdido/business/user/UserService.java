package bichoperdido.business.user;

import bichoperdido.business.anuncio.domain.Coordenadas;
import bichoperdido.business.authentication.service.UserAuthService;
import bichoperdido.business.user.domain.UsuarioInput;
import bichoperdido.business.user.domain.UsuarioProtetor;
import bichoperdido.business.user.exception.EmailAlreadyExistsException;
import bichoperdido.persistence.entities.UsuarioEntity;
import bichoperdido.persistence.entities.UsuarioProtetorEntity;
import bichoperdido.persistence.repository.UsuarioProtetorRepository;
import bichoperdido.persistence.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Gabriel.
 */
@Component
public class UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioProtetorRepository protetorRepository;
    @Autowired
    private UserAuthService userAuthService;

    public UsuarioEntity create(UsuarioInput usuarioInput) throws EmailAlreadyExistsException {
        if (usuarioRepository.findByEmail(usuarioInput.getEmail()) != null) {
            throw new EmailAlreadyExistsException("E-mail already exists");
        }
        return usuarioRepository.save(new UsuarioEntity(usuarioInput.getNome(), usuarioInput.getTelefone(),
                usuarioInput.getEmail(), userAuthService.encryptPassword(usuarioInput.getSenha())));
    }

    public void updateProtetor(UsuarioProtetor input, Integer usuarioId) {
        Double raio = input.getRaio();
        Double longitude = input.getCentro().getLongitude();
        Double latitude = input.getCentro().getLatitude();

        UsuarioProtetorEntity protetor = new UsuarioProtetorEntity(usuarioId, raio, longitude, latitude);

        UsuarioProtetorEntity actual = protetorRepository.findByUsuarioId(usuarioId);
        if (actual != null) {
            protetor.setId(actual.getId());
        }

        protetorRepository.save(protetor);
    }

    public UsuarioProtetor getProtetor(Integer usuarioId) {
        UsuarioProtetorEntity actual = protetorRepository.findByUsuarioId(usuarioId);
        if (actual != null) {
            return new UsuarioProtetor(actual.getRaio(), new Coordenadas(actual.getLatitude(), actual.getLongitude()));
        }

        return new UsuarioProtetor();
    }

    public void removeProtector(Integer usuarioId) {
        UsuarioProtetorEntity actual = protetorRepository.findByUsuarioId(usuarioId);
        if (actual != null) {
            protetorRepository.delete(actual.getId());
        }
    }
}
