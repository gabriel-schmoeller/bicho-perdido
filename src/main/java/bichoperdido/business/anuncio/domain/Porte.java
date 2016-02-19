package bichoperdido.business.anuncio.domain;

/**
 * @author Gabriel
 */
public enum Porte {
    pequeno('p'),
    medio('m'),
    grande('g');

    private Character dbValue;

    Porte(Character dbValue) {
        this.dbValue = dbValue;
    }

    public Character getDbValue() {
        return dbValue;
    }

    public static Porte valueOfByDb(Character dbValue) {
        for (Porte porte : values()) {
            if (porte.getDbValue().equals(dbValue)) {
                return porte;
            }
        }

        throw new IllegalArgumentException("Invalid db value.");
    }
}
