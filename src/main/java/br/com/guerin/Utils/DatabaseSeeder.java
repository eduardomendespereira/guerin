package br.com.guerin.Utils;

import br.com.guerin.Repository.User.UserRepository;
import br.com.guerin.Entity.User;
import br.com.guerin.Entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        try {
            userRepository.save(
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

        }
    }
}
