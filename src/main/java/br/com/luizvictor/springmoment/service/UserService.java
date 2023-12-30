package br.com.luizvictor.springmoment.service;

import br.com.luizvictor.springmoment.entity.user.User;
import br.com.luizvictor.springmoment.entity.user.dto.UserDetailsDto;
import br.com.luizvictor.springmoment.entity.user.dto.UserDto;
import br.com.luizvictor.springmoment.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UserDetailsDto save(UserDto dto) {
        User user = new User(null, dto.name(), dto.email(), dto.password());
        return new UserDetailsDto(repository.save(user));
    }

    public UserDetailsDto findByEmail(String email) {
        User user = repository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return  new UserDetailsDto(user);
    }
}
