package br.com.luizvictor.springmoment.resource;

import br.com.luizvictor.springmoment.entity.user.dto.UserDetailsDto;
import br.com.luizvictor.springmoment.entity.user.dto.UserDto;
import br.com.luizvictor.springmoment.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserResource {
    private final UserService service;

    public UserResource(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserDetailsDto> save(@RequestBody UserDto dto) {
        UserDetailsDto user = service.save(dto);
        return ResponseEntity.created(URI.create("/users/" + user.id())).body(user);
    }
}
