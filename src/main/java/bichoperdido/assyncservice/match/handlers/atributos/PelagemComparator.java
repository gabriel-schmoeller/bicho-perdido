package bichoperdido.assyncservice.match.handlers.atributos;

import bichoperdido.assyncservice.match.domain.Semelhanca;
import bichoperdido.business.anuncio.domain.Pelagem;

/**
 * @author Gabriel.
 */
public class PelagemComparator extends NormalComparator {

    public static final int PESO = 1;
    private final Pelagem pelagemA;
    private final Pelagem pelagemB;

    public PelagemComparator(Pelagem pelagemA, Pelagem pelagemB) {
        this.pelagemA = pelagemA;
        this.pelagemB = pelagemB;
    }

    @Override
    public Semelhanca compare() {
        double grau = (pelagemA == pelagemB) ? 1.0 : 0.5;

        return new Semelhanca(grau, PESO);
    }
}
