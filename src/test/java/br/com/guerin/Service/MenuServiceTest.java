package br.com.guerin.Service;

import br.com.guerin.Entity.Menu;
import br.com.guerin.Service.IService.IMenuService;
import br.com.guerin.Service.IService.IParameterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@SpringBootTest
public class MenuServiceTest {
    @Autowired
    IMenuService menuService;

    private Menu menuFactory() {
        Menu menu = new Menu();
        menu.setName("Usuarios");
        menu.setPath("/usuarios");
        menu.setDescription("Cadastro de Usuarios");
        return menu;
    }

    private final Menu menu = this.menuFactory();

    @Test
    public void save() {
        var newMenu = new Menu();
        newMenu.setName("Gado");
        newMenu.setPath("/gados");
        newMenu.setDescription("Cadastro de Gados");

        var obj = menuService.save(newMenu);
        Assertions.assertEquals(obj.getName(), newMenu.getName());
        Assertions.assertEquals(obj.getDescription(), newMenu.getDescription());
        Assertions.assertEquals(obj.getPath(), newMenu.getPath());
    }
    @Test
    public void saveDuplicated() {
        if (!menuService.findByName(menu.getName()).isPresent())
            menuService.save(menu);
        try {
            var duplicatedParameter = menuService.save(menu);
        } catch (ResponseStatusException ex) {
            Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ex.getStatus());
        }
    }
    @Test
    public void update() {
        if (!menuService.findByName(menu.getName()).isPresent())
            menuService.save(menu);
        var updated_menu = menuService.findByName(menu.getName());
        updated_menu.get().setName("Users");
        var obj = menuService.save(updated_menu.get());
        Assertions.assertEquals(obj.getName(), updated_menu.get().getName());
        Assertions.assertEquals(obj.getId(), updated_menu.get().getId());
    }
    @Test
    public void findById() {
        var me = menuService.findByName(menu.getName());
        if (!me.isPresent())
            me = Optional.of(menuService.save(menu));
        var u = menuService.findById(me.get().getId());
        Assertions.assertEquals(u.get().getId(), me.get().getId());
    }
    @Test
    public void findByName() {
        if (!menuService.findByName(menu.getName()).isPresent())
            menuService.save(menu);
        var u = menuService.findByName(menu.getName());
        Assertions.assertEquals(menu.getName(), u.get().getName());
    }
    @Test
    public void delete() {
        var me = menuService.findByName(menu.getName());
        if (!me.isPresent())
            me = Optional.of(menuService.save(menu));
        menuService.delete(me.get().getId());
        Assertions.assertEquals(false, menuService.findByName(menu.getName()).isPresent());
    }
}
