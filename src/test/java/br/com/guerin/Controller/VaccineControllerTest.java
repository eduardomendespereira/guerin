package br.com.guerin.Controller;

import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.User;
import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Service.IService.IUserService;
import br.com.guerin.Service.IService.IVaccineService;
import br.com.guerin.Utils.GetToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VaccineControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VaccineController vaccineController;

    @Autowired
    private IUserService userService;

    @Autowired
    private IVaccineService vaccineService;

    private final GetToken getToken = new GetToken();

    private Vaccine vaccineFactory(){
        LocalDateTime dateTime = LocalDateTime.now();
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Raiva vacitec");
        vaccine.setDate(dateTime);
        vaccine.setRequired(true);
        return this.vaccineService.save(vaccine);
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
    @DisplayName("Teste de listagem das vacinas")
    public void checkListAll(){
        User user = this.userFactory();
        String token = this.getToken.getToken(user, "123").access_token;
        try{
            this.mockMvc.perform(get("/api/vaccines").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findById(){
        try{
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            User user = this.userFactory();
            Vaccine vaccine = this.vaccineFactory();
            String token = getToken.getToken(user, "123").access_token;
            MvcResult storyResult = mockMvc.perform(get("/api/vaccines/" + vaccine.getId().toString()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            Vaccine vac = objectMapper.readValue(storyResult.getResponse().getContentAsString(), Vaccine.class);
            Assertions.assertEquals(vaccine.getId(), vac.getId());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}