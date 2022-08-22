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
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Raiva vacitec");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        if(this.vaccineService.findByName(vaccine.getName()).isPresent()){
            return this.vaccineService.findByName(vaccine.getName()).get();
        }
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
    @DisplayName("Teste FindById")
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
            Assertions.assertEquals(vaccine.getName(), vac.getName());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste save")
    public void save(){
        try{
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            User user = this.userFactory();
            String token = getToken.getToken(user, "123").access_token;
            Vaccine vaccine = new Vaccine();
            vaccine.setName("carb vacin");
            vaccine.setDate(LocalDateTime.now());
            vaccine.setRequired(false);
            String postValue = objectMapper.writeValueAsString(vaccine);

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/vaccines")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var createdVaccine = objectMapper.readValue(storyResult.getResponse().getContentAsString(), Vaccine.class);

            Assertions.assertEquals(vaccine.getName(), createdVaccine.getName());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste Update")
    public void update(){
        try{
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            User user = this.userFactory();
            String token = getToken.getToken(user, "123").access_token;
            Vaccine vaccine = this.vaccineFactory();
            vaccine.setName("vac carb");
            String postValue = objectMapper.writeValueAsString(vaccine);

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/vaccines")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var updatedVaccine = objectMapper.readValue(storyResult.getResponse().getContentAsString(), Vaccine.class);
            Assertions.assertEquals(vaccine.getName(), updatedVaccine.getName());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste findByName")
    public void findByName(){
        try{
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            User user = this.userFactory();
            Vaccine vaccine = this.vaccineFactory();
            String token = getToken.getToken(user, "123").access_token;
            MvcResult storyResult = mockMvc.perform(get("/api/vaccines/get-by-name?" + "name=" + vaccine.getName()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            Vaccine vac = objectMapper.readValue(storyResult.getResponse().getContentAsString(), Vaccine.class);
            Assertions.assertEquals(vaccine.getName(), vac.getName());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}