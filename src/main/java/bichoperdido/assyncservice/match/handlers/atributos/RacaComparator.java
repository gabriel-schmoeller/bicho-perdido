package bichoperdido.assyncservice.match.handlers.atributos;

import bichoperdido.assyncservice.match.domain.Semelhanca;

/**
 * @author Gabriel.
 */
public class RacaComparator extends BonusComparator {

    public static final int PESO = 3;
    private final int racaA;
    private final int racaB;

    public RacaComparator(int racaA, int racaB) {
        this.racaA = racaA;
        this.racaB = racaB;
    }

    @Override
    public Semelhanca compare() {
        double grau = (racaA == racaB)? 1.0: .0;

        return new Semelhanca(grau, PESO);
    }
}
