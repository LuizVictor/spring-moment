package br.com.luizvictor.springmoment.service;

import br.com.luizvictor.springmoment.entity.user.User;
import br.com.luizvictor.springmoment.repository.UserRepository;
import br.com.luizvictor.springmoment.security.UserDetailsAuth;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService service;
    @Mock
    private UserRepository repository;

    @BeforeAll
    static void beforeAll() {
        var authentication = mock(Authentication.class);
        var userDetailsAuth = mock(UserDetailsAuth.class);
        var context = mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(userDetailsAuth);
        when(userDetailsAuth.getUsername()).thenReturn("email@email.com");

        SecurityContextHolder.setContext(context);
        when((context).getAuthentication()).thenReturn(authentication);
    }

    @Test
    void loadUserByUsername_withValidEmail_mustReturnUserDetails() {
        var user = new User(null, "John Doe", "email@email.com", "123456");

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        var details = service.loadUserByUsername("email@email.com");

        assertNotNull(details);
        assertEquals("email@email.com", details.getUsername());
    }
}