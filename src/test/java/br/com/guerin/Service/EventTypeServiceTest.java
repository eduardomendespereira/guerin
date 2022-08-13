package br.com.guerin.Service;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Repository.EventType.EventTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class EventTypeServiceTest {
    @Autowired
    EventTypeService eventTypeService;
    @Autowired
    EventTypeRepository eventTypeRepository;
    @Test
    public void insertEventType(){
        EventType eventType = new EventType("Test");
        eventTypeService.save(eventType);
        List<EventType> eventTypeList = new ArrayList<EventType>();
        for(EventType eventType1 : eventTypeService.listAll(Pageable.unpaged())){
            if(eventType1.getName().contains("Test")){
                eventTypeList.add(eventType);
            }
        }
        Assertions.assertEquals("Test", eventTypeList.get(0).getName());
    }
    @Test
    public void updateEventType(){
        EventType eventType1 = eventTypeRepository.findById(1L).orElse(new EventType("Tst"));
        Assertions.assertNotNull(eventType1);
        eventType1.setName("Ty");
        eventTypeService.save(eventType1);
        EventType eventType =  eventTypeService.findById(1L);
        Assertions.assertEquals("Ty", eventType1.getName());
    }

    @Test
    public void inactivateEventType(){
        EventType eventType = eventTypeService.findById(1L);
        Assertions.assertFalse(eventType.isInactive());
        eventType.setInactive(true);
        eventTypeService.save(eventType);
        EventType eventType1 = eventTypeService.findById(1L);
        Assertions.assertTrue(eventType1.isInactive());

    }

    @Test
    public void findByIdEventType(){
        EventType eventType = eventTypeService.findById(1L);
        Assertions.assertNotNull(eventType);
    }
    @Test
    public void listAllEventType(){
        Assertions.assertNotNull(eventTypeService.listAll(Pageable.unpaged()));
    }
}
