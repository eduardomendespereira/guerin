package br.com.guerin.Service;

import br.com.guerin.Entity.Menu;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.RoleMenu;
import br.com.guerin.Service.IService.IMenuService;
import br.com.guerin.Service.IService.IParameterService;
import br.com.guerin.Service.IService.IRoleMenuService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@SpringBootTest
public class RoleMenuServiceTest {
    @Autowired
    IMenuService menuService;
    @Autowired
    IRoleMenuService roleMenuService;
    private Menu menuFactory() {
        Menu menu = new Menu();
        menu.setName("Usuarios");
        menu.setPath("/usuarios");
        menu.setDescription("Cadastro de Usuarios");
        return menu;
    }
    private RoleMenu roleMenuFactory() {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setMenu(menu);
        roleMenu.setRole(Role.admin);
        return roleMenu;
    }
    private final Menu menu = this.menuFactory();
    private final RoleMenu roleMenu = this.roleMenuFactory();

    @Test
    public void save() {
        var newMenu = new Menu();
        newMenu.setName("Gado");
        newMenu.setPath("/gados");
        newMenu.setDescription("Cadastro de Gados");
        if (menuService.findByName(newMenu.getName()).isPresent())
            newMenu = menuService.findByName(newMenu.getName()).get();
        else {
            newMenu = menuService.save(newMenu);
        }
        RoleMenu newRoleMenu = new RoleMenu();
        newRoleMenu.setMenu(newMenu);
        newRoleMenu.setRole(Role.user);

        var obj = roleMenuService.save(newRoleMenu);
        Assertions.assertEquals(obj.getRole(), Role.user);
        Assertions.assertEquals(obj.getMenu().getName(), newMenu.getName());
    }
    @Test
    public void saveDuplicated() {
        if (!menuService.findByName(menu.getName()).isPresent())
            roleMenu.setMenu(menuService.save(menu));
        roleMenu.setMenu(menuService.findByName(menu.getName()).get());
        if (!roleMenuService.findByMenuAndRole(roleMenu.getMenu(), roleMenu.getRole()).isPresent())
            roleMenuService.save(roleMenu);
        try {
            var duplicatedRoleMenu = roleMenuService.save(roleMenu);
        } catch (ResponseStatusException ex) {
            Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ex.getStatus());
        }
    }
    @Test
    public void findById() {
        if (!menuService.findByName(menu.getName()).isPresent())
            roleMenu.setMenu(menuService.save(menu));
        roleMenu.setMenu(menuService.findByName(menu.getName()).get());
        var me = roleMenuService.findByMenuAndRole(roleMenu.getMenu(), roleMenu.getRole());
        if (!me.isPresent())
            me = Optional.of(roleMenuService.save(roleMenu));
        var u = roleMenuService.findById(me.get().getId());
        Assertions.assertEquals(u.get().getId(), me.get().getId());
    }
    @Test
    public void findByMenuAndRole() {
        if (!menuService.findByName(menu.getName()).isPresent())
            roleMenu.setMenu(menuService.save(menu));
        roleMenu.setMenu(menuService.findByName(menu.getName()).get());
        if (!roleMenuService.findByMenuAndRole(roleMenu.getMenu(), roleMenu.getRole()).isPresent())
            roleMenuService.save(roleMenu);
        var u = roleMenuService.findByMenuAndRole(roleMenu.getMenu(), roleMenu.getRole());
        Assertions.assertEquals(roleMenu.getMenu().getName(), u.get().getMenu().getName());
        Assertions.assertEquals(roleMenu.getRole().value, u.get().getRole().value);
    }
    @Test
    public void delete() {
        if (!menuService.findByName(menu.getName()).isPresent())
            roleMenu.setMenu(menuService.save(menu));
        roleMenu.setMenu(menuService.findByName(menu.getName()).get());
        var me = roleMenuService.findByMenuAndRole(roleMenu.getMenu(), roleMenu.getRole());
        if (!me.isPresent())
            me = Optional.of(roleMenuService.save(roleMenu));
        roleMenuService.delete(me.get().getId());
        Assertions.assertEquals(false, roleMenuService.findByMenuAndRole(roleMenu.getMenu(), roleMenu.getRole()).isPresent());
    }
}
