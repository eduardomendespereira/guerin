package br.com.guerin.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class RoleMenuTest {
    private RoleMenu roleMenuFactory() {
        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("Usuarios");
        menu.setPath("/usuarios");
        menu.setDescription("Cadastro de Usuarios");

        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setMenu(menu);
        roleMenu.setRole(Role.admin);
        return roleMenu;
    }
    private final RoleMenu roleMenu = this.roleMenuFactory();
    @Test
    public void testIfMenuNameIsCorrect() {
        Assertions.assertEquals(this.roleMenu.getMenu().getName(), "Usuarios");
    }
    @Test
    public void testIfRoleIsCorrect() {
        Assertions.assertEquals(this.roleMenu.getRole(), Role.admin);
    }
}
