package bichoperdido.business.cor.domain;

import java.util.Objects;

/**
 * @author Gabriel.
 */
public class CieLab {

    private final double l;
    private final double a;
    private final double b;

    public CieLab(double l, double a, double b) {
        this.l = l;
        this.a = a;
        this.b = b;
    }

    public double getL() {
        return l;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CieLab)) return false;
        CieLab cieLab = (CieLab) o;
        return Objects.equals(l, cieLab.l) &&
                Objects.equals(a, cieLab.a) &&
                Objects.equals(b, cieLab.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(l, a, b);
    }

    @Override
    public String toString() {
        return "CieLab{" +
                "l=" + l +
                ", a=" + a +
                ", b=" + b +
                '}';
    }
}
