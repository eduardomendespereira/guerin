package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Menu;
import br.com.guerin.Entity.Parameter;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.RoleMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

public interface IRoleMenuService {
    RoleMenu save(RoleMenu roleMenu);
    Page<RoleMenu> findAll(Pageable pageable);
    Optional<RoleMenu> findById(Long id);
    ArrayList<RoleMenu> findByMenu(Menu menu);
    ArrayList<RoleMenu> findByRole(Role role);
    Optional<RoleMenu> findByMenuAndRole(Menu menu, Role role);
    void delete (Long id);
}
