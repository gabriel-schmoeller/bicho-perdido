package bichoperdido.assyncservice.notify.domain;

/**
 * @author gabriel.schmoeller
 */
public class Request {

    private final String token;
    private final String notification;
    private int tries;

    public Request(String token, String notification) {
        this.token = token;
        this.notification = notification;
        this.tries = 0;
    }

    public String getToken() {
        return token;
    }

    public String getNotification() {
        return notification;
    }

    public int getTries() {
        return tries;
    }

    public int addTry() {
        return ++tries;
    }

    @Override
    public String toString() {
        return "Request{" +
                "token='" + token + '\'' +
                ", notification='" + notification + '\'' +
                ", tries=" + tries +
                '}';
    }
}
