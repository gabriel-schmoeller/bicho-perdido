package bichoperdido.assyncservice.notify;

import bichoperdido.appconfig.logger.AppLoggerFactory;
import bichoperdido.assyncservice.notify.domain.Request;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author gabriel.schmoeller
 */
@Component
public class NotificationService implements Runnable{

    private static final String REQUISICAO_TOKEN = "Requisicao '{}' requisitada para o token {}";
    private static final String NOTIFICACAO_ENVIADA = "Notificacao enviada para o {}";
    private static final String CONEXAO_FECHADA = "Conexao fechada para o token {}";
    private static final String TOKEN_NÃO_REGISTRADO = "Token {} não esta registrado ";
    private static final String REAGENDANDO = "Reagendando: {} para daqui a {} segundos";

    private final Logger notifyLog = AppLoggerFactory.getNotify();

    private final Timer timer = new Timer();
    private final LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<>();
    private final Map<String, SseEmitter> emitters = new HashMap<>();

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            try {
                sendOne(requests.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void doRequest(String token, String message) {
        notifyLog.debug(REQUISICAO_TOKEN, message, token);
        try {
            requests.put(new Request(token, message));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public SseEmitter register(String token) {
        SseEmitter emitter = createEmitter();
        emitters.put(token, emitter);

        return emitter;
    }

    public void sendToAll(String message) {
        for (String token : emitters.keySet()) {
            doRequest(token, message);
        }
    }

    private void sendOne(Request request) {
        if (emitters.containsKey(request.getToken())) {
            SseEmitter emitter = emitters.get(request.getToken());
            try {
                emitter.send(request.getNotification());
                notifyLog.debug(NOTIFICACAO_ENVIADA, request);
            } catch (Exception e) {
                emitter.complete();
                notifyLog.debug(CONEXAO_FECHADA, request);
                scheduleRetry(request);
            }
        } else {
            notifyLog.debug(TOKEN_NÃO_REGISTRADO, request.getToken());
            scheduleRetry(request);
        }
    }

    private void scheduleRetry(final Request request) {
        timer.purge();
        if (request.addTry() <= 5) {
            notifyLog.debug(REAGENDANDO, request, 3 * request.getTries());

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        requests.put(request);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, 3000*request.getTries());
        }
    }

    private SseEmitter createEmitter() {
        final SseEmitter emitter = new SseEmitter();

        emitter.onCompletion(new Runnable() {
            @Override
            public void run() {
                emitters.values().remove(emitter);
            }
        });

        return emitter;
    }
}
