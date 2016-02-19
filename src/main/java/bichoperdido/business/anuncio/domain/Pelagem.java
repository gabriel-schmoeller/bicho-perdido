package bichoperdido.business.anuncio.domain;

/**
 * @author Gabriel
 */
public enum Pelagem {
    curta('c'),
    longa('l');

    private Character dbValue;

    Pelagem(Character dbValue) {
        this.dbValue = dbValue;
    }

    public Character getDbValue() {
        return dbValue;
    }

    public static Pelagem valueOfByDb(Character dbValue) {
        for (Pelagem pelagem : values()) {
            if (pelagem.getDbValue().equals(dbValue)) {
                return pelagem;
            }
        }

        throw new IllegalArgumentException("Invalid db value.");
    }
}
