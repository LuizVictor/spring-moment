package br.com.luizvictor.springmoment.service;

import br.com.luizvictor.springmoment.entity.image.Image;
import br.com.luizvictor.springmoment.entity.image.dto.ImageAlbumDto;
import br.com.luizvictor.springmoment.entity.image.dto.ImageDetailsDto;
import br.com.luizvictor.springmoment.entity.user.User;
import br.com.luizvictor.springmoment.repository.ImageRepository;
import br.com.luizvictor.springmoment.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(ImageService.class);

    public ImageService(ImageRepository repository, UserRepository userRepository) {
        this.imageRepository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ImageDetailsDto save(MultipartFile file, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
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
        return path;
    }

    public ImageDetailsDto findById(String id) {
        Image image = imageRepository.findById(UUID.fromString(id)).orElseThrow(
                () -> new EntityNotFoundException("Image not found")
        );

        return new ImageDetailsDto(image);
    }

    public List<ImageDetailsDto> findAll(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Image not found")
        );

        return imageRepository.findAllImages(user.getId()).stream().map(ImageDetailsDto::new).toList();
    }

    @Transactional
    public ImageDetailsDto moveToAlbum(ImageAlbumDto album) {
        Image image = imageRepository.findById(UUID.fromString(album.imageId())).orElseThrow(
                () -> new EntityNotFoundException("Image not found")
        );

        String albumPath = moveImage(Paths.get(image.getPath()), image.getName(), album.albumName());
        image.updatePath(albumPath, album.albumName());

        return new ImageDetailsDto(imageRepository.save(image));
    }

    private String moveImage(Path currentPath, String imageName, String album) {
        Path albumDir = Paths.get(currentPath.toString(), album);
        Path target = Paths.get(currentPath.toString(), imageName);
        Path source = Paths.get(albumDir.toString(), imageName);

        if (!Files.exists(albumDir)) {
            createAlbum(albumDir);
        }

        try {
            logger.info("Moving file  from {} to album {}", currentPath, albumDir);
            Files.move(target, source, REPLACE_EXISTING);

            return albumDir.toString();
        } catch (IOException e) {
            logger.error("Couldn't move file to album");
            throw new RuntimeException(e);
        }
    }

    private void createAlbum(Path path) {
        try {
            logger.info("Creating album {}", path);
            Files.createDirectory(path);
        } catch (IOException e) {
            logger.error("Couldn't create album");
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deteteImage(String id) {
        try {
            logger.info("Deleting image: %s".formatted(id));
            Image imagePath = imageRepository.getReferenceById(UUID.fromString(id));
            imageRepository.deleteById(UUID.fromString(id));

            Files.delete(Paths.get(imagePath.getPath(), imagePath.getName()));
        } catch (Exception e) {
            logger.error("Couldn't delete image: {}", id);
            throw new RuntimeException(e);
        }
    }
}
