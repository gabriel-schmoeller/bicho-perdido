package bichoperdido.business.animal;

import bichoperdido.business.animal.domain.Especie;
import bichoperdido.business.animal.domain.Raca;
import bichoperdido.persistence.entities.PeculiaridadeLocalEntity;
import bichoperdido.persistence.entities.PeculiaridadeTipoEntity;
import bichoperdido.persistence.entities.RacaEntity;
import bichoperdido.persistence.repository.PeculiaridadeLocalRepository;
import bichoperdido.persistence.repository.PeculiaridadeTipoRepository;
import bichoperdido.persistence.repository.RacaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel.
 */
@Component
public class AnimalService {

    @Autowired
    private RacaRepository racaRepository;
    @Autowired
    private PeculiaridadeLocalRepository peculiaridadeLocalRepository;
    @Autowired
    private PeculiaridadeTipoRepository peculiaridadeTipoRepository;

    public List<Raca> getAllRacas() {
        Iterable<RacaEntity> racaEntities = racaRepository.findAll();
        return transformRaca(racaEntities);
    }

    public Map<Integer, String> getAllPeculiaridadeLocal() {
        Iterable<PeculiaridadeLocalEntity> entities = peculiaridadeLocalRepository.findAll();

        return transformPeculiaridadeLocal(entities);
    }

    public Map<Integer, String> getAllPeculiaridadeTipo() {
        Iterable<PeculiaridadeTipoEntity> entities = peculiaridadeTipoRepository.findAll();

        return transformPeculiaridadeTipo(entities);
    }

    private Map<Integer, String> transformPeculiaridadeTipo(Iterable<PeculiaridadeTipoEntity> entities) {
        Map<Integer, String> tipos = new HashMap<>();

        for (PeculiaridadeTipoEntity entity : entities) {
            tipos.put(entity.getId(), entity.getNome());
        }

        return tipos;
    }

    private Map<Integer, String> transformPeculiaridadeLocal(Iterable<PeculiaridadeLocalEntity> entities) {
        Map<Integer, String> tipos = new HashMap<>();

        for (PeculiaridadeLocalEntity entity : entities) {
            tipos.put(entity.getId(), entity.getNome());
        }

        return tipos;
    }

    private List<Raca> transformRaca(Iterable<RacaEntity> entities) {
        List<Raca> racas = new ArrayList<>();

        for (RacaEntity racaEntity : entities) {
            racas.add(new Raca(racaEntity.getId(), Especie.valueOfByDb(racaEntity.getEspecie()), racaEntity.getNome()));
        }

        return racas;
    }
}
