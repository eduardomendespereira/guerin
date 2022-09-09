package br.com.guerin.Service;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.IService.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CattleEventServiceTest {

    @Autowired
    ISpecieService specieService;
    @Autowired
    IFarmService farmService;
    @Autowired
    ICattleEventService cattleEventService;
    @Autowired
    IEventTypeService eventTypeService;
    @Autowired
    IWeighingService weighingService;
    @Autowired
    IVaccineService vaccineApp;
    @Autowired
    IVaccineService vaccineService;
    @Autowired
    ICattleService cattleService;
    @Autowired
    IVaccineApplicationService vaccineApplicationService;

    private Cattle cattle;
    private Vaccine vaccine;
    private VaccineApplication vaccineApplication;
    private EventType eventTypeVaccine;
    private EventType eventTypeWeighing;

    private EventType eventRandom;
    private Weighing weighing;

    public void generateEventFactory() {
        this.generateSpecieAndFarm();
        this.cattle = new Cattle(
                123L,
                300F,
                specieService.findByName("Brandus").get(),
                farmService.findByName("Fazenda Qualquer").get(),
                Gender.male,
                null,
                null
        );
        this.vaccine = new Vaccine(
                "carbunculo",
                LocalDateTime.now(),
                true
        );
        this.vaccineApplication = new VaccineApplication(
                "Aplicação de vacina para carbunculo",
                vaccine,
                LocalDateTime.now(),
                cattle
        );
        this.eventTypeVaccine = new EventType(
                "Vacinação"
        );
        this.eventTypeWeighing = new EventType(
                "Pesagem"
        );
        this.eventRandom = new EventType(
                "Veterinário"
        );
        this.weighing = new Weighing(
                cattle,
                LocalDateTime.now(),
                150F
        );
    }

    public void generateSpecieAndFarm() {
        if (!farmService.findByName("Fazenda Qualquer").isPresent())
            farmService.save(new Farm("Fazenda Qualquer", "Rondonia"));
        if (!specieService.findByName("Brandus").isPresent())
            specieService.save(new Specie("Brandus"));
    }

    @Test
    @DisplayName("Teste de insercao de evento de vacinacao e findById event")
    @Transactional
    public void checkInsertCattleEventVaccineApplication(){
        generateEventFactory();
        cattleService.save(cattle);
        vaccineService.save(vaccine);
        vaccineApplicationService.save(vaccineApplication);
        eventTypeService.save(eventTypeVaccine);
        CattleEvent eventoDeVacinacao = new CattleEvent(
                cattle,
                eventTypeVaccine,
                LocalDateTime.now(),
                "Aplicação de vacina contra carbunculo",
                vaccineApplication
        );
        cattleEventService.save(eventoDeVacinacao);
        var getEventComparation = cattleEventService.findById(eventoDeVacinacao.getId());
        Assertions.assertEquals(eventoDeVacinacao.getDescription(), getEventComparation.get().getDescription());
    }

    @Test
    @DisplayName("Teste de insercao de evento de pesagem")
    @Transactional
    public void checkInsertCattleEventWeighing(){
        generateEventFactory();
        cattleService.save(cattle);
        eventTypeService.save(eventTypeWeighing);
        weighingService.save(weighing);
        CattleEvent eventoDePesagem = new CattleEvent(
                cattle,
                eventTypeWeighing,
                LocalDateTime.now(),
                "Pesagem do gado",
                weighing
        );
        cattleEventService.save(eventoDePesagem);
        var getEventComparation = cattleEventService.findById(eventoDePesagem.getId());
        Assertions.assertEquals(eventoDePesagem.getDescription(), getEventComparation.get().getDescription());
    }

    @Test
    @DisplayName("Teste de insercao so com o tipo do evento")
    @Transactional
    public void checkInsertCattleEventRandom(){
        generateEventFactory();
        cattleService.save(cattle);
        eventTypeService.save(eventRandom);
        CattleEvent eventoRandom = new CattleEvent(
                cattle,
                eventRandom,
                LocalDateTime.now(),
                "Veterinário para tratar machucado na perna"
        );
        cattleEventService.save(eventoRandom);
        var getEventComparation = cattleEventService.findById(eventoRandom.getId());
        Assertions.assertEquals(eventoRandom.getDescription(), getEventComparation.get().getDescription());
    }

    @Test
    public void checkFindByAll(){
        Assertions.assertNotNull(cattleEventService.findAll(Pageable.unpaged()));
    }

    @Test
    @DisplayName("teste edicao de um evento")
    @Transactional
    public void checkUpdate(){
        generateEventFactory();
        cattleService.save(cattle);
        eventTypeService.save(eventRandom);
        vaccineService.save(vaccine);
        vaccineApplicationService.save(vaccineApplication);
        CattleEvent eventoRandom = new CattleEvent(
                cattle,
                eventRandom,
                LocalDateTime.now(),
                "Exames do veterinário"
        );
        cattleEventService.save(eventoRandom);
        eventoRandom.setEventType(eventTypeVaccine);
        eventoRandom.setVaccineApplication(vaccineApplication);
        cattleEventService.update(eventoRandom.getId(), eventoRandom);
        String getNameVaccineApp = String.valueOf(cattleEventService.findById(eventoRandom.getId()).get()
                .getVaccineApplication().getNote());
        Assertions.assertEquals(getNameVaccineApp, "Aplicação de vacina para carbunculo");
    }

    @Test
    public void checkDisable(){
        generateEventFactory();
        cattleService.save(cattle);
        eventTypeService.save(eventRandom);
        CattleEvent cattleEvent = new CattleEvent(
                cattle,
                eventRandom,
                LocalDateTime.now(),
                "Consulta veterinaria"
        );
        cattleEventService.save(cattleEvent);
        cattleEventService.disable(cattleEvent.getId());
        var cattleEventComparation = cattleEventService.findById(cattleEvent.getId());
        Assertions.assertTrue(cattleEventComparation.get().isInactive());
    }

    @Test
    @Transactional
    public void checkFindByEventType(){
        generateEventFactory();
        cattleService.save(cattle);
        eventTypeService.save(eventRandom);
        CattleEvent newEventCattle = new CattleEvent(
                cattle,
                eventRandom,
                LocalDateTime.now(),
                "new event test"
        );
        cattleEventService.save(newEventCattle);
        List<CattleEvent> getEventType = cattleEventService.findByEventType(newEventCattle.getEventType().getId());
        Assertions.assertTrue(getEventType.contains(newEventCattle));
    }

    @Test
    @Transactional
    public void checkFindByWeighing(){
        generateEventFactory();
        cattleService.save(cattle);
        eventTypeService.save(eventTypeWeighing);
        weighingService.save(weighing);
        CattleEvent eventoDePesagem = new CattleEvent(
                cattle,
                eventTypeWeighing,
                LocalDateTime.now(),
                "Pesagem do gado",
                weighing
        );
        cattleEventService.save(eventoDePesagem);
        CattleEvent getEvWeighing = cattleEventService.findByWeighing(eventoDePesagem.getWeighing().getId()).get();
        Assertions.assertEquals(getEvWeighing.getWeighing(), eventoDePesagem.getWeighing());
    }

    @Test
    @Transactional
    public void checkFindByVaccineApp(){
        generateEventFactory();
        cattleService.save(cattle);
        vaccineService.save(vaccine);
        vaccineApplicationService.save(vaccineApplication);
        eventTypeService.save(eventTypeVaccine);
        CattleEvent eventoDeVacinacao = new CattleEvent(
                cattle,
                eventTypeVaccine,
                LocalDateTime.now(),
                "Aplicação de vacina contra carbunculo",
                vaccineApplication
        );
        cattleEventService.save(eventoDeVacinacao);
        var cattleEvent = this.cattleEventService.findByVaccineApp(eventoDeVacinacao
                .getVaccineApplication().getId());
        Assertions.assertTrue(cattleEvent.contains(eventoDeVacinacao));
    }

    @Test
    @Transactional
    public void checkFindByCattle(){
        generateEventFactory();
        cattleService.save(cattle);
        eventTypeService.save(eventRandom);
        CattleEvent newEventCattle = new CattleEvent(
                cattle,
                eventRandom,
                LocalDateTime.now(),
                "Test find cattle"
        );
        cattleEventService.save(newEventCattle);
        var getCattleEvents = this.cattleEventService.findByCattle(newEventCattle.getCattle().getId());
        Assertions.assertTrue(getCattleEvents.contains(newEventCattle));
    }
}