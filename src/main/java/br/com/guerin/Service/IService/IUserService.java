package br.com.guerin.Service.IService;

import br.com.guerin.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Long id);
    Page<User> findAll(Pageable pageable);
    User save(User user);
    User findByEmail(String email);
    void delete (Long id);
}