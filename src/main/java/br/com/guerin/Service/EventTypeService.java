package br.com.guerin.Service;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Repository.EventType.EventTypeRepository;
import br.com.guerin.Service.IService.IEventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EventTypeService implements IEventTypeService {
    @Autowired
    private EventTypeRepository eventTypeRepository;
    @Transactional
    public void save(EventType eventType){
        eventTypeRepository.save(eventType);
    }
    public EventType findById(Long id){
        return this.eventTypeRepository.findById(id).orElse(new EventType());
    }

    public Page<EventType> listAll(Pageable pageable){

        return this.eventTypeRepository.findAll(pageable);
    }
    @Transactional
    public void update(Long id, EventType eventType){
        if(id == eventType.getId()){
            this.eventTypeRepository.save(eventType);
        }else{
            throw  new RuntimeException("Erro : Não foi possivel editar a Especie");
        }
    }




    @Transactional
    public void inactivate(Long id, EventType eventType){
        if(id == eventType.getId()){
            this.eventTypeRepository.desativar(eventType.getId());
        }
    }
}
