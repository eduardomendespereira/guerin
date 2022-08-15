package br.com.guerin.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserTest {
    private User userFactory() {
        User user = new User();
        user.setFirstName("lucas");
        user.setLastName("hanke");
        user.setRole(Role.admin);
        user.setEmail("lucasghank@hotmail.com");
        user.setUsername("bagrt");
        return user;
    }
    private final User user = this.userFactory();

    @Test
    public void testIfUserIsNotInactiveByDefault() {
        Assertions.assertFalse(this.user.isInactive());
    }

    @Test
    public void testIfUserRoleIsCorrect() {
        Assertions.assertEquals(this.user.getRole(), Role.admin);
    }

    @Test
    public void testIfUsernameIsCorrect() {
        Assertions.assertEquals(this.user.getUsername(), "bagrt");
    }
}
