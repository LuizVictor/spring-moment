package br.com.luizvictor.springmoment.entity.image.dto;

import br.com.luizvictor.springmoment.entity.image.Image;

public record ImageDetailsDto(String id, String name, long size, String format, String path, Long ownerId) {
    public ImageDetailsDto(Image image) {
        this(
                image.getId().toString(),
                image.getName(),
                image.getSize(),
                image.getFormat(),
                image.getPath(),
                image.getOwner().getId()
        );
    }
}
