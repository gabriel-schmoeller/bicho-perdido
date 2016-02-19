package bichoperdido.business.anuncio.domain;

/**
 * @author Gabriel.
 */
public class Fronteiras {

    private Coordenadas nordeste;
    private Coordenadas sudoeste;

    public Fronteiras() {
    }

    public Fronteiras(Coordenadas nordeste, Coordenadas sudoeste) {
        this.nordeste = nordeste;
        this.sudoeste = sudoeste;
    }

    public Coordenadas getNordeste() {
        return nordeste;
    }

    public Coordenadas getSudoeste() {
        return sudoeste;
    }
}
