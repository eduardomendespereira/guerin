package br.com.guerin.Controller;

import br.com.guerin.Entity.Menu;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.RoleMenu;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.IService.IMenuService;
import br.com.guerin.Service.IService.IRoleMenuService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleMenuControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleMenuService roleMenuService;
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

    private RoleMenu roleMenuFactory() {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setMenu(menuFactory());
        roleMenu.setRole(Role.admin);
        if (roleMenuService.findByMenuAndRole(menuFactory(), Role.admin).isPresent())
            return roleMenuService.findByMenuAndRole(menuFactory(), Role.admin).get();
        return roleMenuService.save(roleMenu);
    }

    @Test
    public void listAll() {
        try {
            User user = this.userFactoryAdmin();
            var roleMenu = roleMenuFactory();
            String token = getToken.getToken(user, "123").access_token;
            MvcResult storyResult = mockMvc.perform(get("/api/rolemenu").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
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
            var roleMenu = roleMenuFactory();
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;
            MvcResult storyResult = mockMvc.perform(get("/api/rolemenu/" + roleMenu.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var menuObj = objectMapper.readValue(storyResult.getResponse().getContentAsString(), RoleMenu.class);
            Assertions.assertEquals(roleMenu.getId(), menuObj.getId());
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
            menu.setName("Gados");
            menu.setPath("/gados");
            menu.setDescription("Cadastro de Gados");
            if (menuService.findByName(menu.getName()).isPresent())
                menu = menuService.findByName(menu.getName()).get();
            else
                menu = menuService.save(menu);

            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenu(menu);
            roleMenu.setRole(Role.user);

            String postValue = objectMapper.writeValueAsString(roleMenu);

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/rolemenu")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var createdMenu = objectMapper.readValue(storyResult.getResponse().getContentAsString(), RoleMenu.class);

            Assertions.assertEquals(roleMenu.getMenu().getName(), createdMenu.getMenu().getName());
            Assertions.assertEquals(roleMenu.getRole().value, createdMenu.getRole().value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void delete() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;
            var roleMenu = roleMenuFactory();

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/rolemenu/" + roleMenu.getId().toString())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            Assertions.assertEquals(false, menuService.findById(roleMenu.getId()).isPresent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
