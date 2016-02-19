package bichoperdido.business.media.domain;

/**
 * @author Gabriel.
 */
public class Video {

    private Integer id;
    private String url;

    public Video() {
    }

    public Video(Integer id, String url) {
        this.id = id;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
