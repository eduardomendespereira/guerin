package br.com.guerin.Service;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Repository.EventType.EventTypeRepository;
import br.com.guerin.Service.IService.IEventTypeService;
import br.com.guerin.Service.IService.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EventTypeServiceTest {
    @Autowired
    IEventTypeService eventTypeService;
    @Autowired
    EventTypeRepository eventTypeRepository;

    IEventTypeService mock = Mockito.mock(EventTypeService.class);

    @Test
    public void insertEventType(){
        EventType eventType = new EventType("1g2g");
        eventTypeService.save(eventType);
        List<EventType> eventTypeList = new ArrayList<EventType>();
        for(EventType eventType1 : eventTypeService.listAll(Pageable.unpaged())){
            if(eventType1.getName().contains("1g2g")){
                eventTypeList.add(eventType);
            }
        }
        Assertions.assertEquals("1g2g", eventTypeList.get(0).getName());
    }
    @Test
    public void updateEventType(){
        EventType eventType = new EventType();
        eventType = eventTypeService.findById(1L).orElse(new EventType("Fidelis"));
        Assertions.assertNotNull(eventType);
        String temp = eventType.getName();
        eventType.setName("Fipo");
        eventTypeService.update(1L, eventType);
        Assertions.assertTrue(temp != eventTypeService.findById(1L).get().getName());
    }

    @Test
    public void inactivateEventType(){
        if(eventTypeService.listAll(Pageable.unpaged()).isEmpty()){
            eventTypeService.save(new EventType("Fidelis"));
        }
        EventType eventType = eventTypeService.findById(1L).get();
        Assertions.assertFalse(eventType.isInactive());
        eventTypeService.disable(1L, eventType);
        EventType eventType1 = eventTypeService.findById(1L).get();
        Assertions.assertTrue(eventType1.isInactive());
    }

    @Test
    public void findByIdEventType() {
       if(eventTypeService.listAll(Pageable.unpaged()).isEmpty()){
           eventTypeService.save(new EventType("Fidelao"));
       }
       EventType eventType = eventTypeService.findById(1L).get();
       Assertions.assertNotNull(eventType);
    }
    @Test
    public void listAllEventType(){
        if(eventTypeService.listAll(Pageable.unpaged()).isEmpty()){
            eventTypeService.save(new EventType("Fidelis"));
        }
        Assertions.assertNotNull(eventTypeService.listAll(Pageable.unpaged()));
    }
}
