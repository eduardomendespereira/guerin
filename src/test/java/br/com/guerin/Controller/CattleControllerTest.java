package br.com.guerin.Controller;


import br.com.guerin.Entity.*;
import br.com.guerin.Service.IService.IFarmService;
import br.com.guerin.Service.IService.ISpecieService;
import br.com.guerin.Utils.GetToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import br.com.guerin.Service.IService.ICattleService;
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
public class CattleControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ICattleService cattleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFarmService farmService;

    @Autowired
    private ISpecieService specieService;

    private final GetToken gt = new GetToken();

    private Cattle cattleFactory(
            Long earring, Float weight, Specie specie, Farm farm, Gender gender, Long father, Long mother
    ) {
        Cattle cattle = new Cattle(earring, weight, specie, farm, gender, father, mother);
        return this.cattleService.save(cattle);
    }

    private Farm farmFactory(String name, String address) {
        Farm farm = new Farm(name, address);
        return this.farmService.save(farm);
    }

    private Specie specieFactory(String name) {
        Specie specie = new Specie(name);
        return this.specieService.save(specie);
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
            this.mockMvc.perform(get("/api/cattle").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk());
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
        Specie specie = this.specieFactory("new_1");
        Farm farm = this.farmFactory("new_1", "new_1, 123");
        Cattle cattle = this.cattleFactory(450L, 300f, specie, farm, Gender.male, null, null);

        try {
            MvcResult storyResult = this.mockMvc.perform(get("/api/cattle/" + cattle.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Cattle cattle2 = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(), Cattle.class);
            Assertions.assertEquals(cattle, cattle2);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findByEarringTest(){
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        User user = this.userFactory();
        String token = this.gt.getToken(user, "123").access_token;
        Specie specie = this.specieFactory("new_2");
        Farm farm = this.farmFactory("new_2", "new_2, 123");
        Cattle cattle = this.cattleFactory(451L, 300f, specie, farm, Gender.male, null, null);

        try {
            MvcResult storyResult = this.mockMvc.perform(get("/api/cattle/earring/" + cattle.getEarring()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Cattle cattle2 = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(), Cattle.class);
            Assertions.assertEquals(cattle, cattle2);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findByFarmTest(){
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        User user = this.userFactory();
        String token = this.gt.getToken(user, "123").access_token;
        Specie specie = this.specieFactory("new_3");
        Farm farm = this.farmFactory("new_3", "new_3, 123");
        Cattle cattle = this.cattleFactory(452L, 300f, specie, farm, Gender.male, null, null);

        try {
            MvcResult storyResult = this.mockMvc.perform(get("/api/cattle/farm/" + cattle.getFarm().getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Cattle cattle2 = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(), Cattle.class);
            Assertions.assertEquals(cattle, cattle2);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findBySpecieTest(){
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        User user = this.userFactory();
        String token = this.gt.getToken(user, "123").access_token;
        Specie specie = this.specieFactory("new_4");
        Farm farm = this.farmFactory("new_4", "new_4, 123");
        Cattle cattle = this.cattleFactory(453L, 300f, specie, farm, Gender.male, null, null);

        try {
            MvcResult storyResult = this.mockMvc.perform(get("/api/cattle/specie/" + cattle.getSpecie().getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Cattle cattle2 = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(), Cattle.class);
            Assertions.assertEquals(cattle, cattle2);
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
        Specie specie = this.specieFactory("new_5");
        Farm farm = this.farmFactory("new_5", "new_5, 123");
        Cattle cattle = new Cattle(454L, 300f, specie, farm, Gender.male, null, null);

        try {
            String postContent = this.objectMapper.writeValueAsString(cattle);
            MvcResult storyResult = this.mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/cattle/")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postContent))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Cattle cattle2 = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(), Cattle.class);
            Assertions.assertEquals(cattle, cattle2);
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
        Specie specie = this.specieFactory("new_6");
        Farm farm = this.farmFactory("new_6", "new_6, 123");
        Cattle cattle = this.cattleFactory(455L, 300f, specie, farm, Gender.male, null, null);
        cattle.setWeight(350f);

        try {
            String postContent = this.objectMapper.writeValueAsString(farm);
            MvcResult storyResult = this.mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/farm/" + farm.getId())
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
        Specie specie = this.specieFactory("new_7");
        Farm farm = this.farmFactory("new_7", "new_7, 123");

        try {
            String postContent = this.objectMapper.writeValueAsString(farm);
            MvcResult storyResult = this.mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/farm/disable/" + farm.getId())
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
