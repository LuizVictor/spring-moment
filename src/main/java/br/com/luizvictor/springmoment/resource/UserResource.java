package br.com.luizvictor.springmoment.resource;

import br.com.luizvictor.springmoment.entity.user.dto.UserDetailsDto;
import br.com.luizvictor.springmoment.entity.user.dto.UserDto;
import br.com.luizvictor.springmoment.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@RestController
public class UserResource {
    private final UserService service;

    public UserResource(UserService service) {
        this.service = service;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDetailsDto> save(@RequestBody UserDto dto) {
        UserDetailsDto user = service.save(dto);
        return ResponseEntity.created(URI.create("/users/" + user.id())).body(user);
    }

    @GetMapping(value = "/my-account")
    public ResponseEntity<UserDetailsDto> find(Principal principal) {
        UserDetailsDto user = service.findByEmail(principal.getName());
        return ResponseEntity.ok().body(user);
    }
}
