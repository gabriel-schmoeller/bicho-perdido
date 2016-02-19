package bichoperdido.business.anuncio.domain;

/**
 * @author Gabriel
 */
public enum Status {
    aberto('a'),
    resolvido('r');


    private Character dbValue;

    Status(Character dbValue) {
        this.dbValue = dbValue;
    }

    public Character getDbValue() {
        return dbValue;
    }

    public static Status valueOfByDb(Character dbValue) {
        for (Status status : values()) {
            if (status.dbValue.equals(dbValue)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid DB value");
    }
}
