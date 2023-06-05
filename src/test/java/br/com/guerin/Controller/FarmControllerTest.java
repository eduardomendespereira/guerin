package br.com.guerin.Controller;

import br.com.guerin.Utils.GetToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import br.com.guerin.Entity.Farm;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.IService.IFarmService;
import br.com.guerin.Service.IService.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
public class FarmControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IFarmService farmService;

    @Autowired
    private IUserService userService;

    private final GetToken gt = new GetToken();

    private Farm farmFactory(String name, String address) {
        Farm farm = new Farm(name, address);
        return this.farmService.save(farm);
    }

    private User userFactory() {
        User user = new User(
                "user",
                "test",
                "user.test@email.com",
                "Us3r",
                "123",
                Role.admin
        );
        if (this.userService.findByUsername(user.getUsername()).isPresent()) {
            return this.userService.findByUsername(user.getUsername()).get();
        }
        return this.userService.save(user);
    }

    @Test
    public void findAllTest(){
        User user = this.userFactory();
        String token = this.gt.getToken(user, "123").access_token;

        try {
            this.mockMvc.perform(get("/api/farm").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findByIdTest(){
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        User user = this.userFactory();
        String token = this.gt.getToken(user, "123").access_token;
        Farm farm = this.farmFactory("_guerin1", "rua_1");

        try {
            MvcResult storyResult = this.mockMvc.perform(get("/api/farm/" + farm.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Farm farm2 = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(), Farm.class);
            Assertions.assertEquals(farm, farm2);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findByNameTest(){
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        User user = this.userFactory();
        String token = this.gt.getToken(user, "123").access_token;
        Farm farm = this.farmFactory("_guerin2", "rua_2");

        try {
            MvcResult storyResult = this.mockMvc.perform(get("/api/farm/name/" + farm.getName()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Farm farm2 = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(), Farm.class);
            Assertions.assertEquals(farm, farm2);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findByAddressTest(){
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        User user = this.userFactory();
        String token = this.gt.getToken(user, "123").access_token;
        Farm farm = this.farmFactory("_guerin3", "rua_3");

        try {
            MvcResult storyResult = this.mockMvc.perform(get("/api/farm/address/" + farm.getAddress()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Farm farm2 = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(), Farm.class);
            Assertions.assertEquals(farm, farm2);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void saveTest() {
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        User user = this.userFactory();
        String token = this.gt.getToken(user, "123").access_token;
        Farm farm = new Farm("_guerin4", "rua_4");

        try {
            String postContent = this.objectMapper.writeValueAsString(farm);
            MvcResult storyResult = this.mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/farm/")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postContent))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Farm farm2 = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(), Farm.class);
            Assertions.assertEquals(farm, farm2);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateTest() {
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        User user = this.userFactory();
        String token = this.gt.getToken(user, "123").access_token;
        Farm farm = this.farmFactory("_guerin5", "rua_5");
        farm.setName("new_guerin5");

        try {
            String postContent = this.objectMapper.writeValueAsString(farm);
            MvcResult storyResult = this.mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/farm/" + farm.getId())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postContent))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Farm farm2 = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(), Farm.class);
            Assertions.assertEquals(farm, farm2);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void disableTest() {
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        User user = this.userFactory();
        String token = this.gt.getToken(user, "123").access_token;
        Farm farm = this.farmFactory("_guerin6", "rua_6");

        try {
            String postContent = this.objectMapper.writeValueAsString(farm);
            MvcResult storyResult = this.mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/farm/disable/" + farm.getId())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postContent))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Farm farm2 = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(), Farm.class);
            Assertions.assertTrue(farm2.isInactive());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}