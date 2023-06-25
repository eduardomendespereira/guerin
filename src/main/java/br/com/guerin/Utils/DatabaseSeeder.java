package br.com.guerin.Utils;

import br.com.guerin.Entity.RoleMenu;
import br.com.guerin.Service.IService.IRoleMenuService;
import br.com.guerin.Service.IService.IUserService;
import br.com.guerin.Service.NotificationService;
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

    @Autowired
    private IRoleMenuService roleMenuService;

    @Override
    public void run(String... args) {
        this.createDefaultUser();
        this.createDefaultMenus();
    }

    private void createDefaultUser() {
        try {
            NotificationService notificationService = new NotificationService();
            this.userService.save(
                    new User(
                            "admin",
                            "admin",
                            "admin@admin.com",
                            "admin",
                            "guerin.admin",
                            Role.admin
                    ), 
                    notificationService
            );
        }
        catch (Exception e) {}
    }

    private void createDefaultMenus() {
        List<Menu> menus = new ArrayList<>(
                Arrays.asList(
                        new Menu("Usuarios", 1, "Cadastro de Usuarios", "/usuarios", "fa fa-user fa-2x"),
                        new Menu("Gados", 2, "Cadastro de Gados", "/gados", "fa fa-server fa-2x"),
                        new Menu("Eventos", 3, "Eventos", "/eventos", "fa fa-calendar-check-o fa-2x"),
                        new Menu("Espécie", 4, "Cadastros de Espécies", "/especie", "fa fa-database fa-2x"),
                        new Menu("Fazendas", 5, "Cadastros de Fazendas", "/fazendas", "fa fa-home fa-2x"),
                        new Menu("Tipo de Evento", 6, "Cadastro de Tipos de Eventos", "/tipo-de-evento", "fa fa-calendar-plus-o fa-2x"),
                        new Menu("Controle de Lactação", 7, "Listagem de lactações", "/lactacoes", "fa fa-line-chart fa-2x"),
                        new Menu("Sair", 8, "Sair", "/login", "fa fa-sign-out fa-2x")
                )
        );

        for (Menu menu : menus) {
            try {
                RoleMenu roleMenu = new RoleMenu(this.menuService.save(menu), Role.admin);
                this.roleMenuService.save(roleMenu);
            }
            catch (Exception e) {}
        }
    }
}