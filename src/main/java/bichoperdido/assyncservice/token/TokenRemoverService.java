package bichoperdido.assyncservice.token;

import bichoperdido.business.authentication.service.TokenAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Gabriel
 */
@Component
public class TokenRemoverService implements Runnable {

    public static final int FIVE_MINUTES = 300000;

    @Autowired
    private TokenAuthService tokenService;

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while(true) {
            try {
                tokenService.removeExpiredTokens();
                Thread.sleep(FIVE_MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
