package bichoperdido.appconfig.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

/**
 * @author Gabriel
 */
@Component
public class EmberAwareObjectMapper extends ObjectMapper {

    public EmberAwareObjectMapper() {
        //indent the json output so it is easier to read
        configure(SerializationFeature.INDENT_OUTPUT, true);

        //Wite/Read dates as ISO Strings
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

}