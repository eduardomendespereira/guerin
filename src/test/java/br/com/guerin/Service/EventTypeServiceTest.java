package br.com.guerin.Service;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Repository.EventType.EventTypeRepository;
import br.com.guerin.Service.IService.IEventTypeService;
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
    IEventTypeService eventTypeService;
    @Autowired
    EventTypeRepository eventTypeRepository;
    @Test
    public void insertEventType(){
        EventType eventType = new EventType("1g");
        eventTypeService.save(eventType);
        List<EventType> eventTypeList = new ArrayList<EventType>();
        for(EventType eventType1 : eventTypeService.listAll(Pageable.unpaged())){
            if(eventType1.getName().contains("1g")){
                eventTypeList.add(eventType);
            }
        }
        Assertions.assertEquals("1g", eventTypeList.get(0).getName());
    }
    @Test
    public void updateEventType(){
        EventType eventType1 = eventTypeRepository.findById(1L).orElse(new EventType("Tste"));
        Assertions.assertNotNull(eventType1);
        eventType1.setName("Tyr");
        eventTypeService.save(eventType1);
        EventType eventType =  eventTypeService.findById(1L);
        Assertions.assertEquals("Tyr", eventType1.getName());
    }

    @Test
    public void inactivateEventType(){
        EventType eventType = eventTypeRepository.findById(1L).orElse(new EventType("Tst1"));
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
