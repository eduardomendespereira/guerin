package br.com.guerin.Utils;

import br.com.guerin.Service.IService.IUserService;
import br.com.guerin.Entity.User;
import br.com.guerin.Entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private IUserService userService;

    @Override
    public void run(String... args) {
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
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
