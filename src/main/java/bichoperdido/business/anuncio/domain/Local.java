package bichoperdido.business.anuncio.domain;

import java.util.Objects;

/**
 * @author Gabriel
 */
public class Local {

    private Coordenadas coordenadas;
    private String endereco;

    public Local() {
    }

    public Local(Coordenadas coordenadas, String endereco) {
        this.coordenadas = coordenadas;
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Local local = (Local) o;
        return Objects.equals(coordenadas, local.coordenadas) &&
                Objects.equals(endereco, local.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordenadas, endereco);
    }
}
