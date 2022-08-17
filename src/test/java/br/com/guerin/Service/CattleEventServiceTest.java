package br.com.guerin.Service;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.IService.*;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

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

    private Weighing weighing;

    public void generateEventFactory() {
        this.generateSpecieAndFarm();
        this.cattle = new Cattle(
                123L,
                300F,
                specieService.findByName("Brandus").get(),
                farmService.findByName("Fazenda Qualquer").get(),
                Gender.male,
                124L,
                125L
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

}
