package bichoperdido.business.cor.domain;

import java.util.Objects;

/**
 * @author Gabriel.
 */
public class Rgb {

    private final int r;
    private final int g;
    private final int b;

    public Rgb(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rgb)) return false;
        Rgb rgb = (Rgb) o;
        return Objects.equals(r, rgb.r) &&
                Objects.equals(g, rgb.g) &&
                Objects.equals(b, rgb.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }

    @Override
    public String toString() {
        return "Rgb{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
