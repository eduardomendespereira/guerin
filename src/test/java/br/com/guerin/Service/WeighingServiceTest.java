package br.com.guerin.Service;

import br.com.guerin.Entity.*;
import br.com.guerin.Repository.Weighing.WeighingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class WeighingServiceTest {
    @Autowired
    private WeighingRepository weighingRepository;

    @Autowired
    private WeighingService weighingService;

    @Autowired
    private CattleService cattleService;

    @Autowired
    private FarmService farmService;

    @Autowired
    private SpecieService specieService;

    private final static LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2022, 8, 10, 22, 10, 22);

    @Test
    @DisplayName("Teste de insert e list")
    public void insertAndListWeight(){
        Specie specie = new Specie("Nelore");
        ////////////////
        Farm farm = new Farm("Guerin", "Estrada, 4580");
        ////////////////
        Cattle cattle = new Cattle();
        specieService.save(specie);
        cattle.setSpecie(specie);
        farmService.insert(farm);
        cattle.setFarm(farm);
        cattle.setEarring(500L);
        cattle.setGender(Gender.male);
        cattle.setWeight(300.0f);
        ////////////////
        Weighing weighing = new Weighing();
        weighing.setDate(LOCAL_DATE_TIME);
        weighing.setWeight(50f);
        cattleService.insert(cattle);
        weighing.setCattle(cattle);
        weighingRepository.save(weighing);
        ////////////////
        Integer listWeight = weighingRepository.findAll().size();
        Assertions.assertEquals(1, listWeight);
    }
}
