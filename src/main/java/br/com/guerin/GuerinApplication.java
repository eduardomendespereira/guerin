package br.com.guerin;

import br.com.guerin.Entity.*;
import br.com.guerin.Repository.CattleEvent.CattleEventRepository;
import br.com.guerin.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RequiredArgsConstructor
public class GuerinApplication extends SpringBootServletInitializer {

    private final CattleEventService cattleEventService;

    public static void main(String[] args) {

        SpringApplication.run(GuerinApplication.class, args);
    }

    public void returnFindAllGrouped(){
        this.cattleEventService.findAllAgrouped();
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//            }
//        };
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run (UserService userService, CattleService cattleService, FarmService farmService, SpecieService specieService, MenuService menuService, RoleMenuService roleMenuService) {
//        return args -> {
//        returnFindAllGrouped();
//        };
//    }
}
