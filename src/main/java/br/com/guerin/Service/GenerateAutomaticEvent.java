package br.com.guerin.Service;

import br.com.guerin.Entity.*;
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
        CattleEvent cattleEventVaccination = new CattleEvent();
        EventType getEventType = this.generateEventTypeVaccination();
        if (!cattleEventRepository.findByVaccineApplication(vaccineApplication.getId()).isPresent()){
            cattleEventVaccination.setCattle(vaccineApplication.getCattle());
            cattleEventVaccination.setEventType(getEventType);
            cattleEventVaccination.setVaccineApplication(vaccineApplication);
            cattleEventVaccination.setDate(vaccineApplication.getDate());
            cattleEventVaccination.setDescription("Aplicação de vacina " + vaccineApplication.getVaccine().getName());
            return cattleEventRepository.save(cattleEventVaccination);
        }else {
            var event = cattleEventRepository.findByVaccineApplication(vaccineApplication.getId()).get();
            event.setCattle(vaccineApplication.getCattle());
            event.setDate(vaccineApplication.getDate());
            event.setVaccineApplication(vaccineApplication);
            return cattleEventRepository.save(event);
        }
    }

    private EventType generayeEventTypeWeighing(){
        EventType eventTypeWeighing = new EventType(
                "Pesagem do gado"
        );
        if(!findByNameEventType(eventTypeWeighing.getName()).isPresent()){
            saveEventType(eventTypeWeighing);
        }
        return findByNameEventType(eventTypeWeighing.getName()).get();
    }

    public CattleEvent generateCattleEventWeighing(Weighing weighing){
        EventType getEventType = this.generayeEventTypeWeighing();
        Optional<CattleEvent> event = this.cattleEventRepository.findByWeighingById(weighing.getId());

        if(!event.isPresent()){
            CattleEvent cattleEventWeighing = new CattleEvent();
            cattleEventWeighing.setCattle(weighing.getCattle());
            cattleEventWeighing.setEventType(getEventType);
            cattleEventWeighing.setDate(weighing.getDate());
            cattleEventWeighing.setWeighing(weighing);
            cattleEventWeighing.setDescription("Pesagem do gado " + weighing.getCattle().getEarring());
            return this.cattleEventRepository.save(cattleEventWeighing);
        }else {
            var cattleEvent = this.cattleEventRepository.findByWeighingById(weighing.getId()).get();
            cattleEvent.setCattle(weighing.getCattle());
            cattleEvent.setDate(weighing.getDate());
            cattleEvent.setWeighing(weighing);
            return cattleEventRepository.save(cattleEvent);
        }
    }

    private EventType generayeEventTypeInsemination(){
        EventType eventTypeInsemination = new EventType(
                "Inseminação"
        );
        if(!findByNameEventType(eventTypeInsemination.getName()).isPresent()){
            saveEventType(eventTypeInsemination);
        }
        return findByNameEventType(eventTypeInsemination.getName()).get();
    }

    public CattleEvent generateCattleEventInsemination(Insemination insemination){
        EventType getEventType = this.generayeEventTypeInsemination();
        CattleEvent cattleEventInsemination = new CattleEvent();
        if(!this.cattleEventRepository.findByInseminationById(insemination.getId()).isPresent()){
            cattleEventInsemination.setCattle(insemination.getCattle());
            cattleEventInsemination.setEventType(getEventType);
            cattleEventInsemination.setDate(insemination.getDate());
            cattleEventInsemination.setInsemination(insemination);
            cattleEventInsemination.setDescription("Inseminação do Gado: " + insemination.getCattle().getEarring());
            return this.cattleEventRepository.save(cattleEventInsemination);
        }else {
            var event = this.cattleEventRepository.findByInseminationById(insemination.getId()).get();
            event.setCattle(insemination.getCattle());
            event.setDate(insemination.getDate());
            event.setInsemination(insemination);
            return cattleEventRepository.save(event);
        }
    }
}
