package br.com.guerin.Controller;

import br.com.guerin.Entity.Menu;
import br.com.guerin.Entity.Parameter;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.IService.IMenuService;
import br.com.guerin.Service.IService.IParameterService;
import br.com.guerin.Service.IService.IUserService;
import br.com.guerin.Service.NotificationService;
import br.com.guerin.Utils.GetToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MenuControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IMenuService menuService;
    @Autowired
    private IUserService userService;
    private final GetToken getToken = new GetToken();

    private User userFactoryAdmin() {
        User user = new User(
                "lucas",
                "hanke",
                "lucas@email.com",
                "bagrt",
                "123",
                Role.admin
        );
        if (this.userService.findByUsername(user.getUsername()).isPresent())
            return this.userService.findByUsername(user.getUsername()).get();
        NotificationService notificationService = new NotificationService();
        return this.userService.save(user, notificationService);
    }

    private Menu menuFactory() {
        Menu menu = new Menu();
        menu.setName("Usuarios");
        menu.setPath("/usuarios");
        menu.setDescription("Cadastro de Usuarios");
        if (menuService.findByName(menu.getName()).isPresent())
            return menuService.findByName(menu.getName()).get();
        return menuService.save(menu);
    }
    @Test
    public void listAll() {
        try {
            User user = this.userFactoryAdmin();
            var menu = menuFactory();
            String token = getToken.getToken(user, "123").access_token;
            MvcResult storyResult = mockMvc.perform(get("/api/menu").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Assertions.assertEquals(HttpStatus.OK.value(), storyResult.getResponse().getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findById() {
        try {
            var menu = menuFactory();
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;
            MvcResult storyResult = mockMvc.perform(get("/api/menu/" + menu.getId().toString()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var menuObj = objectMapper.readValue(storyResult.getResponse().getContentAsString(), Menu.class);
            Assertions.assertEquals(menu.getId(), menuObj.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void findByName() {
        try {
            var menu = menuFactory();
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;
            MvcResult storyResult = mockMvc.perform(get("/api/menu/name/" + menu.getName()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var menuObj = objectMapper.readValue(storyResult.getResponse().getContentAsString(), Menu.class);
            Assertions.assertEquals(menu.getId(), menuObj.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void save() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;

            Menu menu = new Menu();
            menu.setName("Gados_");
            menu.setPath("/gados_");
            menu.setDescription("Cadastro de Gados");

            String postValue = objectMapper.writeValueAsString(menu);

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/menu")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var createdMenu = objectMapper.readValue(storyResult.getResponse().getContentAsString(), Menu.class);

            Assertions.assertEquals(menu.getName(), createdMenu.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void update() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;
            var menu = menuFactory();
            menu.setDescription("Cadastro de Users");
            String postValue = objectMapper.writeValueAsString(menu);
            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/menu")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var updatedMenu = objectMapper.readValue(storyResult.getResponse().getContentAsString(), Menu.class);
            Assertions.assertEquals(menu.getDescription(), updatedMenu.getDescription());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void delete() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;
            var menu = menuFactory();

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/menu/" + menu.getId().toString())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            Assertions.assertEquals(false, menuService.findById(menu.getId()).isPresent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
