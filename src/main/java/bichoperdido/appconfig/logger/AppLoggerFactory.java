package bichoperdido.appconfig.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gabriel.
 */
public class AppLoggerFactory {

    public static Logger getMatch() {
        return LoggerFactory.getLogger("match");
    }

    public static Logger getNotify() {
        return LoggerFactory.getLogger("notify");
    }

    public static Logger getAnalytics() {
        return LoggerFactory.getLogger("analytics");
    }
}
