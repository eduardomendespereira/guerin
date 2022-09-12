package br.com.guerin.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class MenuTest {
    private Menu menuFactory() {
        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("Usuarios");
        menu.setPath("/usuarios");
        menu.setDescription("Cadastro de Usuarios");
        return menu;
    }
    private final Menu menu = this.menuFactory();
    @Test
    public void testIfMenuNameIsCorrect() {
        Assertions.assertEquals(this.menu.getName(), "Usuarios");
    }
}
