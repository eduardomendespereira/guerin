package br.com.guerin.Controller;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.IService.*;
import br.com.guerin.Utils.GetToken;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CattleEventControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFarmService farmService;

    @Autowired
    private ISpecieService specieService;

    @Autowired
    private ICattleService cattleService;

    @Autowired
    private IEventTypeService eventTypeService;

    @Autowired
    private ICattleEventService cattleEventService;

    @Autowired
    private IVaccineApplicationService vaccineApplicationService;

    @Autowired
    private IWeighingService weighingService;

    @Autowired
    private IVaccineService vaccineService;

    private final GetToken getToken = new GetToken();

    public void generateSpecieAndFarm() {
        if (!farmService.findByName("Fazenda Qualquer").isPresent())
            farmService.save(new Farm("Fazenda Qualquer", "Rondonia"));
        if (!specieService.findByName("Brandus").isPresent())
            specieService.save(new Specie("Brandus"));
    }

    public Cattle cattleFactory(){
        this.generateSpecieAndFarm();
        Cattle cattle = new Cattle(
                123L,
                300F,
                specieService.findByName("Brandus").get(),
                farmService.findByName("Fazenda Qualquer").get(),
                Gender.male,
                null,
                null);
        if(cattleService.findByEarring(cattle.getEarring()).isPresent()){
            return cattleService.findByEarring(cattle.getEarring()).get();
        }
        return cattleService.save(cattle);
    }

    public EventType eventTypeFactory(){
        EventType eventType = new EventType("Visita veterinaria");
        if (eventTypeService.findByName(eventType.getName()).isPresent()){
            return eventTypeService.findByName(eventType.getName()).get();
        }
        return this.eventTypeService.save(eventType);
    }

    private Vaccine vaccineFactory(){
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Micose vacitec");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(false);
        if(this.vaccineService.findByName(vaccine.getName()).isPresent()){
            return this.vaccineService.findByName(vaccine.getName()).get();
        }
        return this.vaccineService.save(vaccine);
    }
    private VaccineApplication vaccineAppFactory(){
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Cattle cattle = this.cattleFactory();
        Vaccine vaccine = this.vaccineFactory();
        VaccineApplication vaccineApplication = new VaccineApplication();
        vaccineApplication.setNote("Aplicacao de vac micose");
        vaccineApplication.setDate(LocalDateTime.now());
        vaccineApplication.setVaccine(vaccine);
        vaccineApplication.setCattle(cattle);
        if(vaccineApplicationService.findByNote(vaccineApplication.getNote()).isPresent()){
            return vaccineApplicationService.findByNote(vaccineApplication.getNote()).get();
        }
        return vaccineApplicationService.save(vaccineApplication);
    }

    private Weighing weighingFactory(){
        Cattle cattle = this.cattleFactory();
        Weighing weighing = new Weighing(
            cattle,
            LocalDateTime.now(),
            150F
        );
        return weighingService.save(weighing);
    }

    private User userFactory(){
        User user = new User(
                "falano",
                "de ciclano",
                "ciclano@email.com",
                "ciclano",
                "123",
                Role.admin
        );
        if (this.userService.findByUsername(user.getUsername()).isPresent()) {
            return this.userService.findByUsername(user.getUsername()).get();
        }
        return this.userService.save(user);
    }

    private CattleEvent cattleEventFactory(){
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        EventType eventType = this.eventTypeFactory();
        Cattle cattle = this.cattleFactory();
        CattleEvent cattleEvent = new CattleEvent(
                cattle,
                eventType,
                LocalDateTime.now(),
                "Aplicação de vacina contra carbunculo"
        );
        if (this.cattleEventService.findByName(cattleEvent.getDescription()).isPresent()){
            return this.cattleEventService.findByName(cattleEvent.getDescription()).get();
        }
        return this.cattleEventService.save(cattleEvent);
    }

    @Test
    @DisplayName("teste listAll")
    public void listAll(){
        User user = this.userFactory();
        String token = this.getToken.getToken(user, "123").access_token;
        try {
            this.mockMvc.perform(get("/api/cattleEvent").header(HttpHeaders.AUTHORIZATION, "Bearer "
                            + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findById(){
        try {
            CattleEvent cattleEvent = this.cattleEventFactory();
            User user = this.userFactory();
            String token = getToken.getToken(user, "123").access_token;
            mockMvc.perform(get("/api/cattleEvent/" +
                            cattleEvent.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findByEventType(){
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Cattle cattle = cattleFactory();
        EventType eventType = new EventType("vacinacao");
        var newEventType = eventTypeService.save(eventType);
        CattleEvent cattleEvent1 = new CattleEvent(
                cattle,
                newEventType,
                LocalDateTime.now(),
                "App de vacina"
        );
        var cattleEvent = cattleEventService.save(cattleEvent1);
        try {
            User user = this.userFactory();
            String token = getToken.getToken(user, "123").access_token;
            MvcResult storyResult = mockMvc.perform(get("/api/cattleEvent/eventType/" +
                            cattleEvent.getEventType().getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            CattleEvent ce = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(), CattleEvent[].class)[0];
            Assertions.assertEquals(cattleEvent.getEventType().getName(), ce.getEventType().getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void disable(){
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        User user = this.userFactory();
        String token = getToken.getToken(user, "123").access_token;
        Cattle cattle = cattleFactory();
        EventType eventType = new EventType("Visita Veterinario");
        var newEventType = eventTypeService.save(eventType);
        CattleEvent cattleEvent1 = new CattleEvent(
                cattle,
                newEventType,
                LocalDateTime.now(),
                "Vist. Veterin."

        );
        var cattleEvent = cattleEventService.save(cattleEvent1);
        try{
            String postValue = objectMapper.writeValueAsString(cattleEvent);
            mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/cattleEvent/disable/" + cattleEvent.getId())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findByWeighing(){
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        User user = this.userFactory();
        String token = this.getToken.getToken(user, "123").access_token;
        Cattle cattle = cattleFactory();
        EventType eventType = new EventType("pesagem");
        var newEventType = eventTypeService.save(eventType);
        var newWeigh = weighingFactory();
        CattleEvent cattleEvent1 = new CattleEvent(
                cattle,
                newEventType,
                LocalDateTime.now(),
                "Evento de pesagem",
                newWeigh
        );
        var cattleEvent = cattleEventService.save(cattleEvent1);
        try {
            MvcResult storyResult =  this.mockMvc.perform(get("/api/cattleEvent/weighing/" + cattleEvent
                            .getWeighing().getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findByVaccineApplication(){
        try {
            CattleEvent cattleEvent = cattleEventFactory();
            cattleEvent.setVaccineApplication(vaccineAppFactory());
            User user = this.userFactory();
            String token = getToken.getToken(user, "123").access_token;
            mockMvc.perform(get("/api/cattleEvent/vacineApp/" + cattleEvent.getVaccineApplication().getId())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findByCattle(){
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Cattle cattle = cattleFactory();
        EventType eventType = new EventType("Visit Veterinario");
        var newEventType = eventTypeService.save(eventType);
        CattleEvent cattleEvent1 = new CattleEvent(
                cattle,
                newEventType,
                LocalDateTime.now(),
                "Vis. Veter."

        );
        var cattleEvent = cattleEventService.save(cattleEvent1);
        User user = this.userFactory();
        String token = getToken.getToken(user, "123").access_token;
        try {
            MvcResult storyResult = mockMvc.perform(get("/api/cattleEvent/cattle/" + cattleEvent.getCattle()
                            .getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            CattleEvent ce = this.objectMapper.readValue(storyResult.getResponse().getContentAsString(),
                    CattleEvent[].class)[0];
            Assertions.assertEquals(cattleEvent.getCattle(), ce.getCattle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void save(){
        try {
            User user = this.userFactory();
            String token = getToken.getToken(user, "123").access_token;
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            Cattle cattle = this.cattleFactory();
            EventType eventType = this.eventTypeFactory();
            CattleEvent cattleEvent = new CattleEvent(
                    cattle,
                    eventType,
                    LocalDateTime.now(),
                    "Aplicação de vacina contra carbunculo"
            );

            String postValue = objectMapper.writeValueAsString(cattleEvent);

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/cattleEvent")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var createdCattleEvent = objectMapper.readValue(storyResult.getResponse().getContentAsString(), CattleEvent.class);

            Assertions.assertNotNull(createdCattleEvent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void update(){
        try{
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            User user = this.userFactory();
            String token = getToken.getToken(user, "123").access_token;
            CattleEvent cattleEvent = this.cattleEventFactory();
            cattleEvent.setDescription("aplicacao de vacina para vermes");
            String postValue = objectMapper.writeValueAsString(cattleEvent);

            MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/cattleEvent/")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postValue))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            var updatedCattleEvent = objectMapper.readValue(storyResult.getResponse().getContentAsString(), CattleEvent.class);
            Assertions.assertEquals(cattleEvent.getDescription(), updatedCattleEvent.getDescription());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
