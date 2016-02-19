package bichoperdido.assyncservice.match.handlers.candidates;

import bichoperdido.assyncservice.match.handlers.CandidatesLoader;
import bichoperdido.business.animal.domain.Especie;
import bichoperdido.business.anuncio.domain.Genero;
import bichoperdido.business.anuncio.domain.Natureza;
import bichoperdido.persistence.entities.AnuncioDistanciaEntity;
import bichoperdido.persistence.entities.AnuncioEntity;
import bichoperdido.persistence.repository.AnuncioCustomRepository;
import bichoperdido.persistence.repository.AnuncioDistanciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Gabriel.
 */
@Component
public class FastCandidatesLoader implements CandidatesLoader {

    @Autowired
    private AnuncioCustomRepository anuncioCustomRepository;
    @Autowired
    private AnuncioDistanciaRepository distanciaRepository;

    @Override
    public Set<AnuncioEntity> load(AnuncioEntity anuncio) {
        List<AnuncioDistanciaEntity> distancias = loadDistancias(anuncio);

        if (!distancias.isEmpty()) {
            Integer anuncioId = anuncio.getId();
            Integer usuarioId = anuncio.getUsuarioId();
            String nome = anuncio.getAnimalEntity().getNome();
            Especie especie = Especie.valueOfByDb(anuncio.getAnimalEntity().getEspecie());
            Genero genero = Genero.valueOfByDb(anuncio.getAnimalEntity().getGenero());
            Calendar dataHora = anuncio.getDataHora();
            Natureza natureza = Natureza.valueOfByDb(anuncio.getNatureza());
            List<Integer> candidates = getBests(anuncioId, distancias);

            return anuncioCustomRepository.getFastCandidates(nome, especie, genero, dataHora, natureza, candidates, usuarioId);
        }

        return new LinkedHashSet<>();
    }

    @Override
    public List<AnuncioDistanciaEntity> loadDistancias(AnuncioEntity anuncio) {
        return distanciaRepository.findByAnuncioIdFast(anuncio.getId(), new PageRequest(0, 100));
    }

    private List<Integer> getBests(Integer anuncioId, List<AnuncioDistanciaEntity> distancias) {
        List<Integer> candidates = new ArrayList<>();

        for (AnuncioDistanciaEntity dist : distancias) {
            Integer candidate = dist.getAnunciosKey().getAnuncio1Id();
            if (candidate.equals(anuncioId)) {
                candidate = dist.getAnunciosKey().getAnuncio2Id();
            }

            candidates.add(candidate);
        }

        return candidates;
    }
}
