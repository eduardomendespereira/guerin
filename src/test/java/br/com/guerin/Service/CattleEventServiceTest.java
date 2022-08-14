package br.com.guerin.Service;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.IService.*;
import jdk.jfr.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    private CattleEvent cattleEventVaccineFactory() {
        var cattle = new Cattle(123L, 300F,
                specieService.findByName("Nelore").get(), farmService.findByName("Fazenda Generica").get(),
                Gender.male, 124L, 125L);

        var vaccine = new Vaccine();
        vaccine.setName("Raiva");
        vaccine.setRequired(true);
        var vac = vaccineApp.save(vaccine);

        var eventType = new EventType();
        eventType.setName("VACINACAO");
        var event = eventTypeService.save(eventType);

        var vaccineApp = new VaccineApplication();
        vaccineApp.setDate(LocalDateTime.now());
        //vaccineApp.setCattle(cattle);
        vaccineApp.setDate(LocalDateTime.now());
        vaccineApp.setVaccine(vac);

        var cattleEvent = new CattleEvent();
        cattleEvent.setCattle(cattle);
        cattleEvent.setDate(LocalDateTime.now());
        cattleEvent.setVaccineApplication(vaccineApp);
        cattleEvent.setEventType(event);
        cattleEvent.setDescription("Aplicacao da vacina 'RAIVA' no Gado com brinco: '123'");

        return cattleEventService.save(cattleEvent);
    }

    private CattleEvent cattleEventWeighingFactory() {
        generateSpecieAndFarm();

        var cattle = new Cattle(123L, 300F,
                specieService.findByName("Nelore").get(), farmService.findByName("Fazenda Generica").get(),
                Gender.male, 124L, 125L);

        var weighing = new Weighing();
        weighing.setCattle(cattle);
        weighing.setDate(LocalDateTime.now());
        weighing.setWeight(200F);
        var wei = weighingService.save(weighing);

        var eventType = new EventType();
        eventType.setName("PESAGEM");
        var event = eventTypeService.save(eventType);

        var cattleEvent = new CattleEvent();
        cattleEvent.setCattle(cattle);
        cattleEvent.setDate(LocalDateTime.now());
        cattleEvent.setWeighing(wei);
        cattleEvent.setDescription("Pesagem do Gado com brinco: '123', Peso Atual: 200");
        cattleEvent.setEventType(event);

        return cattleEventService.save(cattleEvent);
    }
    public void generateSpecieAndFarm() {
        if (farmService.findByName("Fazenda Generica") == null)
            farmService.save(new Farm("Fazenda Generica", "Meio do mato"));
        if (specieService.findByName("Nelore") == null)
            specieService.save(new Specie("Nelore"));
    }

    private final CattleEvent cattleEventVaccine = this.cattleEventVaccineFactory();
    private final CattleEvent cattleEventWeighing = this.cattleEventWeighingFactory();
}
