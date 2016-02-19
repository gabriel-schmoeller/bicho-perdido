package bichoperdido.business.anuncio.domain;

import java.util.Objects;

/**
 * @author Gabriel
 */
public class PeculiaridadeOutput {
    private String oQue;
    private String onde;

    public PeculiaridadeOutput() {
    }

    public PeculiaridadeOutput(String oQue, String onde) {
        this.oQue = oQue;
        this.onde = onde;
    }

    public String getoQue() {
        return oQue;
    }

    public String getOnde() {
        return onde;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeculiaridadeOutput that = (PeculiaridadeOutput) o;
        return Objects.equals(oQue, that.oQue) &&
                Objects.equals(onde, that.onde);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oQue, onde);
    }
}
