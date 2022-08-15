package br.com.guerin.repository;

import br.com.guerin.Entity.*;
import br.com.guerin.Repository.Weighing.WeighingRepository;
import br.com.guerin.Service.CattleService;
import br.com.guerin.Service.FarmService;
import br.com.guerin.Service.SpecieService;
import br.com.guerin.Service.WeighingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class WeighingRepositoryTest{

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
    public void updateAndListWeight(){
        Specie specie = new Specie("Nelore");
        ////////////////
        Farm farm = new Farm("Guerin", "Estrada, 123");
        ////////////////
        Cattle cattle = new Cattle();
        specieService.save(specie);
        cattle.setSpecie(specie);
        farmService.save(farm);
        cattle.setFarm(farm);
        cattle.setEarring(500L);
        cattle.setGender(Gender.male);
        cattle.setWeight(300.0f);
        ////////////////
        Weighing weighing = new Weighing();
        weighing.setDate(LOCAL_DATE_TIME);
        weighing.setWeight(50f);
        cattleService.save(cattle);
        weighing.setCattle(cattle);
        weighingService.save(weighing);
        ////////////////
        Weighing weighingUpdate = new Weighing();
        weighingUpdate.setDate(LOCAL_DATE_TIME);
        weighingUpdate.setWeight(200f);
        cattleService.save(cattle);
        weighingUpdate.setCattle(cattle);
        weighingRepository.save(weighingUpdate);
        ////////////////
        weighingService.update(weighing.getId(), weighingUpdate);
        Weighing wei = weighingService.findById(weighingUpdate.getId());
        Assertions.assertEquals(wei.getWeight(), 200f);
    }

}
