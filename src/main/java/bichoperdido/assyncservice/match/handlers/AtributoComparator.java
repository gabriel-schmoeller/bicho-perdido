package bichoperdido.assyncservice.match.handlers;

import bichoperdido.assyncservice.match.domain.AtributoTipo;
import bichoperdido.assyncservice.match.domain.Semelhanca;

/**
 * @author Gabriel.
 */
public interface AtributoComparator {

    Semelhanca compare();

    AtributoTipo getAtributoTipo();
}
