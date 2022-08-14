package br.com.guerin.Service;

import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.IService.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    IUserService userService;
    private final User user = new User("lucas", "hanke", "lucasghank@hotmail.com", "bagrt", "123", Role.admin);

    @Test
    public void save() {
        var obj = userService.save(user);
        if (obj == null && userService.findByUsername(user.getUsername()) != null) {
            Assertions.assertEquals(userService.findByUsername(user.getUsername()).get().getUsername(), user.getUsername());
        } else {
            Assertions.assertEquals(obj.getUsername(), user.getUsername());
        }
    }

    @Test
    public void update() {
        var usu = userService.save(user);
        Optional<User> u = userService.findByUsername(user.getUsername());
        if (u.isPresent()) {
            User updated_user = new User(u.get().getId(), u.get().getRegistered(), u.get().isInactive(), "lucas",
                    "hanke12", "lucasghank@gmail.com", "bagrt", "123", Role.admin);
            ;
            var obj = userService.save(updated_user);
            Assertions.assertEquals(updated_user.getFirstName(), obj.getFirstName());
            Assertions.assertEquals(updated_user.getLastName(), obj.getLastName());
            Assertions.assertEquals(updated_user.getEmail(), obj.getEmail());
        }
    }

    @Test
    public void findAll() {
        var obj = userService.save(user);
        var users = userService.findAll(PageRequest.of(0, 100));
        var exists = users != null;
        Assertions.assertEquals(true, exists);
    }

    @Test
    public void findByUsername() {
        var obj = userService.save(user);
        var u = userService.findByUsername(user.getUsername());
        Assertions.assertEquals(user.getUsername(), u.get().getUsername());
    }

    @Test
    public void findById() {
        var obj = userService.save(user);
        var u = userService.findByUsername(user.getUsername());
        var uId = userService.findById(u.get().getId());
        Assertions.assertEquals(user.getUsername(), uId.get().getUsername());
    }

    @Test
    public void findByEmail() {
        var obj = userService.save(user);
        var u = userService.findByUsername(user.getUsername());
        var u_email = userService.findByEmail(u.get().getEmail());
        Assertions.assertEquals(user.getUsername(), u_email.get().getUsername());
    }

    @Test
    public void loadUserByUsername() {
        var obj = userService.save(user);
        var u = userService.loadUserByUsername(user.getUsername());
        Assertions.assertEquals(user.getUsername(), u.getUsername());
        Assertions.assertEquals(user.getRole().value, u.getAuthorities().stream().findFirst().get().toString());
    }

    @Test
    public void disable() {
        var obj = userService.save(user);
        var u = userService.findByUsername(user.getUsername());
        userService.disable(u.get().getId());
        var inactive_user = userService.findById(u.get().getId());
        Assertions.assertEquals(true, inactive_user.get().isInactive());
    }
}
