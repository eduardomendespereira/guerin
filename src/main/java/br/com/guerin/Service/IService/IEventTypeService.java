package br.com.guerin.Service.IService;

import br.com.guerin.Entity.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IEventTypeService {
    EventType save(EventType eventType);
    EventType update(Long id, EventType eventType);
    Page<EventType> listAll(Pageable pageable);
    Optional<EventType> findById(Long id);
    void disable(Long id, EventType eventType);
}
