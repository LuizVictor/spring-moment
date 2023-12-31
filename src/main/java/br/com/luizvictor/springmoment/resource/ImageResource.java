package br.com.luizvictor.springmoment.resource;

import br.com.luizvictor.springmoment.entity.image.dto.ImageDetailsDto;
import br.com.luizvictor.springmoment.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class ImageResource {
    private final ImageService service;

    public ImageResource(ImageService service) {
        this.service = service;
    }

    @PostMapping("/uploads")
    public ResponseEntity<ImageDetailsDto> save(@RequestParam("file") MultipartFile file, Principal principal) {
        ImageDetailsDto details = service.save(file, principal.getName());
        return ResponseEntity.created(URI.create("/image/" + details.id())).body(details);
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageDetailsDto>> findAll(Principal principal) {
        List<ImageDetailsDto> details = service.findAll(principal.getName());
        return ResponseEntity.ok(details);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<ImageDetailsDto> findById(@PathVariable String id) {
        ImageDetailsDto details = service.findById(id);
        return ResponseEntity.ok(details);
    }
}
