package br.com.guerin.Controller;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.IService.*;
import br.com.guerin.Utils.GetToken;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WeighingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IWeighingService weighingService;

    @Autowired
    private WeighingController weighingController;

    @Autowired
    private ICattleService cattleService;

    @Autowired
    private IFarmService farmService;

    @Autowired
    private ISpecieService specieService;

    @Autowired
    private IUserService userService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Farm farmFactory(String name, String address) {
        Farm farm = new Farm(name, address);
        return this.farmService.save(farm);
    }

    private Specie specieFactory(String name) {
        Specie specie = new Specie(name);
        return this.specieService.save(specie);
    }

    private Cattle cattleFactory(
            Long earring, Float weight, Specie specie, Farm farm, Gender gender, Long father, Long mother
    ) {
        Cattle cattle = new Cattle(earring, weight, specie, farm, gender, father, mother);
        return this.cattleService.save(cattle);
    }

    private Weighing weightFactory(
            Cattle cattle, Float weight, LocalDateTime date
    ) {

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        Weighing weighing = new Weighing(cattle, date, weight);
        return this.weighingService.save(weighing);
    }

    private final GetToken getTk = new GetToken();

    private User userFactory() {
        User user = new User(
                "gabriel",
                "luiz",
                "gabriel.luiz@gmail.com",
                "skinpert",
                "123",
                Role.admin
        );
        if (this.userService.findByUsername(user.getUsername()).isPresent())
            return this.userService.findByUsername(user.getUsername()).get();
        return this.userService.save(user);
    }

    @Test
    public void insertWeight() {
        User user = this.userFactory();
        System.out.println(user);
        String token = this.getTk.getToken(user, "123").access_token;

        try{
            Specie specie = this.specieFactory("insertController");
            Farm farm = this.farmFactory("insertController", "insertController, 1234");
            Cattle cattle = this.cattleFactory(15L, 700f, specie, farm, Gender.male, null, null);
            Weighing weighing = this.weightFactory(cattle, 200f, LocalDateTime.now());

            String postValue = objectMapper.writeValueAsString(weighing);

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/weighing")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            objectMapper.readValue(storyResult.getResponse().getContentAsString(), User.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateWeight() {
        User user = this.userFactory();
        System.out.println(user);
        String token = this.getTk.getToken(user, "123").access_token;

        try{
            Specie specie = this.specieFactory("updateController");
            Farm farm = this.farmFactory("updateController", "updateController, 1234");
            Cattle cattle = this.cattleFactory(10L, 300f, specie, farm, Gender.male, null, null);
            Weighing weighing = this.weightFactory(cattle, 600f, LocalDateTime.now());

            var weighingUpdate = weighing;
            weighingUpdate.setWeight(900f);

            String postValue = objectMapper.writeValueAsString(weighingUpdate);

            var idWeighing = this.weighingService.findById(weighingUpdate.getId()).getId();

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/weighing/" + idWeighing)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            objectMapper.readValue(storyResult.getResponse().getContentAsString(), User.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void disableWeight() {
        User user = this.userFactory();
        System.out.println(user);
        String token = this.getTk.getToken(user, "123").access_token;

        try{
            Specie specie = this.specieFactory("disableController");
            Farm farm = this.farmFactory("disableController", "disableController, 1234");
            Cattle cattle = this.cattleFactory(12L, 200f, specie, farm, Gender.male, null, null);
            Weighing weighingDisable = this.weightFactory(cattle, 800f, LocalDateTime.now());

            //var weighingDisable = weighing;
            //weighingDisable.setInactive(true);

            String postValue = objectMapper.writeValueAsString(weighingDisable);

            var idWeighing = this.weighingService.findById(weighingDisable.getId()).getId();

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/weighing/disable/" + idWeighing)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            objectMapper.readValue(storyResult.getResponse().getContentAsString(), User.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findAllWeight(){
        User user = this.userFactory();
        System.out.println(user);
        String token = this.getTk.getToken(user, "123").access_token;
        try{
            this.mockMvc.perform(get("/api/weighing").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk()).andDo(print());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

