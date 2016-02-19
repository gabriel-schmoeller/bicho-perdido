package bichoperdido.assyncservice.match.handlers.atributos;

import bichoperdido.assyncservice.match.domain.Semelhanca;

/**
 * @author Gabriel.
 */
public class GeneroComparator extends ExclusivoComparator {

    public static final int PESO = 3;

    @Override
    public Semelhanca compare() {
        return new Semelhanca(1.0, PESO);
    }
}
