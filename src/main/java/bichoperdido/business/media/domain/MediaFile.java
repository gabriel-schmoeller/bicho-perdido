package bichoperdido.business.media.domain;

import java.util.Objects;

/**
 * @author Gabriel
 */
public class MediaFile {

    private final String extension;
    private final byte[] bytes;

    public MediaFile(String extension, byte[] bytes) {
        this.extension = extension;
        this.bytes = bytes;
    }

    public String getExtension() {
        return extension;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaFile mediaFile = (MediaFile) o;
        return Objects.equals(extension, mediaFile.extension) &&
                Objects.equals(bytes, mediaFile.bytes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extension, bytes);
    }
}
