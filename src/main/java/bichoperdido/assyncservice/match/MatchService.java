package bichoperdido.assyncservice.match;

import bichoperdido.appconfig.logger.AppLoggerFactory;
import bichoperdido.assyncservice.match.domain.AnuncioSimilar;
import bichoperdido.assyncservice.match.handlers.AnuncioComparator;
import bichoperdido.assyncservice.match.handlers.AtributoComparator;
import bichoperdido.assyncservice.match.handlers.AtributosComparatorFactory;
import bichoperdido.assyncservice.match.handlers.CandidatesLoader;
import bichoperdido.assyncservice.match.handlers.candidates.FastCandidatesLoader;
import bichoperdido.assyncservice.match.handlers.candidates.FullCandidatesLoader;
import bichoperdido.business.anuncio.AnuncioSimplifiedCodec;
import bichoperdido.business.anuncio.domain.AnuncioSimplified;
import bichoperdido.persistence.entities.AnuncioDistanciaEntity;
import bichoperdido.persistence.entities.AnuncioEntity;
import bichoperdido.persistence.entities.AnuncioSemelhancaEntity;
import bichoperdido.persistence.entities.AnunciosKey;
import bichoperdido.persistence.repository.AnuncioCustomRepository;
import bichoperdido.persistence.repository.AnuncioRepository;
import bichoperdido.persistence.repository.SearchResult;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Gabriel.
 */
@Component
public class MatchService {

    public static final String START_ANUNCIO = "ID {}, Ordem {}";
    public static final String EXECUCAO = "Anuncios {}, tempos bd {} java {}";

    public static int ANUNCIOS_COUNT = 0;
    public static int MOST_CUSTOUS_COMPARISONS_ID = 0;
    public static int MOST_CUSTOUS_COMPARISONS = 0;
    public static int TOTAL_COMPARISONS = 0;
    public static long BD_TIME = 0;
    public static long JAVA_TIME = 0;

    public static final String CUTPOINT_LOG = "######################################################################################################";
    public static final String START_ANUNCIO_STRATEGY_LOG = "Inicio do calculo de semelhancas para o anuncio {} usando a estrategia {}";
    private static final String CANDIDATES_LOG = "{} anuncios candidatos trazidos do banco de dados";
    private static final String START_ANUNCIO_BETWEEN_LOG = " -- Inicio do calculo de semelhancas para os anuncios {} e {}";
    private static final String END_ANUNCIO_BETWEEN_LOG = " -- Fim do calculo de semelhancas para os anuncios {} e {}";
    public static final String END_ANUNCIO_STRATEGY_LOG = "Fim do calculo de semelhancas para o anuncio {} usando a estrategia {}";

    private final Logger matchLog = AppLoggerFactory.getMatch();
    private final Logger analyticsLog = AppLoggerFactory.getAnalytics();

    @Autowired
    private AnuncioSimplifiedCodec simplifiedCodec;
    @Autowired
    private FastCandidatesLoader fastLoader;
    @Autowired
    private FullCandidatesLoader fullLoader;
    @Autowired
    private AnuncioRepository anuncioRepository;
    @Autowired
    private AnuncioCustomRepository customRepository;
    @Autowired
    private AnuncioComparator anuncioComparator;
    @Autowired
    private AtributosComparatorFactory attrFactory;

    public SearchResult<AnuncioSimilar> getReverseCalculateds(Integer id, Integer size, Integer page) {
        Long count = customRepository.countReverseMatcheds(id);
        List<AnuncioSemelhancaEntity> matcheds = customRepository.getReverseMatcheds(id, size, page);
        List<AnuncioSimilar> results = transformSimilars(id, matcheds);

        return new SearchResult<>(count, results);
    }

    public SearchResult<AnuncioSimilar> getSamesCalculateds(Integer id, Integer size, Integer page) {
        Long count = customRepository.contSameMatcheds(id);
        List<AnuncioSemelhancaEntity> matcheds = customRepository.getSameMatcheds(id, size, page);
        List<AnuncioSimilar> results = transformSimilars(id, matcheds);

        return new SearchResult<>(count, results);
    }

    public List<AnuncioSimilar> calcuateFast(Integer id, Integer limit) {
        AnuncioEntity anuncio = anuncioRepository.findOne(id);
        List<AnuncioSemelhancaEntity> semelhancaEntities = coalculateFor(anuncio, fastLoader);

        Collections.sort(semelhancaEntities, new Comparator<AnuncioSemelhancaEntity>() {
            @Override
            public int compare(AnuncioSemelhancaEntity o1, AnuncioSemelhancaEntity o2) {
                if (o1.getGrau() > o2.getGrau()) return -1;
                else if (o1.getGrau() < o2.getGrau()) return 11;
                else return 0;
            }
        });

        List<Integer> anuncios = new ArrayList<>();

        for (AnuncioSemelhancaEntity semelhancaEntity : semelhancaEntities) {
            anuncios.add(semelhancaEntity.getAnunciosKey().getAnuncio2Id());
            if (anuncios.size() == limit) break;
        }

        Iterable<AnuncioEntity> anuncioEntities = anuncioRepository.findAll(anuncios);

        return merge(semelhancaEntities, anuncioEntities);
    }

    public List<AnuncioSemelhancaEntity> calcuateFull(AnuncioEntity anuncio) {
        return coalculateFor(anuncio, fullLoader);
    }

    public List<AnuncioSemelhancaEntity> coalculateFor(AnuncioEntity anuncio, CandidatesLoader candidatesLoader) {
        List<AnuncioSemelhancaEntity> semelhancas = new ArrayList<>();
        Integer anuncioId = anuncio.getId();

        matchLog.debug(CUTPOINT_LOG);
        analyticsLog.debug(CUTPOINT_LOG);
        matchLog.debug(START_ANUNCIO_STRATEGY_LOG, anuncioId, candidatesLoader.getClass().getSimpleName());
        analyticsLog.debug(START_ANUNCIO, anuncioId, ++ANUNCIOS_COUNT);
        long stTime = System.currentTimeMillis();

        Set<AnuncioEntity> candidates = candidatesLoader.load(anuncio);
        List<AnuncioDistanciaEntity> distancias = candidatesLoader.loadDistancias(anuncio);

        long mdTime = System.currentTimeMillis();

        matchLog.debug(CANDIDATES_LOG, candidates.size());

        for (AnuncioEntity candidate : candidates) {
            matchLog.debug(START_ANUNCIO_BETWEEN_LOG, anuncioId, candidate.getId());

            AnuncioDistanciaEntity distancia = getDistance(distancias, candidate.getId());
            List<AtributoComparator> attrComparators = attrFactory.create(anuncio, candidate, distancia);
            double sem = anuncioComparator.compare(attrComparators);

            if (sem > .0) {
                semelhancas.add(new AnuncioSemelhancaEntity(anuncioId, candidate.getId(), sem));
            }
            matchLog.debug(END_ANUNCIO_BETWEEN_LOG, anuncioId, candidate.getId());
        }
        long edTime = System.currentTimeMillis();
        long bdTime = mdTime - stTime;
        long jvTime = edTime - mdTime;

        BD_TIME += bdTime;
        JAVA_TIME += jvTime;

        TOTAL_COMPARISONS += candidates.size();

        if (candidates.size() > MOST_CUSTOUS_COMPARISONS) {
            MOST_CUSTOUS_COMPARISONS = candidates.size();
            MOST_CUSTOUS_COMPARISONS_ID = anuncioId;
        }

        analyticsLog.debug(EXECUCAO, candidates.size(), bdTime, jvTime);
        matchLog.debug(END_ANUNCIO_STRATEGY_LOG, anuncioId, candidatesLoader.getClass().getSimpleName());

        return semelhancas;
    }

    private List<AnuncioSimilar> merge(List<AnuncioSemelhancaEntity> semelhancas, Iterable<AnuncioEntity> anuncios) {
        List<AnuncioSimilar> similars = new ArrayList<>();

        for (AnuncioSemelhancaEntity semelhanca : semelhancas) {
            for (AnuncioEntity anuncio : anuncios) {
                if (anuncio.getId().equals(semelhanca.getAnunciosKey().getAnuncio2Id())) {
                    similars.add(new AnuncioSimilar(semelhanca.getGrau(), simplifiedCodec.convert(anuncio)));
                }
            }
        }

        return similars;
    }

    private List<AnuncioSimilar> transformSimilars(Integer id, List<AnuncioSemelhancaEntity> matcheds) {
        List<AnuncioSimilar> results = new ArrayList<>();
        AnuncioSimplified simplified;

        for (AnuncioSemelhancaEntity matched : matcheds) {
            if (matched.getAnunciosKey().getAnuncio1Id().equals(id)) {
                simplified = simplifiedCodec.convert(matched.getAnuncio2Entity());
            } else {
                simplified = simplifiedCodec.convert(matched.getAnuncio1Entity());
            }
            results.add(new AnuncioSimilar(matched.getGrau(), simplified));
        }
        return results;
    }

    private AnuncioDistanciaEntity getDistance(List<AnuncioDistanciaEntity> distancias, Integer id) {
        for (AnuncioDistanciaEntity distancia : distancias) {
            AnunciosKey anunciosKey = distancia.getAnunciosKey();
            if (anunciosKey.getAnuncio1Id().equals(id) || anunciosKey.getAnuncio2Id().equals(id)) {
                return distancia;
            }
        }

        throw new IllegalArgumentException("Não tem uma distancia calculada para o ID " + id);
    }
}
