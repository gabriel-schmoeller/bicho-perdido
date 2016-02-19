package bichoperdido.assyncservice.match.handlers.atributos;

import bichoperdido.assyncservice.match.domain.Semelhanca;
import bichoperdido.persistence.entities.PeculiaridadeEntity;

import java.util.Collection;

/**
 * @author Gabriel.
 */
public class PeculiaridadeComparator extends BonusComparator {

    public static final int PESO = 1;
    private Collection<PeculiaridadeEntity> peculiaridadesA;
    private Collection<PeculiaridadeEntity> peculiaridadesB;

    public PeculiaridadeComparator(Collection<PeculiaridadeEntity> peculiaridadesA, Collection<PeculiaridadeEntity> peculiaridadesB) {
        this.peculiaridadesA = peculiaridadesA;
        this.peculiaridadesB = peculiaridadesB;
    }

    @Override
    public Semelhanca compare() {
        int peso = 0;

        for (PeculiaridadeEntity peculiaridadeA : peculiaridadesA) {
            for (PeculiaridadeEntity peculiaridadeB : peculiaridadesB) {
                if (peculiaridadeA.getTipoId().equals(peculiaridadeB.getTipoId())
                        && peculiaridadeA.getLocalId().equals(peculiaridadeB.getLocalId())) {
                    peso += PESO;
                }
            }
        }

        double grau = (peso > 0)? 1.0: .0;

        return new Semelhanca(grau, peso);
    }
}
