package br.com.guerin.Controller;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Repository.Vaccine.VaccineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import java.time.LocalDateTime;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = VaccineController.class)
public class VaccineControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VaccineRepository vaccineRepository;

    @Test
    public void checkListAll() throws Exception {
        Vaccine vaccine = new Vaccine();
        vaccine.setName("carbunculo");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        List<Vaccine> vaccinesList = List.of(vaccine);
        when(vaccineRepository.findAll()).thenReturn(vaccinesList);
        this.mockMvc.perform(get("/api/vaccines"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string(containsString("carbunculo")));
    }
}
