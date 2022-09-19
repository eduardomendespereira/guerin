package br.com.guerin.Service;

import br.com.guerin.Entity.Menu;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.RoleMenu;
import br.com.guerin.Repository.RoleMenu.RoleMenuRepository;
import br.com.guerin.Service.IService.IRoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleMenuService implements IRoleMenuService {
    private final RoleMenuRepository roleMenuRepository;

    @Override
    public RoleMenu save(RoleMenu roleMenu) {
        if (findByMenuAndRole(roleMenu.getMenu(), roleMenu.getRole()).isPresent())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("Vinculo entre o menu: {0} e a role: {1} ja cadastrado!", roleMenu.getMenu().getName(), roleMenu.getRole().value));
        return roleMenuRepository.save(roleMenu);
    }

    @Override
    public Page<RoleMenu> findAll(Pageable pageable) {
        return roleMenuRepository.findAll(pageable);
    }

    @Override
    public Optional<RoleMenu> findById(Long id) {
        return roleMenuRepository.findById(id);
    }

    @Override
    public ArrayList<RoleMenu> findByMenu(Menu menu) {
        return roleMenuRepository.findByMenu(menu);
    }

    @Override
    public ArrayList<RoleMenu> findByRole(Role role) {
        return roleMenuRepository.findByRole(role);
    }

    @Override
    public Optional<RoleMenu> findByMenuAndRole(Menu menu, Role role) {
        return roleMenuRepository.findByMenuAndRole(menu, role);
    }

    @Override
    public void delete(Long id) {
        if (this.findById(id).isPresent()) {
            roleMenuRepository.deleteById(id);
        }
    }
}
