package bichoperdido.business.anuncio;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import bichoperdido.business.animal.domain.Especie;
import bichoperdido.business.anuncio.domain.AnuncioSimplified;
import bichoperdido.business.anuncio.domain.Genero;
import bichoperdido.business.anuncio.domain.Natureza;
import bichoperdido.business.anuncio.domain.Status;
import bichoperdido.persistence.entities.AnuncioEntity;
import bichoperdido.persistence.entities.MediaEntity;
import bichoperdido.routes.MediaController;

/**
 * @author Gabriel.
 */
@Component
public class AnuncioSimplifiedCodec {

    public AnuncioSimplified convert(AnuncioEntity entity) {
        Integer id = entity.getId();
        String natureza = Natureza.valueOfByDb(entity.getNatureza()).toString();
        Calendar datahora = entity.getDataHora();
        String endereco = entity.getLocalEndereco();
        Calendar resolvido = entity.getDataHoraResolvido();
        String status = Status.valueOfByDb(entity.getStatus()).toString();
        String nome = entity.getAnimalEntity().getNome();
        String miniatura = "";

        for (MediaEntity media : entity.getMediaEntity()) {
            if (media.getCapa()) {
                miniatura = MediaController.getImageThumbUri(media.getCaminho());
            }
        }
        Double latitude = entity.getLocalLatitude();
        Double longitude = entity.getLocalLongitude();
        String genero = Genero.valueOfByDb(entity.getAnimalEntity().getGenero()).toString();
        String especie = Especie.valueOfByDb(entity.getAnimalEntity().getEspecie()).toString();

        return new AnuncioSimplified(id, natureza, datahora, endereco, genero, especie, resolvido, status,
                nome, miniatura, latitude, longitude);
    }
}
