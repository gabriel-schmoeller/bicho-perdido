package bichoperdido.business.anuncio.domain;

/**
 * @author Gabriel
 */
public enum Natureza {
    perdido('p'),
    encontrado('e');

    private Character dbValue;

    Natureza(Character dbValue) {
        this.dbValue = dbValue;
    }

    public Character getDbValue() {
        return dbValue;
    }

    public static Natureza valueOfByDb(Character dbValue) {
        for (Natureza natureza : values()) {
            if (natureza.dbValue.equals(dbValue)) {
                return natureza;
            }
        }

        throw new IllegalArgumentException("Invalid dbValue");
    }
}
