package br.com.guerin.Controller;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.IService.*;
import br.com.guerin.Utils.GetToken;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VaccineApplicationControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFarmService farmService;

    @Autowired
    private ISpecieService specieService;

    @Autowired
    private ICattleService cattleService;
    @Autowired
    private IVaccineApplicationService vaccineApplicationService;

    @Autowired
    private IVaccineService vaccineService;

    private final GetToken getToken = new GetToken();

    public void generateSpecieAndFarm() {
        if (!farmService.findByName("Fazenda Qualquer").isPresent())
            farmService.save(new Farm("Fazenda Qualquer", "Rondonia"));
        if (!specieService.findByName("Brandus").isPresent())
            specieService.save(new Specie("Brandus"));
    }

    public Cattle cattleFactory(){
            this.generateSpecieAndFarm();
            Cattle cattle = new Cattle(
                    123L,
                    300F,
                    specieService.findByName("Brandus").get(),
                    farmService.findByName("Fazenda Qualquer").get(),
                    Gender.male,
                    null,
                    null);
            if(cattleService.findByEarring(cattle.getEarring()).isPresent()){
                return cattleService.findByEarring(cattle.getEarring()).get();
            }
            return cattleService.save(cattle);
    }

    private Vaccine vaccineFactory(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Carbunculo Vatec");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(false);
        if(this.vaccineService.findByName(vaccine.getName()).isPresent()){
            return this.vaccineService.findByName(vaccine.getName()).get();
        }
        return this.vaccineService.save(vaccine);
    }

    private VaccineApplication vaccineAppFactory(){
        Cattle cattle = this.cattleFactory();
        Vaccine vaccine = this.vaccineFactory();
        VaccineApplication vaccineApplication = new VaccineApplication();
        vaccineApplication.setNote("Aplicacao de vacina para carb");
        vaccineApplication.setDate(LocalDateTime.now());
        vaccineApplication.setVaccine(vaccine);
        vaccineApplication.setCattle(cattle);
        if(vaccineApplicationService.findById(vaccine.getId()).isPresent()){
            return vaccineApplicationService.findById(vaccine.getId()).get();
        }
        return vaccineApplicationService.save(vaccineApplication);
    }

    private User userFactory(){
        User user = new User(
                "falano",
                "de ciclano",
                "ciclano@email.com",
                "ciclano",
                "123",
                Role.admin
        );
        if (this.userService.findByUsername(user.getUsername()).isPresent()) {
            return this.userService.findByUsername(user.getUsername()).get();
        }
        return this.userService.save(user);
    }

    @Test
    @DisplayName("teste listAll")
    public void listAll(){
        try {
            User user = this.userFactory();
            String token = getToken.getToken(user, "123").access_token;
            this.mockMvc.perform(get("/api/vaccineApplications").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste findByVaccine")
    public void findByVaccine(){
        try {
            VaccineApplication vaccineApplication = this.vaccineAppFactory();
            User user = this.userFactory();
            String token = getToken.getToken(user, "123").access_token;
            mockMvc.perform(get("/api/vaccineApplications/" + vaccineApplication.getVaccine().getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste findById")
    public void findById(){

    }
}
