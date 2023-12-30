package br.com.luizvictor.springmoment.service;

import br.com.luizvictor.springmoment.entity.user.User;
import br.com.luizvictor.springmoment.repository.UserRepository;
import br.com.luizvictor.springmoment.security.TokenService;
import br.com.luizvictor.springmoment.security.UserDetailsAuth;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    private final UserRepository repository;
    private final AuthenticationManager manager;
    private final TokenService service;

    public AuthenticationService(UserRepository repository, @Lazy AuthenticationManager manager, TokenService token) {
        this.repository = repository;
        this.manager = manager;
        this.service = token;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        return new UserDetailsAuth(user);
    }

    public String authenticate(String email, String password) {
        var token = new UsernamePasswordAuthenticationToken(email, password);
        Authentication auth = manager.authenticate(token);
        return service.create((UserDetailsAuth) auth.getPrincipal());
    }
}
