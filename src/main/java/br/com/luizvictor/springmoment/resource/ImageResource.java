package br.com.luizvictor.springmoment.resource;

import br.com.luizvictor.springmoment.entity.image.dto.ImageDetailsDto;
import br.com.luizvictor.springmoment.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/uploads")
public class ImageResource {
    private final ImageService service;

    public ImageResource(ImageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ImageDetailsDto> save(@RequestParam("file") MultipartFile file, Principal principal) {
        ImageDetailsDto details = service.save(file, principal.getName());
        return ResponseEntity.ok(details);
    }
}
