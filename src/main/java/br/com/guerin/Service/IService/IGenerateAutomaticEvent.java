package br.com.guerin.Service.IService;

import br.com.guerin.Entity.*;

import java.util.Optional;

public interface IGenerateAutomaticEvent {
    CattleEvent saveCattleEventVaccination(CattleEvent cattle);
    EventType saveEventType(EventType eventType);
    Optional<EventType> findByNameEventType(String nameEvent);
    CattleEvent generateCattleEventVaccination(VaccineApplication vaccineApplication);
    CattleEvent generateCattleEventWeighing(Weighing weighing);

    CattleEvent generateCattleEventInsemination(Insemination insemination);
}
