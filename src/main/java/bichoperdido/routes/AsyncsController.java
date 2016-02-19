package bichoperdido.routes;

import bichoperdido.assyncservice.match.MatchAsyncService;
import bichoperdido.assyncservice.match.MatchService;
import bichoperdido.assyncservice.match.domain.AnuncioSimilar;
import bichoperdido.assyncservice.notify.NotificationService;
import bichoperdido.persistence.repository.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * @author Gabriel.
 */
@RestController
public class AsyncsController extends DefaultController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchAsyncService asyncService;

    @RequestMapping("/public/user/notify/{token:.+}")
    public SseEmitter streamRegister(@PathVariable String token) {
        return notificationService.register(token);
    }

    @Async
    @RequestMapping("/public/user/notify/all/{message:.+}")
    public void streamTest(@PathVariable String message) {
        notificationService.sendToAll(message);
    }

    @RequestMapping("/public/poster/similar/fast/{id}/{nr}")
    public List<AnuncioSimilar> smilarFast(@PathVariable Integer id, @PathVariable Integer nr) {
        return matchService.calcuateFast(id, nr);
    }

    @RequestMapping("/public/poster/similar/reverse/{id}/{tamanho}/{pagina}")
    public SearchResult<AnuncioSimilar> similarReverse(@PathVariable Integer id,
            @PathVariable Integer tamanho, @PathVariable Integer pagina) {
        return matchService.getReverseCalculateds(id, tamanho, pagina);
    }

    @RequestMapping("/public/poster/similar/same/{id}/{tamanho}/{pagina}")
    public SearchResult<AnuncioSimilar> similarSame(@PathVariable Integer id,
            @PathVariable Integer tamanho, @PathVariable Integer pagina) {
        return matchService.getSamesCalculateds(id, tamanho, pagina);
    }

    @RequestMapping("/public/poster/match")
    public void match() {
        asyncService.doRequest();
    }
}
