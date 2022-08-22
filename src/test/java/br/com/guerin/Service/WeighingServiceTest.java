package br.com.guerin.Service;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.IService.ICattleService;
import br.com.guerin.Service.IService.IFarmService;
import br.com.guerin.Service.IService.ISpecieService;
import br.com.guerin.Service.IService.IWeighingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDateTime;

@SpringBootTest
public class WeighingServiceTest {

    @Autowired
    private IWeighingService weighingService;

    @Autowired
    private ICattleService cattleService;

    @Autowired
    private IFarmService farmService;

    @Autowired
    private ISpecieService specieService;

    private Farm farmFactory(String name, String address) {
        Farm farm = new Farm(name, address);
        return this.farmService.save(farm);
    }

    private Specie specieFactory(String name) {
        Specie specie = new Specie(name);
        return this.specieService.save(specie);
    }

    private Cattle cattleFactory(
            Long earring, Float weight, Specie specie, Farm farm, Gender gender, Long father, Long mother
    ) {
        Cattle cattle = new Cattle(earring, weight, specie, farm, gender, father, mother);
        return this.cattleService.save(cattle);
    }

    private Weighing weightFactory(
            Cattle cattle, Float weight, LocalDateTime date
    ) {
        Weighing weighing = new Weighing(cattle, date, weight);
        return this.weighingService.save(weighing);
    }

    @Test
    @DisplayName("Teste de Insert")
    public void insertWeight(){
        Specie specie = this.specieFactory("insert");
        Farm farm = this.farmFactory("insert", "insert, 123");
        Cattle cattle = this.cattleFactory(50L, 250f, specie, farm, Gender.male, null, null);
        Weighing weighing = this.weightFactory(cattle, 500f, LocalDateTime.now());
        this.weighingService.save(weighing);
    }

    @Test
    @DisplayName("Teste de Update")
    public void updateWeight(){
        Specie specie = this.specieFactory("updatesd");
        Farm farm = this.farmFactory("updates", "updatess, 123");
        Cattle cattle = this.cattleFactory(200L, 400f, specie, farm, Gender.male, null, null);
        Weighing weighing = this.weightFactory(cattle, 50f, LocalDateTime.now());
        var getId = this.weighingService.findById(weighing.getId()).getId();
        weighing.setWeight(115f);
        weighing.setDate(LocalDateTime.now());
        this.weighingService.update(getId, weighing);
    }

    @Test
    @DisplayName("Teste de Disable")
    public void disableWeight(){
        Specie specie = this.specieFactory("delete");
        Farm farm = this.farmFactory("delete", "delete, 123");
        Cattle cattle = this.cattleFactory(300L, 500f, specie, farm, Gender.male, null, null);
        Weighing weighing = this.weightFactory(cattle, 50f, LocalDateTime.now());
        var getId = this.weighingService.findById(weighing.getId()).getId();
        weighing.setInactive(true);
        this.weighingService.disable(getId);
    }

    @Test
    @DisplayName("Teste de FindById")
    public void findByIdWeight(){
        Specie specie = this.specieFactory("Fid");
        Farm farm = this.farmFactory("Fid", "Fid, 123");
        Cattle cattle = this.cattleFactory(400L, 600f, specie, farm, Gender.male, null, null);
        Weighing weighing = this.weightFactory(cattle, 50f, LocalDateTime.now());
        var getId = this.weighingService.findById(weighing.getId()).getId();
        Assertions.assertEquals(getId, weighing.getId());
    }

    @Test
    @DisplayName("Teste de FindAll")
    public void findByAllWeight(){
        Specie specie = this.specieFactory("Fall");
        Farm farm = this.farmFactory("Fall", "Fall, 123");
        Cattle cattle = this.cattleFactory(500L, 700f, specie, farm, Gender.male, null, null);
        Weighing weighing = this.weightFactory(cattle, 50f, LocalDateTime.now());
        var getAll = this.weighingService.listAll(PageRequest.of(0,50));
        var checkList = getAll != null;
        Assertions.assertEquals(true, checkList);
    }
}