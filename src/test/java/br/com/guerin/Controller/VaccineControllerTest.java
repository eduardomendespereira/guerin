package br.com.guerin.Controller;

import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.IService.IUserService;
import br.com.guerin.Utils.GetToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VaccineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VaccineController vaccineController;

    @Autowired
    private IUserService userService;

    private final GetToken getToken = new GetToken();

    private User userFactory(){
        User user = new User(
                "falano",
                "de ciclano",
                "ciclano@email.com",
                "ciclano",
                "123",
                Role.admin
        );
        return this.userService.save(user);
    }

    @Test
    public void checkListAll(){
        User user = this.userFactory();
        System.out.println(user);
        String token = this.getToken.getToken(user, "123").access_token;
        try{
            this.mockMvc.perform(get("/api/vaccines").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}