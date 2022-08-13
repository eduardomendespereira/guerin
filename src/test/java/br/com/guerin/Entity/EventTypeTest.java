package br.com.guerin.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EventTypeTest {
    @Test
    public void creteEventType(){
        EventType eventType = new EventType("Vacs");
        eventType.setInactive(false);
        Assertions.assertEquals("Vacs", eventType.getName());
        Assertions.assertFalse(eventType.isInactive());
    }
    @Test
    public void inactiveEventType(){
        EventType eventType = new EventType("Vacs");
        eventType.setInactive(false);
        Assertions.assertEquals("Vacs", eventType.getName());
        Assertions.assertFalse(eventType.isInactive());
        eventType.setInactive(true);
        Assertions.assertTrue(eventType.isInactive());
    }
    @Test
    public void compareEventType(){
        EventType eventType = new EventType("Vacs");
        eventType.setInactive(true);
        EventType eventType1 = new EventType("Vaq");
        eventType.setInactive(true);
        Assertions.assertFalse(eventType.equals(eventType1));
        eventType1.setName("Vacs");
        Assertions.assertTrue(eventType.equals(eventType1));
    }

}
