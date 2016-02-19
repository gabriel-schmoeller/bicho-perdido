package bichoperdido.business.media.domain;

/**
 * @author Gabriel.
 */
public enum MediaTipo {
    IMAGEM('i'),
    VIDEO('v');


    private Character dbValue;

    MediaTipo(Character dbValue) {
        this.dbValue = dbValue;
    }

    public Character getDbValue() {
        return dbValue;
    }

    public static MediaTipo valueOfByDb(Character dbValue) {
        for (MediaTipo mediaTipo : values()) {
            if (mediaTipo.getDbValue().equals(dbValue)) {
                return mediaTipo;
            }
        }

        throw new IllegalArgumentException("Invalid db value.");
    }
}
