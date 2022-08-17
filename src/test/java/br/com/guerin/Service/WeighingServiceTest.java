package br.com.guerin.Service;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.IService.ICattleService;
import br.com.guerin.Service.IService.IFarmService;
import br.com.guerin.Service.IService.ISpecieService;
import br.com.guerin.Service.IService.IWeighingService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Optional;

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

    private final static LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2022, 8, 10, 22, 10, 22);

    public Weighing insertWeighing() {
        Weighing weighing = new Weighing();

        Optional<Cattle> findId = cattleService.findById(1L);

        weighing.setCattle(findId.get());
        weighing.setDate(LOCAL_DATE_TIME);
        weighing.setWeight(96.0f);
        weighingService.save(weighing);

        return weighing;
    }

    public void disableParameters(){
        Specie specie = new Specie("disable");
        specieService.save(specie);

        Farm farm = new Farm("disable", "disable, 123");
        farmService.save(farm);

        Cattle cattle = new Cattle(102L, 320f, specie, farm, Gender.female, null, null);
        cattleService.save(cattle);
    }

    public void updateParameters(){
        Specie specie = new Specie("update");
        specieService.save(specie);

        Farm farm = new Farm("update", "update, 123");
        farmService.save(farm);

        Cattle cattle = new Cattle(101L, 300f, specie, farm, Gender.female, null, null);
        cattleService.save(cattle);
    }

    @Test
    @DisplayName("Teste de Insert")
    public void insertWeight(){
        insertWeighing();
    }

    @Test
    @DisplayName("Teste de Update")
    public void updateWeight(){
        updateParameters();
        Weighing weighing = this.insertWeighing();
        var getId = this.weighingService.findById(weighing.getId()).getId();
        weighing.setWeight(115f);
        weighing.setDate(LocalDateTime.now());
        this.weighingService.update(getId, weighing);
    }

    @Test
    @DisplayName("Teste de Disable")
    public void disableWeight(){
        disableParameters();
        Weighing weighing = this.insertWeighing();
        var getId = this.weighingService.findById(weighing.getId()).getId();
        weighing.setInactive(true);
        this.weighingService.disable(getId, weighing);
    }

    @Test
    @DisplayName("Teste de FindById")
    public void findByIdWeight(){
        Weighing weighing = this.insertWeighing();
        var getId = this.weighingService.findById(weighing.getId()).getId();
        Assertions.assertEquals(getId, weighing.getId());
    }

    @Test
    @DisplayName("Teste de FindAll")
    public void findByAllWeight(){
        Weighing weighing = this.insertWeighing();
        var getAll = this.weighingService.listAll(PageRequest.of(0,50));
        var checkList = getAll != null;
        Assertions.assertEquals(true, checkList);
    }
}
