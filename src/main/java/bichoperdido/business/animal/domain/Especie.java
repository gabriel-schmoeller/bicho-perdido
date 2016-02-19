package bichoperdido.business.animal.domain;

/**
 * @author Gabriel.
 */
public enum Especie {
    cachorro('c'),
    gato('g');

    private Character dbValue;

    Especie(Character dbValue) {
        this.dbValue = dbValue;
    }

    public Character getDbValue() {
        return dbValue;
    }

    public static Especie valueOfByDb(Character dbValue) {
        for (Especie especie : values()) {
            if (especie.dbValue.equals(dbValue)) {
                return especie;
            }
        }

        throw new IllegalArgumentException("Invalid especie " + dbValue);
    }
}
