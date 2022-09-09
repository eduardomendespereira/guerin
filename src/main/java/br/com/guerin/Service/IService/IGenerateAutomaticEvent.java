package br.com.guerin.Service.IService;

import br.com.guerin.Entity.CattleEvent;
import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.VaccineApplication;

import java.util.Optional;

public interface IGenerateAutomaticEvent {
    CattleEvent saveCattleEventVaccination(CattleEvent cattle);
    EventType saveEventType(EventType eventType);
    Optional<EventType> findByNameEventType(String nameEvent);
    CattleEvent generateCattleEventVaccination(VaccineApplication vaccineApplication);
}
