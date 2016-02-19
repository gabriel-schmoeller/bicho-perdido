package bichoperdido.business.anuncio.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Calendar;

/**
 * @author Gabriel
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnuncioSimplified {

    private Integer id;
    private String natureza;
    private Calendar datahora;
    private String endereco;
    private String genero;
    private String especie;
    private Calendar resolvido;
    private String status;
    private String nome;
    private String miniatura;
    private Double latitude;
    private Double longitude;

    public AnuncioSimplified() {
    }

    public AnuncioSimplified(Integer id, String natureza, Calendar datahora, String endereco, String genero, String especie, Calendar resolvido, String status, String nome, String miniatura, Double latitude, Double longitude) {
        this.id = id;
        this.natureza = natureza;
        this.datahora = datahora;
        this.endereco = endereco;
        this.genero = genero;
        this.especie = especie;
        this.resolvido = resolvido;
        this.status = status;
        this.nome = nome;
        this.miniatura = miniatura;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public String getNatureza() {
        return natureza;
    }

    public Calendar getDatahora() {
        return datahora;
    }

    public String getEndereco() {
        return endereco;
    }

    public Calendar getResolvido() {
        return resolvido;
    }

    public String getStatus() {
        return status;
    }

    public String getNome() {
        return nome;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public String getGenero() {
        return genero;
    }

    public String getEspecie() {
        return especie;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
