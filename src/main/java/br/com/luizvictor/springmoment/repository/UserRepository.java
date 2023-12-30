package br.com.luizvictor.springmoment.repository;

import br.com.luizvictor.springmoment.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}