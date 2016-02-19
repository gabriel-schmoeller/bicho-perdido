package bichoperdido.business.anuncio;

import bichoperdido.assyncservice.match.MatchAsyncService;
import bichoperdido.assyncservice.notify.AnuncioNotifyService;
import bichoperdido.business.animal.domain.Especie;
import bichoperdido.business.anuncio.domain.*;
import bichoperdido.business.authentication.domain.UserIdentification;
import bichoperdido.business.authentication.service.UserAuthService;
import bichoperdido.business.cor.CorUtils;
import bichoperdido.business.cor.domain.CieLab;
import bichoperdido.business.cor.domain.Rgb;
import bichoperdido.business.media.MediaService;
import bichoperdido.business.media.domain.Imagem;
import bichoperdido.business.media.domain.MediaTipo;
import bichoperdido.business.media.domain.Video;
import bichoperdido.persistence.entities.*;
import bichoperdido.persistence.repository.*;
import bichoperdido.routes.MediaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Gabriel
 */
@Component
public class AnuncioService {

    @Autowired
    private AnuncioNotifyService notifyService;
    @Autowired
    private MatchAsyncService matchAsyncService;
    @Autowired
    private AnuncioSimplifiedCodec simplifiedCodec;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private PeculiaridadeRepository peculiaridadeRepository;
    @Autowired
    private CorRepository corRepository;
    @Autowired
    private AnuncioRepository anuncioRepository;
    @Autowired
    private AnuncioCustomRepository anuncioCustomRepository;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private CorUtils corUtils;

    @Transactional
    public int createAnuncioAndNotify(AnuncioInput anuncioInput) throws IOException {
        AnimalEntity animalEntity = saveAnimal(anuncioInput);
        AnuncioEntity anuncioEntity = saveAnuncio(anuncioInput, animalEntity);
        anuncioEntity.setAnimalEntity(animalEntity);

        notifyService.notifyProtetores(anuncioEntity);

        return anuncioEntity.getId();
    }

    public void requestMatch() {
        matchAsyncService.doRequest();
    }

    @Transactional
    public void updateAnuncio(AnuncioInput anuncioInput, int id) {
        Integer animalId = anuncioRepository.getAnimalId(id);
        anuncioRepository.update(anuncioInput.getDetalhes(), id);
        animalRepository.update(anuncioInput.getOutras(), anuncioInput.getRaca(), animalId);
        peculiaridadeRepository.deleteByAnimal(animalId);
        savePeculiaridade(animalId, anuncioInput.getPeculiaridades());
    }

    @Transactional
    public AnuncioInput getEditableValues(Integer id) {
        AnuncioEntity anuncio = anuncioRepository.findOne(id);

        List<Peculiaridade> peculiaridades = new ArrayList<>();

        for (PeculiaridadeEntity peculiaridadeEntity : anuncio.getAnimalEntity().getPeculiaridadeEntities()) {
            peculiaridades.add(new Peculiaridade(peculiaridadeEntity.getTipoId(), peculiaridadeEntity.getLocalId()));
        }

        String detalhes = anuncio.getDetalhes();
        Integer raca = anuncio.getAnimalEntity().getRacaId();
        String outras = anuncio.getAnimalEntity().getOutros();

        return new AnuncioInput(peculiaridades, detalhes, raca, outras);
    }

    private AnimalEntity saveAnimal(AnuncioInput anuncioInput) {
        String nome = "";
        if (anuncioInput.getNome() != null) {
            nome = anuncioInput.getNome();
        }
        Character genero = Genero.valueOf(anuncioInput.getGenero()).getDbValue();
        Especie enum_especie = Especie.valueOf(anuncioInput.getEspecie());
        Character especie = enum_especie.getDbValue();
        Integer racaId = anuncioInput.getRaca();
        Character porte = null;
        if (enum_especie == Especie.cachorro) {
            porte = Porte.valueOf(anuncioInput.getPorte()).getDbValue();
        }
        Character pelagem = Pelagem.valueOf(anuncioInput.getPelagem()).getDbValue();
        String outros = anuncioInput.getOutras();

        AnimalEntity animalEntity = new AnimalEntity(nome, genero, especie, racaId, porte, pelagem, outros);
        animalEntity = animalRepository.save(animalEntity);

        savePeculiaridade(animalEntity.getId(), anuncioInput.getPeculiaridades());
        saveCor(anuncioInput, animalEntity);

        return animalEntity;
    }

    private void savePeculiaridade(Integer animalId, List<Peculiaridade> peculiaridades) {
        List<PeculiaridadeEntity> peculiaridadeEntities = new ArrayList<>();

        if (peculiaridades != null) {
            for (Peculiaridade peculiaridade : peculiaridades) {
                PeculiaridadeEntity peculiaridadeEntity = new PeculiaridadeEntity(peculiaridade.getoQue(), peculiaridade.getOnde(),
                        animalId);

                peculiaridadeEntities.add(peculiaridadeEntity);
            }

            peculiaridadeRepository.save(peculiaridadeEntities);
        }
    }

    private void saveCor(AnuncioInput anuncioInput, AnimalEntity animalEntity) {
        List<CorEntity> corEntities = new ArrayList<>();

        for (String cor : anuncioInput.getCores()) {
            Color color = Color.decode(cor);
            CieLab cieLab = corUtils.rgb2lab(color.getRed(), color.getGreen(), color.getBlue());

            corEntities.add(new CorEntity(animalEntity.getId(), cieLab.getL(), cieLab.getA(), cieLab.getB()));
        }

        corRepository.save(corEntities);
    }

    private AnuncioEntity saveAnuncio(AnuncioInput anuncioInput, AnimalEntity animalEntity) {
        Integer usuarioId = userAuthService.getAuthUser().getId();
        Character natureza = Natureza.valueOf(anuncioInput.getNatureza()).getDbValue();
        Integer animalId = animalEntity.getId();
        Calendar data_hora = anuncioInput.getDatahora();
        Double local_longitude = anuncioInput.getLocal().getCoordenadas().getLongitude();
        Double local_latitude = anuncioInput.getLocal().getCoordenadas().getLatitude();
        String local_endereco = anuncioInput.getLocal().getEndereco();
        String detalhes = anuncioInput.getDetalhes();
        Character status = Status.aberto.getDbValue();

        AnuncioEntity anuncioEntity = new AnuncioEntity(usuarioId, natureza, animalId, data_hora, local_longitude, local_latitude, local_endereco, detalhes, status);
        anuncioEntity = anuncioRepository.save(anuncioEntity);

        anuncioRepository.insertAnuncioDistances(anuncioEntity.getId());

        return anuncioEntity;
    }

    @Transactional
    public void delete(int id) {
        Integer userId = userAuthService.getAuthUser().getId();

        List<MediaEntity> imagens = mediaService.getAnuncioImagens(id);
        List<String> names = new ArrayList<>();

        for (MediaEntity imagem : imagens) {
            names.add(imagem.getCaminho());
        }

        anuncioRepository.deleteByIdAndUsuarioId(id, userId);
        mediaService.getDeleteMedia(names);
    }

    @Transactional
    public void setResolvido(int id) {
        Character status = Status.resolvido.getDbValue();
        Integer usuarioId = userAuthService.getAuthUser().getId();
        anuncioRepository.updateStatus(status, id, usuarioId);
    }

    public List<AnuncioSimplified> getByUser(Integer userId) {
        List<AnuncioEntity> entities = anuncioRepository.findByUsuarioIdOrderByDataHoraDesc(userId);

        return convertAnuncioSimplified(entities);
    }

    private List<AnuncioSimplified> convertAnuncioSimplified(Iterable<AnuncioEntity> entities) {
        List<AnuncioSimplified> anuncios = new ArrayList<>();

        for (AnuncioEntity entity : entities) {
            anuncios.add(simplifiedCodec.convert(entity));
        }

        return anuncios;
    }

    public AnuncioOutput getById(int id) {
        AnuncioEntity entity = anuncioRepository.findOne(id);

        String natureza = Natureza.valueOfByDb(entity.getNatureza()).toString();
        Especie especie_enum = Especie.valueOfByDb(entity.getAnimalEntity().getEspecie());
        String especie = especie_enum.toString();
        Calendar datahora = entity.getDataHora();
        List<String> cores = new ArrayList<>();

        for (CorEntity corEntity : entity.getAnimalEntity().getCores()) {
            Rgb rgb = corUtils.lab2rgb(corEntity.getL(), corEntity.getA(), corEntity.getB());
            cores.add(corUtils.rgb2Hex(rgb.getR(), rgb.getG(), rgb.getB()));
        }

        List<PeculiaridadeOutput> peculiaridades = new ArrayList<>();

        for (PeculiaridadeEntity peculiaridadeEntity : entity.getAnimalEntity().getPeculiaridadeEntities()) {
            peculiaridades.add(new PeculiaridadeOutput(peculiaridadeEntity.getTipo(), peculiaridadeEntity.getLocal()));
        }

        Coordenadas coordenadas = new Coordenadas(entity.getLocalLatitude(), entity.getLocalLongitude());
        Local local = new Local(coordenadas, entity.getLocalEndereco());
        String detalhes = entity.getDetalhes();
        String genero = Genero.valueOfByDb(entity.getAnimalEntity().getGenero()).toString();
        String nome = entity.getAnimalEntity().getNome();
        String raca = entity.getAnimalEntity().getRacaEntity().getNome();
        String porte = "";
        if (especie_enum == Especie.cachorro) {
            porte = Porte.valueOfByDb(entity.getAnimalEntity().getPorte()).toString();
        }
        String usuario = entity.getUsuarioEntity().getNome();
        String email = entity.getUsuarioEntity().getEmail();
        String telefone = entity.getUsuarioEntity().getTelefone();
        String pelagem = Pelagem.valueOfByDb(entity.getAnimalEntity().getPelagem()).toString();
        String outras = entity.getAnimalEntity().getOutros();

        List<Imagem> imagens = new ArrayList<>();
        List<Video> videos = new ArrayList<>();

        for (MediaEntity media : entity.getMediaEntity()) {
            Integer mediaId = media.getId();
            MediaTipo tipo = MediaTipo.valueOfByDb(media.getTipo());
            if (tipo == MediaTipo.IMAGEM) {
                String imagem = MediaController.getImageUri(media.getCaminho());
                String miniatura = MediaController.getImageThumbUri(media.getCaminho());
                Boolean capa = media.getCapa();

                imagens.add(new Imagem(mediaId, imagem, miniatura, capa));
            } else {
                String url = media.getCaminho();

                videos.add(new Video(mediaId, url));
            }
        }

        UserIdentification authUser = userAuthService.getAuthUser();
        Boolean meu = Boolean.FALSE;

        if (authUser != null) {
            Integer authUserId = authUser.getId();
            meu = authUserId.equals(entity.getUsuarioId());
        }

        return new AnuncioOutput(natureza, especie, datahora, cores, peculiaridades,
                local, detalhes, genero, nome, raca, porte, pelagem, outras, imagens,
                videos, meu, usuario, email, telefone);
    }

    public SearchResult<AnuncioSimplified> search(BuscaFiltro filtro) {
        SearchResult<AnuncioEntity> search = anuncioCustomRepository.search(filtro);
        List<AnuncioSimplified> anuncioSimplifieds = convertAnuncioSimplified(search.getResultados());

        return new SearchResult<>(search.getTamanho(), anuncioSimplifieds);
    }
}
