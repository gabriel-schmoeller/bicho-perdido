package bichoperdido.appconfig.error;

import bichoperdido.appconfig.exception.AuthenticationRequiredException;
import bichoperdido.business.authentication.exception.BadCredentialsException;
import bichoperdido.business.user.exception.EmailAlreadyExistsException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gabriel.
 */
@Component
public class ClientErrorHandler {

    private static final String CONTENT_TYPE = "application/json";

    public BpError buildMessage(Exception exception) {
        int bpError;
        String details;

        for (Map.Entry<Integer, Class> error : buildErrorsMap().entrySet()) {
            if (error.getValue().isInstance(exception)) {
                bpError = error.getKey();
                details = exception.getMessage();
                return new BpError(bpError, details);
            }
        }

        exception.printStackTrace();
        return getUnknownError(exception);
    }

    private BpError getUnknownError(Exception exception) {
        return new BpError(98, exception.getClass() + ": " + exception.getMessage());
    }

    private Map<Integer, Class> buildErrorsMap() {
        Map<Integer, Class> errorsMap = new HashMap<>();

        errorsMap.put(1, BadCredentialsException.class);
        errorsMap.put(2, AuthenticationRequiredException.class);
        errorsMap.put(3, EmailAlreadyExistsException.class);

        return errorsMap;
    }

    public String getContentType() {
        return CONTENT_TYPE;
    }
}
