package bichoperdido.business.anuncio.domain;

/**
 * @author Gabriel.
 */
public enum OrdemCampo {
    nome("ani.nome"),
    dataHora("anu.dataHora");

    private String dbValue;

    OrdemCampo(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }
}
