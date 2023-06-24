package br.com.guerin.Controller;

import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.User;
import br.com.guerin.Payload.User.ResultTokens;
import br.com.guerin.Service.IService.IUserService;
import br.com.guerin.Service.NotificationService;
import br.com.guerin.Utils.GetToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

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

    private User userFactoryUser() {
        User user = new User(
                "lucas",
                "hanke",
                "lucas1@email.com",
                "lucas",
                "123",
                Role.user
        );
        if (this.userService.findByUsername(user.getUsername()).isPresent())
            return this.userService.findByUsername(user.getUsername()).get();
        NotificationService notificationService = new NotificationService();
        return this.userService.save(user, notificationService);
    }

    @Test
    public void listAll() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;
            MvcResult storyResult = mockMvc.perform(get("/api/users").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            Assertions.assertEquals(HttpStatus.OK.value(), storyResult.getResponse().getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void registerUserForbidden() {
        try {
            User user = this.userFactoryUser();
            String token = getToken.getToken(user, "123").access_token;

            String postValue = objectMapper.writeValueAsString(user);

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/user/register")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isForbidden())
                    .andDo(print())
                    .andReturn();
            Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), storyResult.getResponse().getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void register() {
        try {
            User userAdmin = this.userFactoryAdmin();

            User user = new User(
                    "lucas6",
                    "hanke6",
                    "lucas6@email.com",
                    "lucas6",
                    "123",
                    Role.user
            );

            User createdUser = null;
            if (userService.findByUsername(user.getUsername()).isPresent())
                createdUser = userService.findByUsername(user.getUsername()).get();
            if (createdUser == null) {
                String token = getToken.getToken(userAdmin, "123").access_token;
                String postValue = objectMapper.writeValueAsString(user);

                MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                                .post("/api/user/register")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(postValue))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn();

                createdUser = objectMapper.readValue(storyResult.getResponse().getContentAsString(), User.class);
            }
            Assertions.assertEquals(user.getUsername(), createdUser.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateUserForbidden() {
        try {
            User user = this.userFactoryUser();

            String token = getToken.getToken(user, "123").access_token;
            user.setFirstName("lucas123");
            String postValue = objectMapper.writeValueAsString(user);

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/user/update")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isForbidden())
                    .andDo(print())
                    .andReturn();

            Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), storyResult.getResponse().getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void updateUser() {
        try {
            User user = this.userFactoryAdmin();

            String token = getToken.getToken(user, "123").access_token;

            user.setFirstName("lucas123");
            String postValue = objectMapper.writeValueAsString(user);

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/user/update")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            var updatedUser = objectMapper.readValue(storyResult.getResponse().getContentAsString(), User.class);

            Assertions.assertEquals(user.getFirstName(), updatedUser.getFirstName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void disableUserForbidden() {
        try {
            User user = this.userFactoryUser();
            String token = getToken.getToken(user, "123").access_token;

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/user/disable" + user.getId().toString())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isForbidden())
                    .andDo(print())
                    .andReturn();

            Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), storyResult.getResponse().getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void disableUser() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/user/disable/" + user.getId().toString())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            Assertions.assertEquals(true, userService.findByUsername(user.getUsername()).get().isInactive());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void refreshToken() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/refresh-token")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var refreshToken = objectMapper.readValue(storyResult.getResponse().getContentAsString(), ResultTokens.class);
            MvcResult storyResultList = mockMvc.perform(get("/api/users").header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken.getAccess_token()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            Assertions.assertEquals(HttpStatus.OK.value(), storyResultList.getResponse().getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void findByUsername() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/user/get-by-username?" + "username=" + user.getUsername())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            var userByUsername = objectMapper.readValue(storyResult.getResponse().getContentAsString(), User.class);
            Assertions.assertEquals(user.getUsername(), userByUsername.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void findByEmail() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/user/get-by-email?" + "email=" + user.getEmail())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            var userByUsername = objectMapper.readValue(storyResult.getResponse().getContentAsString(), User.class);
            Assertions.assertEquals(user.getUsername(), userByUsername.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void findById() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/user/" + user.getId())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            var userByUsername = objectMapper.readValue(storyResult.getResponse().getContentAsString(), User.class);
            Assertions.assertEquals(user.getUsername(), userByUsername.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
