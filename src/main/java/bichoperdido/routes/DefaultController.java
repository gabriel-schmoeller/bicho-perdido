package bichoperdido.routes;

import bichoperdido.appconfig.error.BpError;
import bichoperdido.appconfig.error.ClientErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Gabriel
 */
public class DefaultController {

    @Autowired
    private ClientErrorHandler errorHandler;

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public BpError handleError(Exception exception) {
        return errorHandler.buildMessage(exception);
    }
}
