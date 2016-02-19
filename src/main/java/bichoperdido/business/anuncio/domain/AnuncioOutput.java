package bichoperdido.business.anuncio.domain;

import bichoperdido.business.media.domain.Imagem;
import bichoperdido.business.media.domain.Video;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author Gabriel
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnuncioOutput {

    private String natureza;
    private String especie;
    private Calendar datahora;
    private List<String> cores;
    private List<PeculiaridadeOutput> peculiaridades;
    private Local local;
    private String detalhes;
    private String genero;
    private String nome;
    private String raca;
    private String porte;
    private String pelagem;
    private String outras;
    private List<Imagem> imagens;
    private List<Video> videos;
    private Boolean meu;
    private String usuario;
    private String email;
    private String telefone;

    public AnuncioOutput() {
    }

    public AnuncioOutput(String natureza, String especie, Calendar datahora, List<String> cores,
            List<PeculiaridadeOutput> peculiaridades, Local local, String detalhes,
            String genero, String nome, String raca, String porte, String pelagem,
            String outras, List<Imagem> imagens, List<Video> videos, Boolean meu, String usuario, String email, String telefone) {
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
        this.imagens = imagens;
        this.videos = videos;
        this.meu = meu;
        this.usuario = usuario;
        this.email = email;
        this.telefone = telefone;
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

    public List<PeculiaridadeOutput> getPeculiaridades() {
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

    public String getRaca() {
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

    public List<Imagem> getImagens() {
        return imagens;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public Boolean getMeu() {
        return meu;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnuncioOutput)) return false;
        AnuncioOutput that = (AnuncioOutput) o;
        return Objects.equals(natureza, that.natureza) &&
                Objects.equals(especie, that.especie) &&
                Objects.equals(datahora, that.datahora) &&
                Objects.equals(cores, that.cores) &&
                Objects.equals(peculiaridades, that.peculiaridades) &&
                Objects.equals(local, that.local) &&
                Objects.equals(detalhes, that.detalhes) &&
                Objects.equals(genero, that.genero) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(raca, that.raca) &&
                Objects.equals(porte, that.porte) &&
                Objects.equals(pelagem, that.pelagem) &&
                Objects.equals(outras, that.outras) &&
                Objects.equals(meu, that.meu) &&
                Objects.equals(videos, that.videos) &&
                Objects.equals(usuario, that.usuario) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(email, that.email) &&
                Objects.equals(imagens, that.imagens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(natureza, especie, datahora, cores, peculiaridades, local, detalhes, genero, nome,
                raca, porte, pelagem, outras, imagens, videos, meu, usuario, email, telefone);
    }
}
