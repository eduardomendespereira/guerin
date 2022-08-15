package br.com.guerin.repository;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Repository.EventType.EventTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EventTestRepositoryTest {
    @Autowired
    EventTypeRepository eventTypeRepository;
    @Test
    public void insertEventType(){
        EventType eventType = new EventType("Test");
        eventTypeRepository.save(eventType);
        List<EventType> eventTypeList = new ArrayList<EventType>();
        for(EventType eventType1 : eventTypeRepository.findAll()){
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
        eventTypeRepository.save(eventType1);
        EventType eventType = eventTypeRepository.findById(1L).orElse(new EventType());
        Assertions.assertEquals("Ty", eventType1.getName());

    }

    @Test
    public void inactivateEventType(){
        EventType eventType = eventTypeRepository.findById(1L).orElse(new EventType("Jk"));
        Assertions.assertFalse(eventType.isInactive());
        eventType.setInactive(true);
        eventTypeRepository.save(eventType);
        EventType eventType1 = eventTypeRepository.findById(1L).orElse(new EventType());
        Assertions.assertTrue(eventType1.isInactive());

    }

    @Test
    public void findByIdEventType(){
        EventType eventType = eventTypeRepository.findById(1L).orElse(new EventType("Jo"));
        Assertions.assertNotNull(eventType);
    }
    @Test
    public void listAllEventType(){
        Assertions.assertNotNull(eventTypeRepository.findAll());
    }
}
