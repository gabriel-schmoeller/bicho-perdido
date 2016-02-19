package bichoperdido.persistence.repository;

import java.util.List;

/**
 * @author Gabriel.
 */
public class SearchResult <T> {

    private final Long tamanho;
    private final List<T> resultados;

    public SearchResult(Long tamanho, List<T> resultados) {
        this.tamanho = tamanho;
        this.resultados = resultados;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public List<T> getResultados() {
        return resultados;
    }
}
