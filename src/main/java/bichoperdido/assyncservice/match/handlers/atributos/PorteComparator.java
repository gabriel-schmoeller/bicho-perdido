package bichoperdido.assyncservice.match.handlers.atributos;

import bichoperdido.assyncservice.match.domain.Semelhanca;
import bichoperdido.business.anuncio.domain.Porte;

/**
 * @author Gabriel.
 */
public class PorteComparator extends NormalComparator {

    public static final int PESO = 2;
    private final Porte porteA;
    private final Porte porteB;

    public PorteComparator(Porte porteA, Porte porteB) {
        this.porteA = porteA;
        this.porteB = porteB;
    }

    @Override
    public Semelhanca compare() {
        double grau = .5;

        if (porteA == porteB) {
            grau = 1.0;
        } else if (porteA == Porte.grande && porteB == Porte.pequeno || porteB == Porte.grande && porteA == Porte.pequeno) {
            grau = .0;
        }

        return new Semelhanca(grau, PESO);
    }
}
