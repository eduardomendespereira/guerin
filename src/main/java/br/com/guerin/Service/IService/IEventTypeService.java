package br.com.guerin.Service.IService;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.Specie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

public interface IEventTypeService {
    EventType save(EventType eventType);
    EventType update(Long id, EventType eventType);
    ArrayList<EventType> listAll();
    Optional<EventType> findById(Long id);
    void disable(Long id, EventType eventType);
   Optional<EventType> findByName(String nameEvent);
}
