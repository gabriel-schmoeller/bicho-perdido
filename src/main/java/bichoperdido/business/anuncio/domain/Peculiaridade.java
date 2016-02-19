package bichoperdido.business.anuncio.domain;

import java.util.Objects;

/**
 * @author Gabriel
 */
public class Peculiaridade {
    private Integer oQue;
    private Integer onde;

    public Peculiaridade() {
    }

    public Peculiaridade(int oQue, int onde) {
        this.oQue = oQue;
        this.onde = onde;
    }

    public Integer getoQue() {
        return oQue;
    }

    public Integer getOnde() {
        return onde;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peculiaridade that = (Peculiaridade) o;
        return Objects.equals(oQue, that.oQue) &&
                Objects.equals(onde, that.onde);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oQue, onde);
    }
}
