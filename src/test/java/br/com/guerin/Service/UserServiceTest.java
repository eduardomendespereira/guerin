package br.com.guerin.Service;

import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.IService.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    IUserService userService;
    private final User user = new User("lucas", "hanke", "lucasghank@hotmail.com", "bagrt", "123", Role.admin);

    @Test
    public void save() {
        User newUser = new User("lucas2", "hanke2", "lucas@hotmail.com", "lucas", "123", Role.admin);
        var obj = userService.save(newUser);
        Assertions.assertEquals(obj.getUsername(), newUser.getUsername());
    }

    @Test
    public void update() {
        Optional<User> us = userService.findByUsername(user.getUsername());
        if (!us.isPresent())
            us = Optional.of(userService.save(user));
        if (us.isPresent()) {
            User updated_user = new User(us.get().getId(), us.get().getRegistered(), us.get().isInactive(), "lucas",
                    "hanke12", "lucasghank@gmail.com", "bagrt", "123", Role.admin);

            var obj = userService.save(updated_user);
            Assertions.assertEquals(updated_user.getFirstName(), obj.getFirstName());
            Assertions.assertEquals(updated_user.getLastName(), obj.getLastName());
            Assertions.assertEquals(updated_user.getEmail(), obj.getEmail());
        }
    }

    @Test
    public void findAll() {
        if (!userService.findByUsername(user.getUsername()).isPresent())
            userService.save(user);
        var users = userService.findAll(PageRequest.of(0, 100));
        Assertions.assertEquals(1, users.get().count());
    }

    @Test
    public void findByUsername() {
        if (!userService.findByUsername(user.getUsername()).isPresent())
            userService.save(user);
        var u = userService.findByUsername(user.getUsername());
        Assertions.assertEquals(user.getUsername(), u.get().getUsername());
    }
    @Test
    public void testUsernameDuplicated() {
        if (!userService.findByUsername(user.getUsername()).isPresent())
            userService.save(user);
        try {
            var duplicatedUser = userService.save(user);
        } catch (ResponseStatusException ex) {
            Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ex.getStatus());
        }
    }
    @Test
    public void findById() {
        if (!userService.findByUsername(user.getUsername()).isPresent())
            userService.save(user);
        var u = userService.findByUsername(user.getUsername());
        var uId = userService.findById(u.get().getId());
        Assertions.assertEquals(user.getUsername(), uId.get().getUsername());
    }

    @Test
    public void findByEmail() {
        if (!userService.findByUsername(user.getUsername()).isPresent())
            userService.save(user);
        var u = userService.findByUsername(user.getUsername());
        var u_email = userService.findByEmail(u.get().getEmail());
        Assertions.assertEquals(user.getUsername(), u_email.get().getUsername());
    }

    @Test
    public void loadUserByUsername() {
        if (!userService.findByUsername(user.getUsername()).isPresent())
            userService.save(user);
        var u = userService.loadUserByUsername(user.getUsername());
        Assertions.assertEquals(user.getUsername(), u.getUsername());
        Assertions.assertEquals(user.getRole().value, u.getAuthorities().stream().findFirst().get().toString());
    }

    @Test
    public void disable() {
        if (!userService.findByUsername(user.getUsername()).isPresent())
            userService.save(user);
        var u = userService.findByUsername(user.getUsername());
        userService.disable(u.get().getId());
        var inactive_user = userService.findById(u.get().getId());
        Assertions.assertEquals(true, inactive_user.get().isInactive());
    }
}
