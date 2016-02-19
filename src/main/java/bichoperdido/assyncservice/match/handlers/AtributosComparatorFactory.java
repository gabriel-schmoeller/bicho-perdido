package bichoperdido.assyncservice.match.handlers;

import bichoperdido.assyncservice.match.handlers.atributos.*;
import bichoperdido.business.animal.domain.Especie;
import bichoperdido.business.anuncio.domain.Pelagem;
import bichoperdido.business.anuncio.domain.Porte;
import bichoperdido.business.cor.CorUtils;
import bichoperdido.business.cor.domain.CieLab;
import bichoperdido.persistence.entities.AnuncioDistanciaEntity;
import bichoperdido.persistence.entities.AnuncioEntity;
import bichoperdido.persistence.entities.CorEntity;
import bichoperdido.persistence.entities.PeculiaridadeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Gabriel.
 */
@Component
public class AtributosComparatorFactory {

    @Autowired
    private CorUtils corUtils;

    public List<AtributoComparator> create(AnuncioEntity anuncioA, AnuncioEntity anuncioB, AnuncioDistanciaEntity distancia) {
        List<AtributoComparator> atributoComparators = new ArrayList<>();

        atributoComparators.add(buildCorComparator(anuncioA, anuncioB));
        atributoComparators.add(new GeneroComparator());
        atributoComparators.add(new NomeComparator());
        atributoComparators.add(new MomentoComparator());
        atributoComparators.add(buildLocalComparator(anuncioA, distancia));
        atributoComparators.add(buildPelagemComparator(anuncioA, anuncioB));
        if (Especie.cachorro == Especie.valueOfByDb(anuncioA.getAnimalEntity().getEspecie())) {
            atributoComparators.add(buildPorteComparator(anuncioA, anuncioB));
        }
        atributoComparators.add(buildPeculiaridadeComparator(anuncioA, anuncioB));
        atributoComparators.add(buildRacaComparator(anuncioA, anuncioB));

        return atributoComparators;
    }

    private RacaComparator buildRacaComparator(AnuncioEntity anuncioA, AnuncioEntity anuncioB) {
        Integer racaA = anuncioA.getAnimalEntity().getRacaId();
        Integer racaB = anuncioB.getAnimalEntity().getRacaId();
        return new RacaComparator(racaA, racaB);
    }

    private PeculiaridadeComparator buildPeculiaridadeComparator(AnuncioEntity anuncioA, AnuncioEntity anuncioB) {
        Set<PeculiaridadeEntity> peculiaridadesA = anuncioA.getAnimalEntity().getPeculiaridadeEntities();
        Set<PeculiaridadeEntity> peculiaridadesB = anuncioB.getAnimalEntity().getPeculiaridadeEntities();
        return new PeculiaridadeComparator(peculiaridadesA, peculiaridadesB);
    }

    private PorteComparator buildPorteComparator(AnuncioEntity anuncioA, AnuncioEntity anuncioB) {
        Porte porteA = Porte.valueOfByDb(anuncioA.getAnimalEntity().getPorte());
        Porte porteB = Porte.valueOfByDb(anuncioB.getAnimalEntity().getPorte());
        return new PorteComparator(porteA, porteB);
    }

    private PelagemComparator buildPelagemComparator(AnuncioEntity anuncioA, AnuncioEntity anuncioB) {
        Pelagem pelagemA = Pelagem.valueOfByDb(anuncioA.getAnimalEntity().getPelagem());
        Pelagem pelagemB = Pelagem.valueOfByDb(anuncioB.getAnimalEntity().getPelagem());
        return new PelagemComparator(pelagemA, pelagemB);
    }

    private LocalComparator buildLocalComparator(AnuncioEntity anuncioA, AnuncioDistanciaEntity distancia) {
        Especie especie = Especie.valueOfByDb(anuncioA.getAnimalEntity().getEspecie());
        return new LocalComparator(distancia.getDistancia(), especie);
    }

    private CorComparator buildCorComparator(AnuncioEntity anuncioA, AnuncioEntity anuncioB) {
        List<CieLab> corA = toCieLabs(anuncioA.getAnimalEntity().getCores());
        List<CieLab> corB = toCieLabs(anuncioB.getAnimalEntity().getCores());
        return new CorComparator(corA, corB, corUtils);
    }

    private List<CieLab> toCieLabs(Collection<CorEntity> cores) {
        List<CieLab> corA = new ArrayList<>();
        for (CorEntity cor : cores) {
            corA.add(new CieLab(cor.getL(), cor.getA(), cor.getB()));
        }
        return corA;
    }
}
