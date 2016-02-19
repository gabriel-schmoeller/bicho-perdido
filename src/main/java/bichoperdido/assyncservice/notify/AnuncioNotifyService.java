package bichoperdido.assyncservice.notify;

import bichoperdido.appconfig.logger.AppLoggerFactory;
import bichoperdido.assyncservice.notify.domain.Notification;
import bichoperdido.business.animal.domain.Especie;
import bichoperdido.business.anuncio.domain.Genero;
import bichoperdido.business.anuncio.domain.Natureza;
import bichoperdido.business.media.MediaService;
import bichoperdido.persistence.entities.AnuncioEntity;
import bichoperdido.persistence.entities.AnuncioSemelhancaEntity;
import bichoperdido.persistence.repository.AnuncioCustomRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gabriel.schmoeller
 */
@Component
public class AnuncioNotifyService {

    private static final String ANUNCIOS_NOTIFICADOS_ANUNCIO = "{} anuncios serao notificados sobre a insercao do anuncio {}";
    private static final String TOKENS_NOTIFICADOS_ANUNCIO = "{} tokens de usuarios serao notificados para o anuncio {}";
    private static final String PROTETORES_NOTIFICADOS = "Usuarios protetores ao qual o anuncio {} esteja dentro da area serao notificados";

    private final Logger notfyLog = AppLoggerFactory.getNotify();

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AnuncioCustomRepository customRepository;
    @Autowired
    private MediaService mediaService;

    public void notifySemelhantes(final AnuncioEntity anuncio, final List<AnuncioSemelhancaEntity> semelhantes) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                notfyLog.debug(ANUNCIOS_NOTIFICADOS_ANUNCIO, semelhantes.size(), anuncio.getId());

                for (AnuncioSemelhancaEntity semelhante : semelhantes) {
                    Notification message = buildNotification(anuncio, "semelhante", semelhante.getGrau());
                    List<String> tokens = customRepository.getTokensByAnuncio(semelhante.getAnunciosKey().getAnuncio2Id());
                    notfyLog.debug(TOKENS_NOTIFICADOS_ANUNCIO, tokens.size(), semelhante);

                    for (String token : tokens) {
                        notificationService.doRequest(token, message.toString());
                    }
                }
            }
        }).start();
    }

    public void notifyProtetores(final AnuncioEntity anuncio) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Notification message = buildNotification(anuncio, "protetor");
                notfyLog.debug(PROTETORES_NOTIFICADOS, anuncio.getId());

                List<String> tokens = customRepository.getProtetorsByAnuncio(anuncio.getId());
                notfyLog.debug(TOKENS_NOTIFICADOS_ANUNCIO, tokens.size(), anuncio.getId());
                for (String token : tokens) {
                    notificationService.doRequest(token, message.toString());
                }
            }
        }).start();
    }

    private Notification buildNotification(AnuncioEntity anuncio, String tipo, Double grau) {
        Integer anuncioId = anuncio.getId();
        String natureza = Natureza.valueOfByDb(anuncio.getNatureza()).toString();
        String genero = Genero.valueOfByDb(anuncio.getAnimalEntity().getGenero()).toString();
        String especie = Especie.valueOfByDb(anuncio.getAnimalEntity().getEspecie()).toString();
        String miniatura = mediaService.getAnuncioCapa(anuncioId);

        return new Notification(anuncioId, natureza, genero, especie, tipo, miniatura, grau);
    }

    private Notification buildNotification(AnuncioEntity anuncio, String tipo) {
        return buildNotification(anuncio, tipo, null);
    }
}
