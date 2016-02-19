package bichoperdido.business.media;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Rotation;
import org.springframework.stereotype.Component;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

/**
 * @author Gabriel
 */
@Component
public class MediaUtils {

    private static final String SEPARATOR = File.separator;
    private static final String LOG_PATH = "log";
    private static final String MEDIA_PATH = "media";
    private static final String IMAGE_PATH = MEDIA_PATH + SEPARATOR + "image";
    private static final String IMAGE_THUMB_PATH = IMAGE_PATH + SEPARATOR + "thumb";
    private static final String IMAGE_EXTENSION = "jpg";
    private static final int MAX_WIDTH = 1280;
    private static final int MAX_HEIGHT = 960;
    private static final double PROPORTION = (double) MAX_WIDTH / MAX_HEIGHT;
    private static final int THUMB_WIDTH = 400;
    private static final int THUMB_HEIGHT = 300;

    public String uniqueImageName() {
        return UUID.randomUUID().toString() + "." + IMAGE_EXTENSION;
    }

    public String saveImage(String fileName, byte[] bytes) throws IOException {
        String fullFileName = getImageFullPath(fileName);
        String fullThumbName = getImageThumbFullPath(fileName);

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(bytes));
            ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            if (directory != null) {
                switch (directory.getInt(ExifIFD0Directory.TAG_ORIENTATION)) { // Em cima, Na esquerda (O que fazer)
                    case 1: // Top, left side (Horizontal / normal)
                        break;
                    case 2: // Top, right side (Mirror horizontal)
                        image = Scalr.rotate(image, Rotation.FLIP_HORZ);
                        break;
                    case 3: // Bottom, right side (Rotate 180)
                        image = Scalr.rotate(image, Rotation.CW_180);
                        break;
                    case 4: // Bottom, left side (Mirror vertical)
                        image = Scalr.rotate(image, Rotation.FLIP_VERT);
                        break;
                    case 5: // Left side, top (Mirror horizontal and rotate 270 CW)
                        image = Scalr.rotate(image, Rotation.FLIP_HORZ);
                        image = Scalr.rotate(image, Rotation.CW_270);
                        break;
                    case 6: // Right side, top (Rotate 90 CW)
                        image = Scalr.rotate(image, Rotation.CW_90);
                        break;
                    case 7: // Right side, bottom (Mirror horizontal and rotate 90 CW)
                        image = Scalr.rotate(image, Rotation.FLIP_HORZ);
                        image = Scalr.rotate(image, Rotation.CW_90);
                        break;
                    case 8: // Left side, bottom (Rotate 270 CW)
                        image = Scalr.rotate(image, Rotation.CW_270);
                        break;
                }
            }
        } catch (ImageProcessingException | MetadataException e) {
            e.printStackTrace();
        }

        BufferedImage resizedImage = image;
        BufferedImage resizedThumb = image;

        if (resizedImage.getWidth() > MAX_WIDTH || resizedImage.getHeight() > MAX_HEIGHT) {
            resizedImage = Scalr.resize(image, Method.ULTRA_QUALITY, MAX_WIDTH, MAX_HEIGHT);
        }
        if (resizedThumb.getWidth() > THUMB_WIDTH || resizedThumb.getHeight() > THUMB_WIDTH) {
            resizedThumb = Scalr.resize(image, THUMB_WIDTH, THUMB_HEIGHT);
        }

        int width = resizedImage.getWidth();
        int height = resizedImage.getHeight();
        int tmbWidth = resizedThumb.getWidth();
        int tmbHeight = resizedThumb.getHeight();
        double actProportion = (double) width / height;

        if (actProportion < PROPORTION) {
            // largura maior (pinta altura)
            width = (int) Math.round(height * PROPORTION);
            tmbWidth = (int) Math.round(tmbHeight * PROPORTION);
            int widthDiff = (int) Math.round((double)(width - resizedImage.getWidth())/2);
            int tmbWidthDiff = (int) Math.round((double)(tmbWidth - resizedThumb.getWidth())/2);

            resizedImage = Scalr.pad(resizedImage, widthDiff);
            resizedThumb = Scalr.pad(resizedThumb, tmbWidthDiff);

            int heightDiff = (int) Math.ceil((double)(resizedImage.getHeight() - height)/2);
            int tmbHeightDiff = (int) Math.ceil((double) (resizedThumb.getHeight() - tmbHeight) / 2);

            resizedImage = Scalr.crop(resizedImage, 0, heightDiff, width, height);
            resizedThumb = Scalr.crop(resizedThumb, 0, tmbHeightDiff, tmbWidth, tmbHeight);
        } else if (actProportion > PROPORTION) {
            // altura maior (pinta largura)
            height = (int) Math.round(width / PROPORTION);
            tmbHeight = (int) Math.round(tmbWidth / PROPORTION);
            int heightDiff = (int) Math.round((double)(height - resizedImage.getHeight())/2);
            int tmbHeightDiff = (int) Math.round((double)(tmbHeight - resizedThumb.getHeight())/2);

            resizedImage = Scalr.pad(resizedImage, heightDiff);
            resizedThumb = Scalr.pad(resizedThumb, tmbHeightDiff);

            int widthDiff = (int) Math.ceil((double) (resizedImage.getWidth() - width) / 2);
            int tmbWidthDiff = (int) Math.ceil((double) (resizedThumb.getWidth() - tmbWidth) / 2);

            resizedImage = Scalr.crop(resizedImage, widthDiff, 0, width, height);
            resizedThumb = Scalr.crop(resizedThumb, tmbWidthDiff, 0, tmbWidth, tmbHeight);
        }

        ImageIO.write(resizedImage, IMAGE_EXTENSION, new File(fullFileName));
        ImageIO.write(resizedThumb, IMAGE_EXTENSION, new File(fullThumbName));

        return fullFileName;
    }

    public String getImageFullPath(String image) {
        return IMAGE_PATH + SEPARATOR + image;
    }

    public String getImageThumbFullPath(String image) {
        return IMAGE_THUMB_PATH + SEPARATOR + image;
    }

    public void buildMediaEnv() {
        List<File> dirs = new ArrayList<>();
        dirs.add(new File(IMAGE_PATH));
        dirs.add(new File(IMAGE_THUMB_PATH));

        for (File dir : dirs) {
            dir.mkdirs();
        }
    }

    public byte[] getImageBytes(String name) throws IOException {
        return FileUtils.readFileToByteArray(new File(getImageFullPath(name)));
    }

    public byte[] getImageThumbBytes(String name) throws IOException {
        return FileUtils.readFileToByteArray(new File(getImageThumbFullPath(name)));
    }

    public String getLog(String name, int size) throws IOException {
        return readTail(size, getLogFullPath(name));
    }

    public String getJbossLog(int size) throws IOException {
        return readTail(size, System.getProperty("user.home") + SEPARATOR + "app-root" + SEPARATOR + "logs"+ SEPARATOR + "jbossews.log");
    }

    private String readTail(int size, String filePath) throws IOException {
        File file = new File(filePath);

        if (size > 0) {
            LineIterator lineIterator = FileUtils.lineIterator(file);
            LinkedList<String> lines = new LinkedList<>();
            StringBuilder log = new StringBuilder();

            while (lineIterator.hasNext()) {
                lines.add(lineIterator.next());

                if (lines.size() > size) {
                    lines.remove();
                }
            }

            for (String line : lines) {
                log.append(line).append("\n");
            }

            return log.toString();
        } else {
            return FileUtils.readFileToString(file);
        }
    }

    private String getLogFullPath(String name) {
        return LOG_PATH + SEPARATOR + name + ".log";
    }

    public void deleteImages(List<String> names) {
        for (String name : names) {
            new File(getImageFullPath(name)).delete();
            new File(getImageThumbFullPath(name)).delete();
        }
    }
}
