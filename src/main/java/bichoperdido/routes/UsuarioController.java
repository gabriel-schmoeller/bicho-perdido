package bichoperdido.routes;

import bichoperdido.business.authentication.domain.UserCredentials;
import bichoperdido.business.authentication.domain.UserIdentification;
import bichoperdido.business.authentication.exception.BadCredentialsException;
import bichoperdido.business.authentication.service.UserAuthService;
import bichoperdido.business.user.UserService;
import bichoperdido.business.user.domain.UsuarioInput;
import bichoperdido.business.user.domain.UsuarioProtetor;
import bichoperdido.business.user.exception.EmailAlreadyExistsException;
import bichoperdido.persistence.entities.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@RestController
public class UsuarioController extends DefaultController {

    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserService userService;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @RequestMapping(value = "/public/")
    public Map<String, List<String>> home() {
        Map<String, List<String>> routes = new TreeMap<>();

        for (RequestMappingInfo requestMappingInfo : requestMappingHandlerMapping.getHandlerMethods().keySet()) {
            String path = requestMappingInfo.getPatternsCondition().toString().replaceAll("[\\[\\]]", "");
            if (!routes.containsKey(path)) {
                routes.put(path, new ArrayList<String>());
            }

            String method = "GET";
            if (!requestMappingInfo.getMethodsCondition().isEmpty()) {
                method = requestMappingInfo.getMethodsCondition().toString().replaceAll("[\\[\\]]", "");
            }

            routes.get(path).add(method);
        }

        return routes;
    }

    @RequestMapping(value = "/public/user/create", method = RequestMethod.POST)
    public Map<String, Integer> createUser(@RequestBody UsuarioInput usuarioInput) throws EmailAlreadyExistsException {
        UsuarioEntity usuarioEntity = userService.create(usuarioInput);

        return Collections.singletonMap("id", usuarioEntity.getId());
    }

    @RequestMapping(value = "/public/login", method = RequestMethod.POST)
    public Map<String, String> login(@RequestBody UserCredentials userCredentials) throws BadCredentialsException {
        String token = userAuthService.authUser(userCredentials);

        return Collections.singletonMap("token", token);
    }

    @RequestMapping("/private/logout")
    public void logout() throws BadCredentialsException {
        userAuthService.logout();
    }

    @RequestMapping("/private/login/who")
    public UserIdentification loginWho() {
        return userAuthService.getAuthUser();
    }

    @RequestMapping("/private/user/protector")
    public UsuarioProtetor getProtector() {
        return userService.getProtetor(userAuthService.getAuthUser().getId());
    }

    @RequestMapping(value = "/private/user/protector", method = RequestMethod.POST)
    public void setProtector(@RequestBody UsuarioProtetor protetor) {
        userService.updateProtetor(protetor, userAuthService.getAuthUser().getId());
    }

    @RequestMapping(value = "/private/user/protector", method = RequestMethod.DELETE)
    public void removeProtector() {
        userService.removeProtector(userAuthService.getAuthUser().getId());
    }
}
