package br.com.guerin.Service;

import br.com.guerin.Entity.Menu;
import br.com.guerin.Entity.RoleMenu;
import br.com.guerin.Entity.User;
import br.com.guerin.Repository.Menu.MenuRepository;
import br.com.guerin.Repository.RoleMenu.RoleMenuRepository;
import br.com.guerin.Service.IService.IMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MenuService implements IMenuService {
    private final MenuRepository menuRepository;
    private final RoleMenuRepository roleMenuRepository;

    public Optional<Menu> findById(Long id) {
        return menuRepository.findById(id);
    }

    public Page<Menu> findAll(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    @Transactional
    public Menu save(Menu menu) {
        if (findByName(menu.getName()).isPresent() && menu.getRegistered() == null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Menu ja cadastrado!");
        return menuRepository.save(menu);
    }

    public Optional<Menu> findByName(String name) {
        return menuRepository.findByName(name);
    }

    @Transactional
    public void delete(Long id) {
        if (this.findById(id).isPresent()) {
            var roleMenus = roleMenuRepository.findByMenu(this.findById(id).get());
            if (!roleMenus.isEmpty()) {
                for (RoleMenu r : roleMenus) {
                    roleMenuRepository.deleteById(r.getId());
                }
            }
            menuRepository.deleteById(id);
        }
    }
}
