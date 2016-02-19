package bichoperdido.assyncservice.match.handlers;

import bichoperdido.persistence.entities.AnuncioDistanciaEntity;
import bichoperdido.persistence.entities.AnuncioEntity;

import java.util.List;
import java.util.Set;

/**
 * @author Gabriel.
 */
public interface CandidatesLoader {

    Set<AnuncioEntity> load(AnuncioEntity anuncio);

    List<AnuncioDistanciaEntity> loadDistancias(AnuncioEntity anuncio);
}
