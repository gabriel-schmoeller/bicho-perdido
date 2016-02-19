package bichoperdido.assyncservice.match.domain;

import java.util.Objects;

/**
 * @author Gabriel.
 */
public class Semelhanca {

    private final double grau;
    private final int peso;

    public Semelhanca(double grau, int peso) {
        this.grau = grau;
        this.peso = peso;
    }

    public double getGrau() {
        return grau;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Semelhanca)) return false;
        Semelhanca semelhanca1 = (Semelhanca) o;
        return Objects.equals(grau, semelhanca1.grau) &&
                Objects.equals(peso, semelhanca1.peso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grau, peso);
    }

    @Override
    public String toString() {
        return "Semelhanca{" +
                "grau=" + grau +
                ", peso=" + peso +
                '}';
    }
}
