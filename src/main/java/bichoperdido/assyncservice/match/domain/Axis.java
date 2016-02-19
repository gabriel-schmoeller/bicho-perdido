package bichoperdido.assyncservice.match.domain;

import java.util.Objects;

/**
 * @author Gabriel.
 */
public class Axis {

    private final int x;
    private final int y;

    public Axis(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Axis)) return false;
        Axis axis = (Axis) o;
        return Objects.equals(x, axis.x) &&
                Objects.equals(y, axis.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
