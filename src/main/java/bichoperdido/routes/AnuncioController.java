package bichoperdido.routes;

import bichoperdido.assyncservice.match.MatchAsyncService;
import bichoperdido.assyncservice.notify.AnuncioNotifyService;
import bichoperdido.business.animal.AnimalService;
import bichoperdido.business.animal.domain.Raca;
import bichoperdido.business.anuncio.AnuncioService;
import bichoperdido.business.anuncio.domain.AnuncioInput;
import bichoperdido.business.anuncio.domain.AnuncioOutput;
import bichoperdido.business.anuncio.domain.AnuncioSimplified;
import bichoperdido.business.anuncio.domain.BuscaFiltro;
import bichoperdido.business.authentication.service.UserAuthService;
import bichoperdido.persistence.repository.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel
 */
@RestController
public class AnuncioController extends DefaultController {

    @Autowired
    private AnuncioNotifyService notifyService;
    @Autowired
    private MatchAsyncService matchAsyncService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private AnuncioService anuncioService;
    @Autowired
    private UserAuthService userAuthService;

    @RequestMapping("/public/raca")
    public List<Raca> listRacas() {
        return animalService.getAllRacas();
    }

    @RequestMapping("/public/peculiaridade/local")
    public Map<Integer, String> peculiaridadeLocal() {
        return animalService.getAllPeculiaridadeLocal();
    }

    @RequestMapping("/public/peculiaridade/tipo")
    public Map<Integer, String> peculiaridadeTipo() {
        return animalService.getAllPeculiaridadeTipo();
    }

    @RequestMapping(value = "/private/poster/create", method = RequestMethod.POST)
    public Map<String, Integer> posterCreate(@RequestBody AnuncioInput anuncioInput) throws IOException {
        int anuncioId = anuncioService.createAnuncioAndNotify(anuncioInput);
        anuncioService.requestMatch();

        return Collections.singletonMap("id", anuncioId);
    }

    @RequestMapping(value = "/private/poster/edit/{id}", method = RequestMethod.POST)
    public void posterUpdate(@RequestBody AnuncioInput anuncioInput, @PathVariable int id) throws IOException {
        anuncioService.updateAnuncio(anuncioInput, id);
    }

    @RequestMapping(value = "/private/poster/edit/{id}", method = RequestMethod.GET)
    public AnuncioInput posterGetToUpdate(@PathVariable int id) throws IOException {
        return anuncioService.getEditableValues(id);
    }

    @RequestMapping("/private/poster/delete/{id}")
    public void posterRemove(@PathVariable int id) throws IOException {
        anuncioService.delete(id);
    }

    @RequestMapping("/private/poster/resolved/{id}")
    public void posterSetResolvido(@PathVariable int id) throws IOException {
        anuncioService.setResolvido(id);
    }

    @RequestMapping("/public/poster/{id}")
    public AnuncioOutput posterView(@PathVariable int id) throws IOException {
        return anuncioService.getById(id);
    }

    @RequestMapping("/private/poster/my")
    public List<AnuncioSimplified> posterRemove() throws IOException {
        Integer userId = userAuthService.getAuthUser().getId();

        return anuncioService.getByUser(userId);
    }

    @RequestMapping(value = "/public/poster/search", method = RequestMethod.POST)
    public SearchResult<AnuncioSimplified> searchMap(@RequestBody BuscaFiltro filtro) throws IOException {
        return anuncioService.search(filtro);
    }
}
