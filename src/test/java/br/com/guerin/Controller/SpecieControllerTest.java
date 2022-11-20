package br.com.guerin.Controller;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.Specie;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.SpecieService;
import br.com.guerin.Service.UserService;
import br.com.guerin.Utils.GetToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class SpecieControllerTest {
    @Autowired
    private SpecieService specieService;

    @Autowired
    private UserService userService;
    ;

    @Autowired
    MockMvc mockMvc;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final GetToken gtToken = new GetToken();

    public User userFactory(){
        User user = new User(
                "user",
                "test",
                "user.test@gmail.com",
                "Us3r",
                "123",
                Role.admin
        );
        if(!userService.findAll().isEmpty()){
            return userService.findByUsername("Us3r").get();
        }else {
            return userService.save(user);
        }
    }
    public Specie saveSpecieFactory(){
        if(specieService.listAll().isEmpty()){
            return specieService.save(new Specie("Pimposa"));

        }else{
            return specieService.findByName("Pimposa").get();
        }
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public Specie specieFactory(){
        Specie specie = new Specie("Fidas");
        return specie;
    }

    @Test
    public void insertSpecie(){
        User user = userFactory();
        Specie specie = specieFactory();
        String token = this.gtToken.getToken(user, "123").access_token;
        try {
            this.mockMvc.perform(post("/api/species").header(HttpHeaders.AUTHORIZATION, "Bearer" + token)
                            .content(asJsonString(specie)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void findAllSpecie(){
        User user = userFactory();
        String token = this.gtToken.getToken(user, "123").access_token;
        try {
            this.mockMvc.
                    perform(MockMvcRequestBuilders.
                            get("/api/species").
                            accept(MediaType.APPLICATION_JSON).
                            header(HttpHeaders.AUTHORIZATION, "Bearer " + token)).
                    andExpect(status().isOk());

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }
    @Test
    public void updateSpecie(){
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            User user = userFactory();
            String token = this.gtToken.getToken(user, "123").access_token;
            Specie specie = saveSpecieFactory();
            String postVal = objectMapper.writeValueAsString(specie);
            this.mockMvc.perform(put("/api/species/" + specie.getId().toString())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer" + token).content(postVal)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }

    }
    @Test
    public void findSpecieById(){
        User user = userFactory();
        Specie specie = saveSpecieFactory();
        String token = this.gtToken.getToken(user, "123").access_token;
        try {
            MvcResult result = mockMvc.perform(get("/api/species/" + specie.getId().toString()).
                    header(HttpHeaders.AUTHORIZATION, "Bearer" + token)).andExpect(status().isOk()).andReturn();
            Specie tempEvent = objectMapper.readValue(result.getResponse().getContentAsString(), Specie.class);
            Assertions.assertEquals(specie, tempEvent);
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void findSpecieByName(){
        User user = userFactory();
        Specie specie = saveSpecieFactory();
        String token = this.gtToken.getToken(user, "123").access_token;
        try {
            MvcResult result = mockMvc.perform(get("/api/species/name/" + specie.getName()).
                    header(HttpHeaders.AUTHORIZATION, "Bearer" + token)).andExpect(status().isOk()).andReturn();
            Specie tempEvent = objectMapper.readValue(result.getResponse().getContentAsString(), Specie.class);
            Assertions.assertEquals(specie, tempEvent);
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void disableSpecie(){
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            User user = userFactory();
            Specie specie = saveSpecieFactory();
            String token = this.gtToken.getToken(user, "123").access_token;
            String postVal = objectMapper.writeValueAsString(specie);

            this.mockMvc.perform(put("/api/species/disable/"+ specie.getId().toString())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer" + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postVal))
                    .andExpect(status().isOk())
                    .andReturn();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    @Test
    @DisplayName("Tentando criar uma Specie com um nome já existente")
    public void insertAExistentSpecie(){
        try {
            User user = userFactory();
            Specie specie = saveSpecieFactory();
            String token = this.gtToken.getToken(user, "123").access_token;
            Specie specie1 = new Specie();
            specie1.setName(specie.getName());
            this.mockMvc.perform(post("/api/species").header(HttpHeaders.AUTHORIZATION, "Bearer" + token)
                            .content(asJsonString(specie1)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());

        }
    }

    @Test
    @DisplayName("Inserindo Id Invalido no Update da Specie")
    public void updateASpecieWithAInvalidId(){
        try {
            User user = userFactory();
            Specie specie = saveSpecieFactory();
            String token = this.gtToken.getToken(user, "123").access_token;
            String postVal = objectMapper.writeValueAsString(specie);
            this.mockMvc.perform(put("/api/species/240")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer" + token).content(postVal)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @DisplayName("Atualizando a Specie com um Nome que pertence a outra")
    public void updateASpecieWithANameThatExistInAnotherSpecie(){
        try {
            User user = userFactory();
            Specie specie = saveSpecieFactory();
            Specie specie1 = specieService.save(new Specie("Pipocas"));
            Assertions.assertNotEquals(specie1, specie);
            specie.setName(specie1.getName());
            String token = this.gtToken.getToken(user,"123").access_token;
            String postVal = objectMapper.writeValueAsString(specie);
            this.mockMvc.perform(put("/api/species/" + specie.getId().toString())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer" + token).content(postVal)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void findByAInvalidId(){
        try {
            User user = userFactory();
            Specie specie = saveSpecieFactory();
            String token = this.gtToken.getToken(user, "123").access_token;
            this.mockMvc.perform(get("/api/species/240").
                            header(HttpHeaders.AUTHORIZATION, "Bearer" + token))
                    .andExpect(status().isNotFound()).andDo(print()).andReturn();

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    @Test
    public void findSpecieByAInvalidName(){
        User user = userFactory();
        Specie specie = saveSpecieFactory();
        specie.setName("Jaozito");
        String token = this.gtToken.getToken(user, "123").access_token;
        try {
            MvcResult result = mockMvc.perform(get("/api/species/name/" + specie.getName()).
                    header(HttpHeaders.AUTHORIZATION, "Bearer" + token)).andExpect(status().isNotFound()).andReturn();
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }




}
