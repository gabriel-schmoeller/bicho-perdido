package bichoperdido.business.animal.domain;

import java.util.Objects;

/**
 * @author Gabriel.
 */
public class Raca {

    private final int id;
    private final Especie especie;
    private final String nome;

    public Raca(int id, Especie especie, String nome) {
        this.id = id;
        this.especie = especie;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public Especie getEspecie() {
        return especie;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Raca)) return false;
        Raca raca = (Raca) o;
        return Objects.equals(id, raca.id) &&
                Objects.equals(especie, raca.especie) &&
                Objects.equals(nome, raca.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, especie, nome);
    }
}
