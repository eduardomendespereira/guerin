package br.com.guerin;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.CattleService;
import br.com.guerin.Service.FarmService;
import br.com.guerin.Service.SpecieService;
import br.com.guerin.Service.UserService;
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

//	@Bean
//	CommandLineRunner run (UserService userService, CattleService cattleService, FarmService farmService, SpecieService specieService) {
//		return args -> {
//			userService.save(new User("lucas", "hanke", "lucasghank@hotmail.com", "bagrt", "123", Role.admin));
//			farmService.save(new Farm("Fazenda Generica", "Meio do mato"));
//			specieService.save(new Specie("Nelore"));
//			cattleService.save(new Cattle(Long.valueOf(123), Float.valueOf(300),
//					specieService.findByName("Nelore").get(), farmService.findByName("Fazenda Generica").get(),
//					Gender.male, Long.valueOf(124), Long.valueOf(125)));
//			cattleService.save(new Cattle(Long.valueOf(124), Float.valueOf(350),
//					specieService.findByName("Nelore").get(), farmService.findByName("Fazenda Generica").get(),
//					Gender.male, null, null));
//			cattleService.save(new Cattle(Long.valueOf(125), Float.valueOf(400),
//					specieService.findByName("Nelore").get(), farmService.findByName("Fazenda Generica").get(),
//					Gender.male, null, null));
//		};
//	}
}
