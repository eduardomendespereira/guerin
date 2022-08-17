package br.com.guerin.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VaccineControllerTest {

    //UserResource user = new UserResource("Zaphod", "zaphod@galaxy.net");

   //mockMvc.perform(post("/forums/{forumId}/register", 42L)
     //   .contentType("application/json")
       // .param("sendWelcomeMail", "true")
       // .content(objectMapper.writeValueAsString(user)))
         //   .andExpect(status().isOk());
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkListAll() throws Exception {

        this.mockMvc.perform(get("/api/vaccines")).andDo(print()).andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string(containsString("")));
    }

    @Test
    public void
}
