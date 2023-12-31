package br.com.luizvictor.springmoment.entity.user.dto;

import br.com.luizvictor.springmoment.entity.user.User;

public record UserDetailsDto(Long id, String name, String email, String password, String folder) {
    public UserDetailsDto(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getFolder());
    }
}
