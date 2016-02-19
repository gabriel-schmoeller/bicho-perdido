package bichoperdido.business.media.domain;

/**
 * @author Gabriel.
 */
public class ExternalMedia {

    private Integer anuncio;
    private String url;

    public ExternalMedia() {
    }

    public ExternalMedia(Integer anuncio, String url) {
        this.anuncio = anuncio;
        this.url = url;
    }

    public Integer getAnuncio() {
        return anuncio;
    }

    public String getUrl() {
        return url;
    }
}
