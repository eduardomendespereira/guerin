package br.com.guerin.Service;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.Specie;
import br.com.guerin.Repository.EventType.EventTypeRepository;
import br.com.guerin.Service.IService.IEventTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventTypeService implements IEventTypeService {
    private final EventTypeRepository eventTypeRepository;
    @Transactional
    public EventType save(EventType eventType){
        return eventTypeRepository.save(eventType);
    }
    public Optional<EventType> findById(Long id){
        return this.eventTypeRepository.findById(id);
    }
    public ArrayList<EventType> listAll(){
        return  (ArrayList<EventType>) this.eventTypeRepository.findAll();
    }
    @Transactional
    public EventType update(Long id, EventType eventType){
        if(id == eventType.getId()){
            return this.eventTypeRepository.save(eventType);
        }else{
            throw  new RuntimeException("Erro : Não foi possivel editar a Especie");
        }
    }
    @Transactional
    public void disable(Long id, EventType eventType){
        if(id == eventType.getId()){
            this.eventTypeRepository.desativar(eventType.getId());
        }
    }
    @Transactional
    public void enable(Long id){
        this.eventTypeRepository.enable(id);
    }

    public Optional<EventType> findByName(String nameEvent){
        return this.eventTypeRepository.findByName(nameEvent);
    }
}
