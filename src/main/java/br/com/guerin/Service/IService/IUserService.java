package br.com.guerin.Service.IService;

import br.com.guerin.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Long id);
    Page<User> findAll(Pageable pageable);
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    UserDetails loadUserByUsername(String username);
    void disable (Long id);
}