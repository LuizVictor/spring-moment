package br.com.luizvictor.springmoment.resource;

import br.com.luizvictor.springmoment.entity.user.dto.UserLoginDetailsDto;
import br.com.luizvictor.springmoment.entity.user.dto.UserLoginDto;
import br.com.luizvictor.springmoment.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationResource {
    private final AuthenticationService service;

    public AuthenticationResource(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserLoginDetailsDto> login(@RequestBody UserLoginDto dto) {
        String token = service.authenticate(dto.email(), dto.password());
        return ResponseEntity.ok(new UserLoginDetailsDto(token));
    }
}
