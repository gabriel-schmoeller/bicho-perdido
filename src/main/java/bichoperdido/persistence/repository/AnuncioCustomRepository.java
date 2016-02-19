package bichoperdido.persistence.repository;

import bichoperdido.business.animal.domain.Especie;
import bichoperdido.business.anuncio.domain.*;
import bichoperdido.persistence.entities.AnuncioDistanciaEntity;
import bichoperdido.persistence.entities.AnuncioEntity;
import bichoperdido.persistence.entities.AnuncioSemelhancaEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * @author Gabriel.
 */
@Component
public class AnuncioCustomRepository {

    private static final String WHERE = "\n WHERE ";
    private static final String WHERE_CAPA = "\n WHERE ";
    private static final String ORDER_BY = " ORDER BY %s %s";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String USUARIO_ID = "usuarioId";
    private String SELECT_DISTANCIA = "SELECT dist FROM AnuncioDistanciaEntity dist \n" +
            "WHERE (dist.anunciosKey.anuncio1Id = :" + ID + " " +
            "AND dist.anunciosKey.anuncio2Id IN (%s)) " +
            "OR (dist.anunciosKey.anuncio2Id = :" + ID + " " +
            "AND dist.anunciosKey.anuncio1Id IN (%s)) ";
    private static final String SEMELHANCA_SIMP = "SELECT a FROM AnuncioSemelhancaEntity a " +
            "  INNER JOIN FETCH a.anuncio1Entity anu1 " +
            "  INNER JOIN FETCH anu1.animalEntity ani1 " +
            "  LEFT JOIN FETCH anu1.mediaEntity med1 " +
            "  INNER JOIN FETCH a.anuncio2Entity anu2 " +
            "  INNER JOIN FETCH anu2.animalEntity ani2 " +
            "  LEFT JOIN FETCH anu2.mediaEntity med2 " +
            "WHERE (a.anunciosKey.anuncio1Id = :" + ID + " OR a.anunciosKey.anuncio2Id = :" + ID + ") " +
            "  AND (med1.capa=true OR med1.capa IS NULL) " +
            "  AND (med2.capa=true OR med2.capa IS NULL) ";
    private static final String DATA_HORA = "dataHora";
    private static final String DATA_INICIAL = "dataInicial";
    private static final String DATA_FINAL = "dataFinal";
    private static final String NATUREZA = "natureza";
    private static final String ESPECIE = "especie";
    private static final String GENERO = "genero";
    private static final String OESTE_LONGITUDE = "oesteLongitude";
    private static final String LESTE_LONGITUDE = "lesteLongitude";
    private static final String NORTE_LATITUDE = "norteLatitude";
    private static final String SUL_LATITUDE = "sulLatitude";
    private static final String OR = " OR ";
    private static final String AND = " AND ";
    private static final String KEY_WD = "keyWd%s";
    public static final String SELECT_SIMPL =
            "SELECT anu FROM AnuncioEntity anu " +
                "  INNER JOIN FETCH anu.animalEntity ani " +
                "  LEFT JOIN FETCH anu.mediaEntity med ";
    public static final String SELECT_NOT_MEDIA =
            "SELECT anu FROM AnuncioEntity anu " +
                "  INNER JOIN FETCH anu.animalEntity ani " +
                "  INNER JOIN FETCH anu.usuarioEntity usr " +
                "  LEFT JOIN FETCH ani.cores cor " +
                "  LEFT JOIN FETCH ani.peculiaridadeEntities pecu " +
                "  LEFT JOIN FETCH pecu.localEntity pecu_l " +
                "  LEFT JOIN FETCH pecu.tipoEntity pecu_t " +
                "  LEFT JOIN FETCH ani.racaEntity rca ";
    public static final String SELECT = SELECT_NOT_MEDIA + "  LEFT JOIN FETCH anu.mediaEntity med ";
    public static final String COUNT = "SELECT count(anu.id) FROM AnuncioEntity anu \nINNER JOIN anu.animalEntity AS ani" +
            " \nINNER JOIN ani.racaEntity AS rca LEFT JOIN anu.mediaEntity med ";

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @SuppressWarnings("unchecked")
    public List<String> getProtetorsByAnuncio(Integer anuncio) {
        String hql = "SELECT token FROM usuario_protetor AS up \n" +
                "INNER JOIN usuario ON up.usuario_id = usuario.id \n" +
                "INNER JOIN usuario_token ON usuario_token.usuario_id = usuario.id \n" +
                "INNER JOIN anuncio AS a ON a.usuario_id <> usuario.id \n" +
                "WHERE a.id = :"+ID+" \n" +
                "AND ((point(a.local_longitude, a.local_latitude) <@> point(up.longitude, up.latitude)) * 1.609344) < up.raio";

        Query query = entityManager.createNativeQuery(hql);
        query.setParameter(ID, anuncio);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<String> getTokensByAnuncio(Integer anuncio) {
        String hql = "SELECT token FROM usuario \n" +
                "INNER JOIN usuario_token ON usuario_token.usuario_id = usuario.id \n" +
                "INNER JOIN anuncio ON anuncio.usuario_id = usuario.id \n" +
                "WHERE anuncio.id = :"+ID;

        Query query = entityManager.createNativeQuery(hql);
        query.setParameter(ID, anuncio);

        return query.getResultList();
    }

    public Set<AnuncioEntity> getAllCandidates(Integer anuncioId, String nome, Especie especie, Genero genero, Calendar dataHora, Natureza natureza, Integer usuarioId) {
        String hql = SELECT_NOT_MEDIA + WHERE;
        hql += buildFilters(nome, genero, natureza);

        TypedQuery<AnuncioEntity> query = entityManager.createQuery(hql, AnuncioEntity.class);
        setParameters(query, anuncioId, nome, especie, genero, dataHora, usuarioId);

        return new LinkedHashSet<>(query.getResultList());
    }

    public List<AnuncioDistanciaEntity> getDistanciasAllCandidates(Integer anuncioId, String nome, Especie especie, Genero genero, Calendar dataHora,
            Natureza natureza, Integer usuarioId) {
        String candHql = "SELECT anu.id FROM AnuncioEntity anu INNER JOIN anu.animalEntity ani "
                + WHERE + buildFilters(nome, genero, natureza);
        String hql = String.format(SELECT_DISTANCIA, candHql, candHql);

        TypedQuery<AnuncioDistanciaEntity> query = entityManager.createQuery(hql, AnuncioDistanciaEntity.class);
        setParameters(query, anuncioId, nome, especie, genero, dataHora, usuarioId);

        return query.getResultList();
    }

    public Set<AnuncioEntity> getFastCandidates(String nome, Especie especie, Genero genero, Calendar dataHora,
            Natureza natureza, List<Integer> candidates, Integer usuarioId) {
        String hql = SELECT + WHERE;
        hql += buildFiltersFast(nome, genero, natureza);

        TypedQuery<AnuncioEntity> query = entityManager.createQuery(hql, AnuncioEntity.class);
        setParametersFast(query, candidates, nome, especie, genero, dataHora, natureza, usuarioId);

        return new LinkedHashSet<>(query.getResultList());
    }

    private void setParameters(TypedQuery<?> query, Integer anuncioId, String nome, Especie especie, Genero genero, Calendar dataHora, Integer usuarioId) {
        query.setParameter(ID, anuncioId);
        query.setParameter(ESPECIE, especie.getDbValue());
        if (!nome.isEmpty()) {
            query.setParameter(NOME, nome);
        }
        query.setParameter(DATA_HORA, dataHora);

        if (genero != Genero.naosei) {
            query.setParameter(GENERO, genero.getDbValue());
        }
        query.setParameter(USUARIO_ID, usuarioId);
    }

    private void setParametersFast(TypedQuery<AnuncioEntity> query, List<Integer> candidates, String nome, Especie especie,
            Genero genero, Calendar dataHora, Natureza natureza, Integer usuarioId) {
        query.setParameter(ID, candidates);
        query.setParameter(ESPECIE, especie.getDbValue());
        if (!nome.isEmpty()) {
            query.setParameter(NOME, nome);
        }
        query.setParameter(DATA_HORA, dataHora);
        query.setParameter(NATUREZA, natureza.getDbValue());

        if (genero != Genero.naosei) {
            query.setParameter(GENERO, genero.getDbValue());
        }
        query.setParameter(USUARIO_ID, usuarioId);
    }

    public SearchResult<AnuncioEntity> search(BuscaFiltro filtro) {
        Long size = countSearchResults(filtro);
        List<AnuncioEntity> resultList = executeSearch(filtro);

        return new SearchResult<>(size, resultList);
    }

    private Long countSearchResults(BuscaFiltro filtro) {
        String hql = COUNT + buildWhere(filtro);
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        setParameters(query, filtro);

        return query.getSingleResult();
    }

    private List<AnuncioEntity> executeSearch(BuscaFiltro filtro) {
        String hql = buildQuery(filtro);
        TypedQuery<AnuncioEntity> query = entityManager.createQuery(hql, AnuncioEntity.class);
        setParameters(query, filtro);

        if (filtro.getPaginaNumero() != null && filtro.getPaginaTamanho() != null) {
            query.setFirstResult(((filtro.getPaginaNumero()-1)*filtro.getPaginaTamanho()));
            query.setMaxResults(filtro.getPaginaTamanho());
        }

        return query.getResultList();
    }

    private String buildFilters(String nome, Genero genero, Natureza natureza) {
        List<String> filters = new ArrayList<>();

        filters.add("anu.status = 'a'");
        filters.add("anu.id < :" + ID );
        filters.add("ani.especie = :" + ESPECIE);
        if (genero != Genero.naosei) {
            filters.add("(ani.genero = 'n' OR ani.genero = :" + GENERO + ")");
        }
        if (!nome.isEmpty()) {
            filters.add("(ani.nome = '' OR ani.nome = :" + NOME + ")");
        }
        if (natureza == Natureza.encontrado) {
            filters.add("(anu.natureza = 'e' OR (anu.natureza = 'p' AND anu.dataHora < :" + DATA_HORA + "))");
        } else {
            filters.add("(anu.natureza = 'p' OR (anu.natureza = 'e' AND anu.dataHora > :" + DATA_HORA + "))");
        }
        filters.add("anu.usuarioId <> :" + USUARIO_ID);

        return joiner("\n" + AND, filters);
    }

    private String buildFiltersFast(String nome, Genero genero, Natureza natureza) {
        List<String> filters = new ArrayList<>();

        filters.add("anu.status = 'a'");
        filters.add("anu.id IN (:" + ID + ")");
        filters.add("ani.especie = :" + ESPECIE);
        if (genero != Genero.naosei) {
            filters.add("(ani.genero = 'n' OR ani.genero = :" + GENERO + ") ");
        }

        filters.add("anu.natureza <> :" + NATUREZA);

        if (!nome.isEmpty()) {
            filters.add("(ani.nome = '' OR ani.nome = :" + NOME + ")");
        }

        if (natureza == Natureza.encontrado) {
            filters.add("anu.dataHora < :" + DATA_HORA);
        } else {
            filters.add("anu.dataHora > :" + DATA_HORA);
        }

        filters.add("anu.usuarioId <> :" + USUARIO_ID);

        return joiner("\n" + AND, filters);
    }

    String buildQuery(BuscaFiltro filtro) {
        String hql = SELECT + buildWhere(filtro);

        String campo;
        String sentido;
        if (filtro.getOrdemCampo() != null && filtro.getOrdemSentido() != null) {
            campo = OrdemCampo.valueOf(filtro.getOrdemCampo()).getDbValue();
            sentido = OrdemSentido.valueOf(filtro.getOrdemSentido()).getDbValue();
        } else {
            campo = OrdemCampo.dataHora.getDbValue();
            sentido = OrdemSentido.desc.getDbValue();
        }

        hql += String.format(ORDER_BY, campo, sentido);

        return hql;
    }

    private String buildWhere(BuscaFiltro filtro) {
        List<String> filters = buildFilters(filtro);

        return WHERE_CAPA + joiner("\n" + AND, filters);
    }

    private List<String> buildFilters(BuscaFiltro filtro) {
        List<String> filters = new ArrayList<>();
        filters.add("(med.capa=true OR med.capa IS NULL)");
        filters.add("anu.status = 'a'");

        if (filtro.getDataInicial() != null && filtro.getDataFinal() != null) {
            filters.add("anu.dataHora between :" + DATA_INICIAL + " AND :" + DATA_FINAL);
        } else if (filtro.getDataInicial() != null) {
            filters.add("anu.dataHora > :" + DATA_INICIAL);
        } else if (filtro.getDataFinal() != null) {
            filters.add("anu.dataHora < :" + DATA_FINAL);
        }
        if (filtro.getNatureza() != null) {
            filters.add("anu.natureza = :" + NATUREZA);
        }
        if (filtro.getEspecie() != null) {
            filters.add("ani.especie = :" + ESPECIE);
        }
        if (filtro.getGenero() != null) {
            filters.add("(ani.genero = :" + GENERO + " OR ani.genero = 'n')");
        }
        if (filtro.getFronteiras() != null) {
            filters.add("anu.localLongitude between :" + OESTE_LONGITUDE + " AND :" + LESTE_LONGITUDE);
            filters.add("anu.localLatitude between :" + SUL_LATITUDE + " AND :" + NORTE_LATITUDE);
        }
        if (filtro.getKeywords() != null && !filtro.getKeywords().isEmpty()) {
            Collection<String> keyOr = new ArrayList<>();

            for (int i = 0; i < filtro.getKeywords().size(); i++) {
                List<String> keyAnd = new ArrayList<>();
                String namedParam = String.format(KEY_WD, i);

                keyAnd.add("lower(anu.localEndereco) LIKE lower(:" + namedParam + ")");
                keyAnd.add("lower(anu.detalhes) LIKE lower(:" + namedParam + ")");
                keyAnd.add("lower(ani.nome) LIKE lower(:" + namedParam + ")");
                keyAnd.add("lower(ani.outros) LIKE lower(:" + namedParam + ")");
                keyAnd.add("lower(rca.nome) LIKE lower(:" + namedParam + ")");

                keyOr.add("(" + joiner(OR, keyAnd) + ")");
            }

            filters.add("(" + joiner("\n" + AND, keyOr) + ")");
        }

        return filters;
    }

    private void setParameters(TypedQuery<?> query, BuscaFiltro filtro) {
        if (filtro.getDataInicial() != null) {
            Calendar dataInicial = filtro.getDataInicial();
            dataInicial.set(Calendar.HOUR, 0);
            dataInicial.set(Calendar.MINUTE, 0);
            dataInicial.set(Calendar.SECOND, 0);
            dataInicial.set(Calendar.MILLISECOND, 0);

            query.setParameter(DATA_INICIAL, dataInicial);
        }
        if (filtro.getDataFinal() != null) {
            Calendar dataFinal = filtro.getDataFinal();
            dataFinal.set(Calendar.HOUR, 23);
            dataFinal.set(Calendar.MINUTE, 59);
            dataFinal.set(Calendar.SECOND, 59);
            dataFinal.set(Calendar.MILLISECOND, 999);

            query.setParameter(DATA_FINAL, dataFinal);
        }
        if (filtro.getNatureza() != null) {
            query.setParameter(NATUREZA, Natureza.valueOf(filtro.getNatureza()).getDbValue());
        }
        if (filtro.getEspecie() != null) {
            query.setParameter(ESPECIE, Especie.valueOf(filtro.getEspecie()).getDbValue());
        }
        if (filtro.getGenero() != null) {
            query.setParameter(GENERO, Genero.valueOf(filtro.getGenero()).getDbValue());
        }
        if (filtro.getFronteiras() != null) {
            query.setParameter(OESTE_LONGITUDE, filtro.getFronteiras().getSudoeste().getLongitude());
            query.setParameter(LESTE_LONGITUDE, filtro.getFronteiras().getNordeste().getLongitude());
            query.setParameter(SUL_LATITUDE, filtro.getFronteiras().getSudoeste().getLatitude());
            query.setParameter(NORTE_LATITUDE, filtro.getFronteiras().getNordeste().getLatitude());
        }
        if (filtro.getKeywords() != null) {
            for (int i = 0; i < filtro.getKeywords().size(); i++) {
                query.setParameter(String.format(KEY_WD, i), "%" + filtro.getKeywords().get(i) + "%");
            }
        }
    }

    private String joiner(String sep, Iterable<?> pieces) {
        boolean first = true;
        StringBuilder join = new StringBuilder();

        for (Object piece : pieces) {
            if (first) {
                first = false;
            } else {
                join.append(sep);
            }

            join.append(piece.toString());
        }

        return join.toString();
    }

    public Set<AnuncioEntity> getNotMatcheds() {
        String hql = SELECT_NOT_MEDIA + " WHERE anu.compared = false ORDER BY anu.id ASC";

        TypedQuery<AnuncioEntity> query = entityManager.createQuery(hql, AnuncioEntity.class);

        return new LinkedHashSet<>(query.getResultList());
    }

    public List<AnuncioSemelhancaEntity> getReverseMatcheds(Integer id, Integer size, Integer page) {
        String hql = SEMELHANCA_SIMP +
                "  AND anu1.natureza <> anu2.natureza " +
                "ORDER BY a.grau DESC";

        TypedQuery<AnuncioSemelhancaEntity> query = entityManager.createQuery(hql, AnuncioSemelhancaEntity.class);
        query.setParameter(ID, id);

        if (size != null && page != null) {
            query.setFirstResult(((page-1)*size));
            query.setMaxResults(size);
        }

        return query.getResultList();
    }

    public List<AnuncioSemelhancaEntity> getSameMatcheds(Integer id, Integer size, Integer page) {
        String hql = SEMELHANCA_SIMP +
                    "  AND anu1.natureza = anu2.natureza " +
                    "ORDER BY a.grau DESC";

        TypedQuery<AnuncioSemelhancaEntity> query = entityManager.createQuery(hql, AnuncioSemelhancaEntity.class);
        query.setParameter(ID, id);

        if (size != null && page != null) {
            query.setFirstResult(((page-1)*size));
            query.setMaxResults(size);
        }

        return query.getResultList();
    }

    public Long countReverseMatcheds(Integer id) {
        String hql = "SELECT count(a.grau) FROM AnuncioSemelhancaEntity a " +
                "WHERE (a.anunciosKey.anuncio1Id = :id OR a.anunciosKey.anuncio2Id = :id) " +
                "AND a.anuncio1Entity.natureza <> a.anuncio2Entity.natureza";

        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        query.setParameter(ID, id);

        return query.getSingleResult();
    }

    public Long contSameMatcheds(Integer id) {
        String hql = "SELECT count(a.grau) FROM AnuncioSemelhancaEntity a " +
                "WHERE (a.anunciosKey.anuncio1Id = :"+ID+" OR a.anunciosKey.anuncio2Id = :"+ID+") " +
                "AND a.anuncio1Entity.natureza = a.anuncio2Entity.natureza";

        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        query.setParameter(ID, id);

        return query.getSingleResult();
    }

    public void saveSemelhancaBatch(List<AnuncioSemelhancaEntity> semelhancaEntities) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        for (AnuncioSemelhancaEntity semelhancaEntity : semelhancaEntities) {
            session.save(semelhancaEntity);
        }

        transaction.commit();
        session.close();
    }
}
