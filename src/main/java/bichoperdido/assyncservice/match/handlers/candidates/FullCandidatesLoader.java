package bichoperdido.assyncservice.match.handlers.candidates;

import bichoperdido.assyncservice.match.handlers.CandidatesLoader;
import bichoperdido.business.animal.domain.Especie;
import bichoperdido.business.anuncio.domain.Genero;
import bichoperdido.business.anuncio.domain.Natureza;
import bichoperdido.persistence.entities.AnuncioDistanciaEntity;
import bichoperdido.persistence.entities.AnuncioEntity;
import bichoperdido.persistence.repository.AnuncioCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * @author Gabriel.
 */
@Component
public class FullCandidatesLoader  implements CandidatesLoader {

    @Autowired
    private AnuncioCustomRepository anuncioCustomRepository;

    @Override
    public Set<AnuncioEntity> load(AnuncioEntity anuncio) {
        Integer anuncioId = anuncio.getId();
        Integer usuarioId = anuncio.getUsuarioId();
        String nome = anuncio.getAnimalEntity().getNome();
        Especie especie = Especie.valueOfByDb(anuncio.getAnimalEntity().getEspecie());
        Genero genero = Genero.valueOfByDb(anuncio.getAnimalEntity().getGenero());
        Calendar dataHora = anuncio.getDataHora();
        Natureza natureza = Natureza.valueOfByDb(anuncio.getNatureza());

        return anuncioCustomRepository.getAllCandidates(anuncioId, nome, especie, genero, dataHora, natureza, usuarioId);
    }

    @Override
    public List<AnuncioDistanciaEntity> loadDistancias(AnuncioEntity anuncio) {
        Integer anuncioId = anuncio.getId();
        Integer usuarioId = anuncio.getUsuarioId();
        String nome = anuncio.getAnimalEntity().getNome();
        Especie especie = Especie.valueOfByDb(anuncio.getAnimalEntity().getEspecie());
        Genero genero = Genero.valueOfByDb(anuncio.getAnimalEntity().getGenero());
        Calendar dataHora = anuncio.getDataHora();
        Natureza natureza = Natureza.valueOfByDb(anuncio.getNatureza());

        return anuncioCustomRepository.getDistanciasAllCandidates(anuncioId, nome, especie, genero, dataHora, natureza, usuarioId);
    }
}
