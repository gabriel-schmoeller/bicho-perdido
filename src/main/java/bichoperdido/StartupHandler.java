package bichoperdido;

import bichoperdido.assyncservice.notify.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import bichoperdido.assyncservice.match.MatchAsyncService;
import bichoperdido.assyncservice.token.TokenRemoverService;
import bichoperdido.business.media.MediaUtils;

/**
 * @author Gabriel.
 */
@Component
public class StartupHandler implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MatchAsyncService matchAsyncService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private TokenRemoverService tokenRemoverService;
    @Autowired
    private MediaUtils mediaUtils;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        mediaUtils.buildMediaEnv();
        new Thread(tokenRemoverService).start();
        new Thread(notificationService).start();
        new Thread(matchAsyncService).start();
    }
}
