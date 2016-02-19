package bichoperdido.assyncservice.match;

import bichoperdido.appconfig.logger.AppLoggerFactory;
import bichoperdido.assyncservice.notify.AnuncioNotifyService;
import bichoperdido.persistence.entities.AnuncioEntity;
import bichoperdido.persistence.entities.AnuncioSemelhancaEntity;
import bichoperdido.persistence.repository.AnuncioCustomRepository;
import bichoperdido.persistence.repository.AnuncioRepository;
import bichoperdido.persistence.repository.AnuncioSemelhancaRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static bichoperdido.assyncservice.match.MatchService.*;

/**
 * @author Gabriel.
 */
@Component
public class MatchAsyncService implements Runnable {

    private static final String SEMELHANCA_PERSISTIDA_LOG = "{} semelhancas persistidas para o anuncio {}";
    private static final String SEMELHANTES_NOTIFICADOS_LOG = "Anuncios semelhantes ao {} foram notificados";

    private static long FULL_MATCH_TIME = 0;
    private static int PERSISTIDOS = 0;
    private static int MOST_CUSTOUS_TIME_ID = 0;
    private static long MOST_CUSTOUS_TIME = 0;
    private static long LOAD_TIME = 0;
    private static long PERSIST_TIME = 0;
    private static final String END_COMPARE = "comparando {}, persistindo {} ({}), tudo {}";
    private static final String PIOR_ALL = "pior tempo {} ID {}, pior comparacoes {} ID {}";
    private static final String END_TOTAL_ALL =
            "comparacoes: {}, persistencias {}, tempos: carregando {}, BD {}, Java {}, persist {}, total {}";
    private static final String END = PIOR_ALL
            + "\n                          " + END_TOTAL_ALL;

    private final Logger matchLog = AppLoggerFactory.getMatch();
    private final Logger notfyLog = AppLoggerFactory.getNotify();
    private final Logger analyticsLog = AppLoggerFactory.getAnalytics();

    @Autowired
    private AnuncioNotifyService notifyService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private AnuncioSemelhancaRepository semelhancaRepository;
    @Autowired
    private AnuncioCustomRepository anuncioCustomRepository;
    @Autowired
    private AnuncioRepository anuncioRepository;

    private final AtomicBoolean request = new AtomicBoolean(true);

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while(true) {
            try {
                if (request.getAndSet(false)) {
                    long stFuTime = System.currentTimeMillis();
                    Set<AnuncioEntity> notMatcheds = anuncioCustomRepository.getNotMatcheds();

                    long mdFuTime = System.currentTimeMillis();
                    LOAD_TIME += mdFuTime - stFuTime;

                    for (AnuncioEntity anuncio : notMatcheds) {
                        long stTime = System.currentTimeMillis();
                        List<AnuncioSemelhancaEntity> semelhancaEntities = matchService.calcuateFull(anuncio);
                        Integer anuncioId = anuncio.getId();

                        long mdTime = System.currentTimeMillis();
                        long mtTime = mdTime - stTime;
                        int persist = semelhancaEntities.size();
                        PERSISTIDOS += persist;

                        anuncioCustomRepository.saveSemelhancaBatch(semelhancaEntities);
                        semelhancaRepository.save(semelhancaEntities);
                        anuncioRepository.updateCompared(true, anuncioId);
                        matchLog.debug(SEMELHANCA_PERSISTIDA_LOG, semelhancaEntities.size(), anuncioId);
                        long edTime = System.currentTimeMillis();

                        long psTime = edTime - mdTime;
                        PERSIST_TIME += psTime;

                        long fmTime = edTime - stTime;

                        if (fmTime > MOST_CUSTOUS_TIME) {
                            MOST_CUSTOUS_TIME = fmTime;
                            MOST_CUSTOUS_TIME_ID = anuncioId;
                        }

                        analyticsLog.debug(END_COMPARE, mtTime, psTime, persist, fmTime);

                        notifyService.notifySemelhantes(anuncio, semelhancaEntities);
                        notfyLog.debug(SEMELHANTES_NOTIFICADOS_LOG, anuncioId);
                    }

                    long edFuTime = System.currentTimeMillis();
                    FULL_MATCH_TIME += edFuTime - stFuTime;

                    analyticsLog.debug(END, MOST_CUSTOUS_TIME, MOST_CUSTOUS_TIME_ID,
                            MOST_CUSTOUS_COMPARISONS, MOST_CUSTOUS_COMPARISONS_ID, TOTAL_COMPARISONS, PERSISTIDOS, LOAD_TIME,
                            BD_TIME, JAVA_TIME, PERSIST_TIME, (LOAD_TIME + FULL_MATCH_TIME));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void doRequest() {
        request.set(true);
    }
}
