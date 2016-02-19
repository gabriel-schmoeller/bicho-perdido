package bichoperdido.business.media.domain;

/**
 * @author Gabriel.
 */
public class Imagem {

    private Integer id;
    private String imagem;
    private String miniatura;
    private Boolean capa;

    public Imagem() {
    }

    public Imagem(Integer id, String imagem, String miniatura, Boolean capa) {
        this.id = id;
        this.imagem = imagem;
        this.miniatura = miniatura;
        this.capa = capa;
    }

    public Integer getId() {
        return id;
    }

    public String getImagem() {
        return imagem;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public Boolean getCapa() {
        return capa;
    }
}
