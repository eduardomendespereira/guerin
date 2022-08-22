package br.com.guerin.Controller;

import br.com.guerin.Entity.Parameter;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.IService.IParameterService;
import br.com.guerin.Service.IService.IUserService;
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
public class ParameterControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IParameterService parameterService;
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
        return this.userService.save(user);
    }

    private Parameter parameterFactory() {
        var param = new Parameter();
        param.setDescription("parametro teste");
        param.setValue("59");
        param.setId("ID_EVENTO_VACINACAO");
        param.setIncluded(LocalDateTime.now());
        if (this.parameterService.findById(param.getId()).isPresent())
            return this.parameterService.findById(param.getId()).get();
        return this.parameterService.save(param);
    }

    @Test
    public void listAll() {
        try {
            User user = this.userFactoryAdmin();
            var param = parameterFactory();
            String token = getToken.getToken(user, "123").access_token;
            MvcResult storyResult = mockMvc.perform(get("/api/parameter").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
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
            var param = parameterFactory();
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;
            MvcResult storyResult = mockMvc.perform(get("/api/parameter/" + param.getId().toString()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var parameter = objectMapper.readValue(storyResult.getResponse().getContentAsString(), Parameter.class);
            Assertions.assertEquals(param.getId(), parameter.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void save() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;

            var param = new Parameter();
            param.setDescription("parametro teste 2");
            param.setValue("60");
            param.setId("ID_EVENTO_PESAGEM");
            param.setIncluded(LocalDateTime.now());

            String postValue = objectMapper.writeValueAsString(param);

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/parameter")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var createdParam = objectMapper.readValue(storyResult.getResponse().getContentAsString(), Parameter.class);

            Assertions.assertEquals(param.getId(), createdParam.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void update() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;
            var param = parameterFactory();
            param.setValue("99");
            String postValue = objectMapper.writeValueAsString(param);
            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/parameter")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var updatedParam = objectMapper.readValue(storyResult.getResponse().getContentAsString(), Parameter.class);
            Assertions.assertEquals(param.getValue(), updatedParam.getValue());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void delete() {
        try {
            User user = this.userFactoryAdmin();
            String token = getToken.getToken(user, "123").access_token;
            var param = parameterFactory();

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/parameter/" + param.getId().toString())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            Assertions.assertEquals(false, parameterService.findById(param.getId()).isPresent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
