package bichoperdido.assyncservice.match.domain;

import bichoperdido.business.anuncio.domain.AnuncioSimplified;

import java.util.Calendar;

/**
 * @author Gabriel.
 */
public class AnuncioSimilar {

    private Double semelhanca;
    private AnuncioSimplified anuncio;

    public AnuncioSimilar() {
    }

    public AnuncioSimilar(Double semelhanca, AnuncioSimplified anuncio) {
        this.semelhanca = semelhanca;
        this.anuncio = anuncio;
    }

    public Double getSemelhanca() {
        return semelhanca;
    }

    public Integer getId() {
        return anuncio.getId();
    }

    public String getNatureza() {
        return anuncio.getNatureza();
    }

    public Calendar getDatahora() {
        return anuncio.getDatahora();
    }

    public String getEndereco() {
        return anuncio.getEndereco();
    }

    public Calendar getResolvido() {
        return anuncio.getResolvido();
    }

    public String getStatus() {
        return anuncio.getStatus();
    }

    public String getNome() {
        return anuncio.getNome();
    }

    public String getMiniatura() {
        return anuncio.getMiniatura();
    }

    public String getGenero() {
        return anuncio.getGenero();
    }

    public String getEspecie() {
        return anuncio.getEspecie();
    }

    public Double getLatitude() {
        return anuncio.getLatitude();
    }

    public Double getLongitude() {
        return anuncio.getLongitude();
    }
}
