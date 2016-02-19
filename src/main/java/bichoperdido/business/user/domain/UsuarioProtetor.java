package bichoperdido.business.user.domain;

import bichoperdido.business.anuncio.domain.Coordenadas;

/**
 * @author Gabriel.
 */
public class UsuarioProtetor {

    private Double raio;
    private Coordenadas centro;

    public UsuarioProtetor() {
    }

    public UsuarioProtetor(Double raio, Coordenadas centro) {
        this.raio = raio;
        this.centro = centro;
    }

    public Double getRaio() {
        return raio;
    }

    public Coordenadas getCentro() {
        return centro;
    }
}
