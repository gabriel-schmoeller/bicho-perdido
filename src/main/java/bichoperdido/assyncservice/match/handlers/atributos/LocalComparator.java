package bichoperdido.assyncservice.match.handlers.atributos;

import bichoperdido.assyncservice.match.domain.Semelhanca;
import bichoperdido.business.animal.domain.Especie;

/**
 * @author Gabriel.
 */
public class LocalComparator extends NormalComparator {

    private static final int PESO = 2;
    private final double distanciaKm;
    private final Especie especie;

    public LocalComparator(double distanciaKm, Especie especie) {
        this.distanciaKm = distanciaKm;
        this.especie = especie;
    }

    @Override
    public Semelhanca compare() {
        double grau = 1.0;

        if (especie.equals(Especie.cachorro)) {
            if (distanciaKm >= 50) grau = .1;
            else if (distanciaKm >= 25) grau = .3;
            else if (distanciaKm >= 15) grau = .6;
            else if (distanciaKm >= 10) grau = .11;
            else if (distanciaKm >= 5) grau = .22;
            else if (distanciaKm >= 2) grau = .51;
        } else {
            if (distanciaKm >= 1.5) grau = .6;
            else if (distanciaKm >= .5) grau = .14;
            else if (distanciaKm >= .3) grau = .25;
            else if (distanciaKm >= .15) grau = .391;
        }

        return new Semelhanca(grau, PESO);
    }
}
