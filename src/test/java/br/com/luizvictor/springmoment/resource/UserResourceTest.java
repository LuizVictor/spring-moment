package br.com.luizvictor.springmoment.resource;

import br.com.luizvictor.springmoment.entity.user.User;
import br.com.luizvictor.springmoment.entity.user.dto.UserDto;
import br.com.luizvictor.springmoment.repository.UserRepository;
import br.com.luizvictor.springmoment.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserResourceTest {
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;

    @Test
    public void save_withValidData_mustReturnUserDetailsDto() {
       var user = new User(null, "John Doe", "email@email.com", "123456");
       var dto = new UserDto("John Doe", "email@email.com", "123456");

       when(repository.save(any(User.class))).thenReturn(user);
       var details = service.save(dto);

       assertEquals("John Doe", details.name());
       assertTrue(BCrypt.checkpw("123456", details.password()));
    }
}