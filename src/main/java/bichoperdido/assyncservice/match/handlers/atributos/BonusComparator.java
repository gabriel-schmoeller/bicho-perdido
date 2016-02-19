package bichoperdido.assyncservice.match.handlers.atributos;

import bichoperdido.assyncservice.match.domain.AtributoTipo;
import bichoperdido.assyncservice.match.handlers.AtributoComparator;

/**
 * @author Gabriel.
 */
public abstract class BonusComparator implements AtributoComparator {

    @Override
    public AtributoTipo getAtributoTipo() {
        return AtributoTipo.BONUS;
    }
}
