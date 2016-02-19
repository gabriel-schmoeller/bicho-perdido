package bichoperdido.appconfig.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Gabriel.
 */
public class BpError {

    private final int bpError;
    private final String details;

    public BpError(int bpError, String details) {
        this.bpError = bpError;
        this.details = details;
    }

    public int getBpError() {
        return bpError;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
