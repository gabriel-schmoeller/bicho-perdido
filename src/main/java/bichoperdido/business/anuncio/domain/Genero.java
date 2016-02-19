package bichoperdido.business.anuncio.domain;

/**
 * @author Gabriel
 */
public enum Genero {
    macho('m'),
    femea('f'),
    naosei('n');

    private Character dbValue;

    Genero(Character dbValue) {
        this.dbValue = dbValue;
    }

    public Character getDbValue() {
        return dbValue;
    }

    public static Genero valueOfByDb(Character dbValue) {
        for (Genero genero : values()) {
            if (genero.getDbValue().equals(dbValue)) {
                return genero;
            }
        }

        throw new IllegalArgumentException("Invalid db value.");
    }
}
