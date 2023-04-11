package br.com.guerin.Utils;

import br.com.guerin.Service.IService.IUserService;
import br.com.guerin.Service.IService.IMenuService;
import br.com.guerin.Entity.User;
import br.com.guerin.Entity.Menu;
import br.com.guerin.Entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMenuService menuService;

    @Override
    public void run(String... args) {
        this.createDefaultUser();
        this.createDefaultMenus();
    }

    private void createDefaultUser() {
        try {
            this.userService.save(
                    new User(
                            "admin",
                            "admin",
                            "admin@admin.com",
                            "admin",
                            "guerin.admin",
                            Role.admin
                    )
            );
        }
        catch (Exception e) {}
    }

    private void createDefaultMenus() {
        List<Menu> menus = new ArrayList<>(
                Arrays.asList(
                        new Menu("Usuarios", 1, "Cadastro de Usuarios", "/usuarios", "fa fa-user fa-2x"),
                        new Menu("Gados", 2, "Cadastro de Gados", "/gados", "")
                )
        );
        for (Menu menu : menus) {
            try {
                this.menuService.save(menu);
            }
            catch (Exception e) {}
        }

    }

}
