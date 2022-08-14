package br.com.guerin.Entity;

import br.com.guerin.Service.IService.ICattleService;
import br.com.guerin.Service.IService.IFarmService;
import br.com.guerin.Service.IService.ISpecieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
public class CattleEventTest {
    @Autowired
    ISpecieService specieService;

    @Autowired
    IFarmService farmService;
    private CattleEvent cattleEventFactory() {
        var cattle = new Cattle(123L, 300F,
                specieService.findByName("Nelore").get(), farmService.findByName("Fazenda Generica").get(),
                Gender.male, 124L, 125L);

        var vaccine = new Vaccine();
        vaccine.setName("Raiva");
        vaccine.setRequired(true);

        var vaccineApp = new VaccineApplication();
        vaccineApp.setDate(LocalDateTime.now());
        //vaccineApp.setCattle(cattle);
        vaccineApp.setDate(LocalDateTime.now());
        vaccineApp.setVaccine(vaccine);

        var cattleEvent = new CattleEvent();
        cattleEvent.setCattle(cattle);
        cattleEvent.setDate(LocalDateTime.now());
        cattleEvent.setVaccineApplication(vaccineApp);
        cattleEvent.setDescription("Aplicacao da vacina 'RAIVA' no Gado com brinco: '123'");

        return cattleEvent;
    }
    private final CattleEvent cattleEvent = this.cattleEventFactory();

    @Test
    public void testIfCattleEventIsNotInactiveByDefault() {
        Assertions.assertFalse(this.cattleEvent.isInactive());
    }
}
