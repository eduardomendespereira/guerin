package br.com.guerin.Controller;


import br.com.guerin.Entity.*;
import br.com.guerin.Utils.GetToken;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import br.com.guerin.Service.IService.ICattleService;
import br.com.guerin.Service.IService.IUserService;
import org.json.JSONObject;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CattleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ICattleService cattleService;

    @Autowired
    private CattleController cattleController;

    @Autowired
    private IUserService userService;

    private final GetToken gt = new GetToken();

    private Cattle cattleFactory(
            Long earring, Float weight, Specie specie, Farm farm, Gender gender, Long father, Long mother
    ) {
        Cattle cattle = new Cattle(earring, weight, specie, farm, gender, father, mother);
        return this.cattleService.save(cattle);
    }

    private User userFactory() {
        User user = new User(
                "user1",
                "test1",
                "user1.test1@email.com",
                "Us3r1",
                "123",
                Role.admin
        );
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
}
