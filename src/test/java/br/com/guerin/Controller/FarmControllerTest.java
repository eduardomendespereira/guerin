package br.com.guerin.Controller;

import br.com.guerin.Utils.GetToken;
import br.com.guerin.Entity.Farm;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.IService.IFarmService;
import br.com.guerin.Service.IService.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FarmControllerTest {
    @Autowired
    private IFarmService farmService;

    @Autowired
    private IUserService userService;

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
    public void testGetToken() {
        User user =this.userFactory();
        GetToken gt = new GetToken();
        try {
            StringBuffer x = gt.getToken(user, "123");
            StringBuffer test_temp = null;
        }
        catch (Exception e) {
            String error = e.getMessage();
        }
    }
}