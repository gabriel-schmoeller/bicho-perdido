package bichoperdido.business.anuncio.domain;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Gabriel
 */
@JsonInclude(Include.NON_NULL)
public class AnuncioInput {

    private String natureza;
    private String especie;
    private Calendar datahora;
    private List<String> cores;
    private List<Peculiaridade> peculiaridades;
    private Local local;
    private String detalhes;
    private String genero;
    private String nome;
    private Integer raca;
    private String porte;
    private String pelagem;
    private String outras;

    public AnuncioInput() {
    }

    public AnuncioInput(String natureza, String especie, Calendar datahora, List<String> cores, List<Peculiaridade>
            peculiaridades, Local local, String detalhes, String genero, String nome, Integer raca, String porte, String pelagem,
            String outras) {
        this.natureza = natureza;
        this.especie = especie;
        this.datahora = datahora;
        this.cores = cores;
        this.peculiaridades = peculiaridades;
        this.local = local;
        this.detalhes = detalhes;
        this.genero = genero;
        this.nome = nome;
        this.raca = raca;
        this.porte = porte;
        this.pelagem = pelagem;
        this.outras = outras;
    }

    public AnuncioInput(List<Peculiaridade> peculiaridades, String detalhes, Integer raca, String outras) {
        this.peculiaridades = peculiaridades;
        this.detalhes = detalhes;
        this.raca = raca;
        this.outras = outras;
    }

    public void setNome(String nome) {
        this.nome = ((nome == null) ? "" : nome);
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = ((detalhes == null) ? "" : detalhes);
    }

    public void setOutras(String outras) {
        this.outras = ((outras == null) ? "" : outras);
    }

    public String getNatureza() {
        return natureza;
    }

    public String getEspecie() {
        return especie;
    }

    public Calendar getDatahora() {
        return datahora;
    }

    public List<String> getCores() {
        return cores;
    }

    public List<Peculiaridade> getPeculiaridades() {
        return peculiaridades;
    }

    public Local getLocal() {
        return local;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public String getGenero() {
        return genero;
    }

    public String getNome() {
        return nome;
    }

    public Integer getRaca() {
        return raca;
    }

    public String getPorte() {
        return porte;
    }

    public String getPelagem() {
        return pelagem;
    }

    public String getOutras() {
        return outras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnuncioInput anuncioInput = (AnuncioInput) o;
        return Objects.equals(raca, anuncioInput.raca) &&
                Objects.equals(natureza, anuncioInput.natureza) &&
                Objects.equals(especie, anuncioInput.especie) &&
                Objects.equals(datahora, anuncioInput.datahora) &&
                Objects.equals(cores, anuncioInput.cores) &&
                Objects.equals(peculiaridades, anuncioInput.peculiaridades) &&
                Objects.equals(local, anuncioInput.local) &&
                Objects.equals(detalhes, anuncioInput.detalhes) &&
                Objects.equals(genero, anuncioInput.genero) &&
                Objects.equals(nome, anuncioInput.nome) &&
                Objects.equals(porte, anuncioInput.porte) &&
                Objects.equals(pelagem, anuncioInput.pelagem) &&
                Objects.equals(outras, anuncioInput.outras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(natureza, especie, datahora, cores, peculiaridades, local, detalhes, genero, nome, raca, porte, pelagem, outras);
    }
}
