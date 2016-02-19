package bichoperdido.business.media;

import bichoperdido.business.media.domain.ExternalMedia;
import bichoperdido.business.media.domain.MediaTipo;
import bichoperdido.persistence.entities.MediaEntity;
import bichoperdido.persistence.repository.MediaRepository;
import bichoperdido.routes.MediaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gabriel.
 */
@Component
public class MediaService {

    @Autowired
    private MediaUtils mediaUtils;
    @Autowired
    private MediaRepository mediaRepository;

    @Transactional(rollbackOn = Exception.class)
    public String saveImage(byte[] bytes, Integer anuncio) throws IOException {
        String mediaName = mediaUtils.uniqueImageName();

        String nome = anuncio + "_" + mediaName;
        Boolean capa = mediaRepository.countImagesByAnuncioId(anuncio) == 0;
        Character tipo = MediaTipo.IMAGEM.getDbValue();

        mediaRepository.save(new MediaEntity(nome, mediaName, capa, tipo, anuncio));
        mediaUtils.saveImage(mediaName, bytes);

        return MediaController.getImageUri(mediaName);
    }

    @Transactional
    public void setAsCapa(Integer imagem) {
        mediaRepository.clearCapaOfAnuncioByImage(imagem);
        mediaRepository.setAsCapa(imagem);
    }

    public String getAnuncioCapa(int id) {
        MediaEntity media = mediaRepository.findByAnuncioIdAndCapa(id, true);

        if (media != null) {
            return MediaController.getImageThumbUri(media.getCaminho());
        }

        return "";
    }

    public List<MediaEntity> getAnuncioImagens(Integer anuncio_id) {
        return mediaRepository.findByAnuncioIdOrderById(anuncio_id);
    }

    public void getDeleteMedia(List<String> names) {
        mediaUtils.deleteImages(names);
    }

    public void deleteImage(Integer id) {
        MediaEntity media = mediaRepository.findOne(id);
        mediaRepository.delete(id);
        mediaUtils.deleteImages(Arrays.asList(media.getCaminho()));
    }

    public void saveVideo(ExternalMedia video) {
        String nome = video.getAnuncio() + "_" + video;
        String caminho = video.getUrl();
        Boolean capa = false;
        Character tipo = MediaTipo.VIDEO.getDbValue();
        Integer anuncioId = video.getAnuncio();

        mediaRepository.save(new MediaEntity(nome, caminho, capa, tipo, anuncioId));
    }

    public void deleteVideo(Integer id) {
        mediaRepository.delete(id);
    }
}
