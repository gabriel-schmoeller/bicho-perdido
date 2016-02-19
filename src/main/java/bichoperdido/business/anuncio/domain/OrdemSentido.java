package bichoperdido.business.anuncio.domain;

/**
 * @author Gabriel.
 */
public enum OrdemSentido {
    asc("asc"),
    desc("desc");

    private String dbValue;

    OrdemSentido(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }
}
