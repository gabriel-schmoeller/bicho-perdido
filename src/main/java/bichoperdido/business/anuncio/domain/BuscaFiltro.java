package bichoperdido.business.anuncio.domain;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gabriel.
 */
public class BuscaFiltro {

    private Calendar dataInicial;
    private Calendar dataFinal;
    private String natureza;
    private String especie;
    private String genero;
    private List<String> keywords;
    private Fronteiras fronteiras;
    private Integer paginaTamanho;
    private Integer paginaNumero;
    private String ordemCampo;
    private String ordemSentido;

    public BuscaFiltro() {
    }

    public BuscaFiltro(Calendar dataInicial, Calendar dataFinal, String natureza, String especie, String genero, List<String> keywords, Fronteiras fronteiras, Integer paginaTamanho, Integer paginaNumero, String ordemCampo, String ordemSentido) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.natureza = natureza;
        this.especie = especie;
        this.genero = genero;
        this.keywords = keywords;
        this.fronteiras = fronteiras;
        this.paginaTamanho = paginaTamanho;
        this.paginaNumero = paginaNumero;
        this.ordemCampo = ordemCampo;
        this.ordemSentido = ordemSentido;
    }

    public Calendar getDataInicial() {
        return dataInicial;
    }

    public Calendar getDataFinal() {
        return dataFinal;
    }

    public String getNatureza() {
        return natureza;
    }

    public String getEspecie() {
        return especie;
    }

    public String getGenero() {
        return genero;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public Fronteiras getFronteiras() {
        return fronteiras;
    }

    public Integer getPaginaTamanho() {
        return paginaTamanho;
    }

    public Integer getPaginaNumero() {
        return paginaNumero;
    }

    public String getOrdemCampo() {
        return ordemCampo;
    }

    public String getOrdemSentido() {
        return ordemSentido;
    }
}
