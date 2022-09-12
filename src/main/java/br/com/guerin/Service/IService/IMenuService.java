package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Menu;
import br.com.guerin.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface IMenuService {
    Optional<Menu> findById(Long id);
    Page<Menu> findAll(Pageable pageable);
    Menu save(Menu user);
    Optional<Menu> findByName(String name);
    void delete (Long id);
}
