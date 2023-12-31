package br.com.luizvictor.springmoment.entity.user;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String folder;
    private String role;

    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.folder = createFolder();
        this.role = "USER";
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFolder() {
        return folder;
    }

    public String createFolder() {
        String userFolderName = UUID.randomUUID().toString();
        String currentDir = System.getProperty("user.dir");
        Path uploadsFolder = Paths.get(currentDir, "uploads");
        Path userFoldrDir = uploadsFolder.resolve(userFolderName);
        try {
            Files.createDirectory(userFoldrDir);
            return userFoldrDir.toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
