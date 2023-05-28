package br.com.guerin.Service;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.IService.ICattleService;
import br.com.guerin.Service.IService.IFarmService;
import br.com.guerin.Service.IService.IInseminationService;
import br.com.guerin.Service.IService.ISpecieService;
import io.swagger.models.auth.In;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InseminationServiceTest {
    @Autowired
    IInseminationService inseminationService;

    @Autowired
    private ICattleService cattleService;

    @Autowired
    private ISpecieService specieService;

    @Autowired
    private IFarmService farmService;
    private Farm farmFactory(String name, String address) {
        Farm farm = new Farm(name, address);
        return this.farmService.save(farm);
    }

    private Specie specieFactory(String name) {
        Specie specie = new Specie(name);
        return this.specieService.save(specie);
    }
    private Cattle cattleFactory(
            Long earring, Float weight, Specie specie, Farm farm, Gender gender, Long father, Long mother,
            LocalDate bornAt, Boolean breastFeeding, CattleStatus status
    ) {
        Cattle cattle = new Cattle(earring, weight, specie, farm, gender, father, mother, bornAt, breastFeeding, status);
        return this.cattleService.save(cattle);
    }

    private Insemination inseminationFactory(Cattle cattle, LocalDateTime date){
        Insemination insemination = new Insemination();
        insemination.setCattle(cattle);
        insemination.setDate(date);
        return this.inseminationService.save(insemination);
    }

    @Test
    @DisplayName("inserindo 1 inseminação e verificando se cadastrou")
    public void checkInsert(){
        Specie specie = this.specieFactory("save");
        Farm farm = this.farmFactory("save", "save, 123");
        Cattle cattle = this.cattleFactory(100L, 300f, specie, farm, Gender.male, null, null, LocalDate.now(), true, CattleStatus.engorda);
        Insemination insemination = this.inseminationFactory(cattle, LocalDateTime.now());
        Assertions.assertEquals(insemination, this.inseminationService.findById(insemination.getId()).get());
    }
}
