package br.com.guerin.Controller;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.EventTypeService;
import br.com.guerin.Service.UserService;
import br.com.guerin.Utils.GetToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.bytebuddy.implementation.bytecode.Throw;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class EventTypeControllerTest {
    @Autowired
    private EventTypeController eventTypeController;
    @Autowired
    private EventTypeService eventTypeService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    private final GetToken gtToken = new GetToken();

    public EventType eventTypeFactory(){
        EventType eventType = new EventType("Fidelao");
        return eventType;
    }

    public EventType eventTypeSaveFactory(){
        if(!eventTypeService.listAll(Pageable.unpaged()).isEmpty()){
            return eventTypeService.findByName("Fidelao").get();
        }
        return eventTypeService.save(new EventType("Fidelin"));
    }
    public User userFactory(){
       User user = new User(
               "user",
               "test",
               "user.test@gmail.com",
               "Us3r",
               "123",
               Role.admin
       );
       if(!userService.findAll(Pageable.unpaged()).isEmpty()){
           return userService.findByUsername("Us3r").get();
       }else {
           return userService.save(user);
       }
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void findAllEventType(){
        User user = userFactory();
        String token = this.gtToken.getToken(user, "123").access_token;

        try {
            this.mockMvc.
                    perform(MockMvcRequestBuilders.
                            get("/api/event_type").
                            accept(MediaType.APPLICATION_JSON).
                            header(HttpHeaders.AUTHORIZATION, "Bearer " + token)).
                    andExpect(status().isOk());


        } catch (Exception e) {
            throw  new RuntimeException();
        }
    }

    @Test
    public void updateEventType(){
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            User user = userFactory();
            EventType eventType = eventTypeSaveFactory();
            eventType.setName("Agadr");
            String token = this.gtToken.getToken(user, "123").access_token;
            String postVal = objectMapper.writeValueAsString(eventType);
            this.mockMvc.perform(put("/api/event_type/" + eventType.getId().toString())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer" + token).content(postVal).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }



    @Test
    public void insertEventType(){
        User user = userFactory();
        String token = this.gtToken.getToken(user, "123").access_token;
        EventType eventType = eventTypeFactory();
        try {
            this.mockMvc.perform(post("/api/event_type").header(HttpHeaders.AUTHORIZATION, "Bearer" + token)
                    .content(asJsonString(eventType)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }


    @Test
    public void findEventTypeById(){
        try {
            User user = userFactory();
            String token = this.gtToken.getToken(user, "123").access_token;
            EventType eventType = eventTypeSaveFactory();
            MvcResult result = mockMvc.perform(get("/api/event_type/" + eventType.getId().toString()).
                    header(HttpHeaders.AUTHORIZATION, "Bearer" + token)).andExpect(status().isOk()).andReturn();
            EventType tempEvent = objectMapper.readValue(result.getResponse().getContentAsString(), EventType.class);
            Assertions.assertEquals(eventType, tempEvent);
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }

    }


    @Test
    public void disableEventType(){
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            User user = userFactory();
            EventType eventType = eventTypeService.save(new EventType("Jonas"));
            String post = objectMapper.writeValueAsString(eventType);
            String token = this.gtToken.getToken(user, "123").access_token;
            MvcResult result = mockMvc.perform(put("/api/event_type/disable/"+ eventType.getId().toString())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer" + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(post))
                    .andExpect(status().isOk())
                    .andReturn();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @DisplayName("Inserindo Nome Existente")
    public void insertAExistentNameForEventType(){
        try {
            User user = userFactory();
            EventType eventType = new EventType();
            if(eventTypeService.listAll(Pageable.unpaged()).isEmpty()){
                eventType = eventTypeService.save(new EventType("Fidelito"));
            }else{
                eventType = eventTypeService.findById(2L).get();
            }
            String token = this.gtToken.getToken(user, "123").access_token;
            EventType eventType1 = new EventType();
            eventType1.setName(eventType.getName());
            this.mockMvc.perform(post("/api/event_type").header(HttpHeaders.AUTHORIZATION, "Bearer" + token)
                            .content(asJsonString(eventType1)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
