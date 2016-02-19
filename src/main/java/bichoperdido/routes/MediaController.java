package bichoperdido.routes;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bichoperdido.business.media.MediaService;
import bichoperdido.business.media.MediaUtils;
import bichoperdido.business.media.domain.ExternalMedia;

/**
 * @author Gabriel
 */

@RestController
public class MediaController extends DefaultController {

    private static final String PUBLIC_MEDIA_IMAGE = "/public/media/image/";
    private static final String PUBLIC_MEDIA_IMAGE_THUMB = "/public/media/image/thumb/";

    @Autowired
    private MediaUtils mediaUtils;
    @Autowired
    private MediaService mediaService;

    @RequestMapping(value="/private/gallery/image", method= RequestMethod.POST)
    public String uploadImagem(@RequestParam("imagem") MultipartFile imagem, @RequestParam("anuncio") Integer anuncio)
            throws IOException {
        return mediaService.saveImage(imagem.getBytes(), anuncio);
    }

    @RequestMapping(value="/private/gallery/video", method= RequestMethod.POST)
    public void saveVideo(@RequestBody ExternalMedia video) throws IOException {
        mediaService.saveVideo(video);
    }

    @RequestMapping("/private/gallery/cover/{id}")
    public void setAsCapa(@PathVariable(value = "id") Integer id) {
        mediaService.setAsCapa(id);
    }

    @RequestMapping("/private/gallery/image/delete/{id}")
    public void deleteImage(@PathVariable(value = "id") Integer id) throws IOException {
        mediaService.deleteImage(id);
    }

    @RequestMapping("/private/gallery/video/delete/{id}")
    public void deleteVideo(@PathVariable(value = "id") Integer id) throws IOException {
        mediaService.deleteVideo(id);
    }

    @RequestMapping(PUBLIC_MEDIA_IMAGE + "{name:.+}")
    public ResponseEntity<byte[]> loadImage(@PathVariable(value = "name") String name) throws IOException {
        byte[] image = mediaUtils.getImageBytes(name);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @RequestMapping(PUBLIC_MEDIA_IMAGE_THUMB + "{name:.+}")
    public ResponseEntity<byte[]> loadImageThump(@PathVariable(value = "name") String name) throws IOException {
        byte[] image = mediaUtils.getImageThumbBytes(name);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @RequestMapping("/public/log/{name}/{tail}")
    public ResponseEntity<String> logs(@PathVariable String name, @PathVariable int tail)
            throws IOException {
        String log;

        if ("out".equals(name)) {
            log = mediaUtils.getJbossLog(tail);
        } else {
            log = mediaUtils.getLog(name, tail);
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        return new ResponseEntity<>(log, headers, HttpStatus.OK);
    }

    public static String getImageUri(String fileName) {
        return PUBLIC_MEDIA_IMAGE + fileName;
    }

    public static String getImageThumbUri(String fileName) {
        return PUBLIC_MEDIA_IMAGE_THUMB + fileName;
    }
}
