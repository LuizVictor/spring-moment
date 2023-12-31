package br.com.luizvictor.springmoment.repository;

import br.com.luizvictor.springmoment.entity.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM images WHERE owner_id = :id")
    List<Image> findAllImages(Long id);
}