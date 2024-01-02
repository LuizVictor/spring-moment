package br.com.luizvictor.springmoment.entity.image;

import br.com.luizvictor.springmoment.entity.user.User;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private long size;
    private String format;
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
    private String album;

    public Image(UUID id, String name, long size, String format, String path, User owner) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.format = format;
        this.path = path;
        this.owner = owner;
    }

    public Image() {

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public String getFormat() {
        return format;
    }

    public String getPath() {
        return path;
    }

    public User getOwner() {
        return owner;
    }

    public String getAlbum() {
        return album;
    }

    public void updatePath(String albumPath, String albumName) {
        this.path = albumPath;
        album = albumName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
