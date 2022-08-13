package br.com.guerin.Service.IService;

import br.com.guerin.Entity.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEventTypeService {
    void save(EventType eventType);
    void inactivate(Long id, EventType eventType);
    void update(Long id, EventType eventType);
    Page<EventType> listAll(Pageable pageable);
    EventType findById(Long id);
}
