package br.com.guerin.Service;

import br.com.guerin.Entity.CattleEvent;
import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Repository.CattleEvent.CattleEventRepository;
import br.com.guerin.Repository.EventType.EventTypeRepository;
import br.com.guerin.Service.IService.IGenerateAutomaticEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenerateAutomaticEvent implements IGenerateAutomaticEvent {

    private final EventTypeRepository eventTypeRepository;

    private final CattleEventRepository cattleEventRepository;

    @Transactional
    public CattleEvent saveCattleEventVaccination(CattleEvent cattleEvent) {
        if(!validateTypeEvent(cattleEvent)){
            throw new RuntimeException("O evento possui 2 tipos de evento (vacinação e pesagem)");
        }else{
            return cattleEventRepository.save(cattleEvent);
        }
    }

    public boolean validateTypeEvent(CattleEvent cattleEvent){
        if(cattleEvent.getVaccineApplication() != null && cattleEvent.getWeighing() != null){
            return false;
        }else {
            return true;
        }
    }

    @Transactional
    public EventType saveEventType(EventType eventType){
        return eventTypeRepository.save(eventType);
    }

    public Optional<EventType> findByNameEventType(String nameEvent){
        return this.eventTypeRepository.findByName(nameEvent);
    }

    private EventType generateEventTypeVaccination(){
        EventType eventTypeVaccination = new EventType(
                "Aplicação de Vacina"
        );
        if (!findByNameEventType(eventTypeVaccination.getName()).isPresent()){
            saveEventType(eventTypeVaccination);
        }
        return findByNameEventType(eventTypeVaccination.getName()).get();
    }

    public CattleEvent generateCattleEventVaccination(VaccineApplication vaccineApplication) {
        EventType getEventType = this.generateEventTypeVaccination();
        CattleEvent cattleEventVaccination = new CattleEvent(
                vaccineApplication.getCattle(),
                getEventType,
                vaccineApplication.getDate(),
                "Aplicação de vacina {vaccineApplication.getVaccine().getName()}",
                vaccineApplication
        );

        if(!validateTypeEvent(cattleEventVaccination)){
            throw new RuntimeException("O evento possui 2 tipos de evento (vacinação e pesagem)");
        }else{
            return cattleEventRepository.save(cattleEventVaccination);
        }
    }
}
