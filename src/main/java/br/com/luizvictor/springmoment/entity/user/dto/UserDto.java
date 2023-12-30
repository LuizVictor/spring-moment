package br.com.luizvictor.springmoment.entity.user.dto;

import br.com.luizvictor.springmoment.entity.user.User;

public record UserDto(String name, String email, String password) {
}