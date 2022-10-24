package br.com.guerin;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GuerinApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuerinApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run (UserService userService, CattleService cattleService, FarmService farmService, SpecieService specieService, MenuService menuService, RoleMenuService roleMenuService) {
//        return args -> {
//            userService.save(new User("lucas", "hanke", "lucasghank@hotmail.com", "bagrt", "123", Role.admin));
//
//        };
//    }
}
