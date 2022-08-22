package br.com.guerin.Controller;

import br.com.guerin.Repository.Farm.FarmRepository;
import br.com.guerin.Utils.GetToken;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import br.com.guerin.Entity.Farm;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.IService.IFarmService;
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
public class FarmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IFarmService farmService;

    @Autowired
    private FarmController farmController;

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
        return this.userService.save(user);
    }

    @Test
    public void findAllThrowsExceptionTest(){
        User user = this.userFactory();
        String token = this.gt.getToken(user, "123").access_token;

        try {
            this.mockMvc.perform(get("/api/cattle").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isBadRequest());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}