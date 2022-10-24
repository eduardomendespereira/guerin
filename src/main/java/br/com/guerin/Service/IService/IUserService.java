package br.com.guerin.Service.IService;

import br.com.guerin.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Long id);
    ArrayList<User> findAll();
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    UserDetails loadUserByUsername(String username);
    void disable (Long id);
    void enable (Long id);
}