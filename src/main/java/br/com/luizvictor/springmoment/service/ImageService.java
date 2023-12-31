package br.com.luizvictor.springmoment.service;

import br.com.luizvictor.springmoment.entity.image.Image;
import br.com.luizvictor.springmoment.entity.image.dto.ImageDetailsDto;
import br.com.luizvictor.springmoment.entity.user.User;
import br.com.luizvictor.springmoment.repository.ImageRepository;
import br.com.luizvictor.springmoment.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public ImageService(ImageRepository repository, UserRepository userRepository) {
        this.imageRepository = repository;
        this.userRepository = userRepository;
    }

    public ImageDetailsDto save(MultipartFile file, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        System.out.println(file);
        try {
            Image image = new Image(
                    UUID.randomUUID(),
                    file.getOriginalFilename(),
                    file.getSize(),
                    file.getContentType(),
                    saveImageOnDisk(file, user.getFolder()),
                    user
            );

            return new ImageDetailsDto(imageRepository.save(image));
        } catch (Exception e) {
            throw new RuntimeException("Image not saved: " + e.getMessage());
        }
    }

    private String saveImageOnDisk(MultipartFile file, String path) throws IOException {
        Path filePath = Paths.get(path, file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        return filePath.toString();
    }
}
