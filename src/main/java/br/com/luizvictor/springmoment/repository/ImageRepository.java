package br.com.luizvictor.springmoment.repository;

import br.com.luizvictor.springmoment.entity.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}