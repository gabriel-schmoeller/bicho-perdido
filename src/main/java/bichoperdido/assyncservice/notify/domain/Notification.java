package bichoperdido.assyncservice.notify.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author gabriel.schmoeller
 */
public class Notification {

    private final Integer id;
    private final String natureza;
    private final String genero;
    private final String especie;
    private final String tipo;
    private final String miniatura;
    private final Double semelhanca;

    public Notification(Integer id, String natureza, String genero, String especie, String tipo, String miniatura) {
        this.id = id;
        this.natureza = natureza;
        this.genero = genero;
        this.especie = especie;
        this.tipo = tipo;
        this.miniatura = miniatura;
        semelhanca = null;
    }

    public Notification(Integer id, String natureza, String genero, String especie, String tipo, String miniatura, Double semelhanca) {
        this.id = id;
        this.natureza = natureza;
        this.genero = genero;
        this.especie = especie;
        this.tipo = tipo;
        this.miniatura = miniatura;
        this.semelhanca = semelhanca;
    }

    public Integer getId() {
        return id;
    }

    public String getNatureza() {
        return natureza;
    }

    public String getGenero() {
        return genero;
    }

    public String getEspecie() {
        return especie;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public Double getSemelhanca() {
        return semelhanca;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
