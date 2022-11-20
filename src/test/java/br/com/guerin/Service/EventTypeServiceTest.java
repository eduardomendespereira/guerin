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
        EventType eventType = new EventType("1g2g");
        eventTypeService.save(eventType);
        List<EventType> eventTypeList = new ArrayList<EventType>();
        for(EventType eventType1 : eventTypeService.listAll()){
            if(eventType1.getName().contains("1g2g")){
                eventTypeList.add(eventType);
            }
        }
        Assertions.assertEquals("1g2g", eventTypeList.get(0).getName());
    }
    @Test
    public void updateEventType(){
        EventType eventType = eventTypeService.save(new EventType("GGGG"));
        Assertions.assertNotNull(eventType);
        String temp = eventType.getName();
        eventType.setName("Fipo");
        eventTypeService.update(eventType.getId(), eventType);
        Assertions.assertTrue(temp != eventTypeService.findById(eventType.getId()).get().getName());
    }

    @Test
    public void inactivateEventType(){

        EventType eventType = eventTypeService.save(new EventType("G4G5"));
        Assertions.assertFalse(eventType.isInactive());
        eventTypeService.disable(eventType.getId(), eventType);
        EventType eventType1 = eventTypeService.findById(eventType.getId()).get();
        Assertions.assertTrue(eventType1.isInactive());
    }

    @Test
    public void findByIdEventType() {
       EventType eventType1 = eventTypeService.save(new EventType("ASAS"));
       EventType eventType = eventTypeService.findById(eventType1.getId()).get();
       Assertions.assertNotNull(eventType);
    }
    @Test
    public void listAllEventType(){
        if(eventTypeService.listAll().isEmpty()){
            eventTypeService.save(new EventType("Fidelis"));
        }
        Assertions.assertNotNull(eventTypeService.listAll());
    }
}
