package br.com.guerin.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import br.com.guerin.Entity.*;
import br.com.guerin.Payload.Cattle.ResultFindChildren;
import br.com.guerin.Payload.Cattle.ResultFindParents;
import br.com.guerin.Service.IService.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class CattleServiceTest {
    private Farm farmFactory(String name, String address) {
        Farm farm = new Farm(name, address);
        return this.farmService.save(farm);
    }

    private Specie specieFactory(String name) {
        Specie specie = new Specie(name);
        return this.specieService.save(specie);
    }

    private Cattle cattleFactory(
            Long earring, Float weight, Specie specie, Farm farm, Gender gender, Long father, Long mother, LocalDateTime bornAt, Boolean breastFeeding
    ) {
        Cattle cattle = new Cattle(earring, weight, specie, farm, gender, father, mother, bornAt, breastFeeding);
        return this.cattleService.save(cattle);
    }

    @Autowired
    private ICattleService cattleService;

    @Autowired
    private ISpecieService specieService;

    @Autowired
    private IFarmService farmService;


    @Test
    public void saveTest() {
        Specie specie = this.specieFactory("save");
        Farm farm = this.farmFactory("save", "save, 123");
        Cattle cattle = this.cattleFactory(100L, 300f, specie, farm, Gender.male, null, null, LocalDateTime.now(), true);

        Assertions.assertEquals(cattle, this.cattleService.findById(cattle.getId()).get());
    }

    @Test
    public void updateTest() {
        Specie specie = this.specieFactory("update");
        Farm farm = this.farmFactory("update", "update, 123");
        Cattle cattle = this.cattleFactory(101L, 300f, specie, farm, Gender.female, null, null, LocalDateTime.now(), true);
        cattle.setWeight(400f);
        this.cattleService.update(cattle.getEarring(), cattle);
        Float weight = this.cattleService.findById(cattle.getId()).get().getWeight();

        Assertions.assertEquals(weight, 400f);
    }

    @Test
    public void findByIdTest() {
        Specie specie = this.specieFactory("findbyid");
        Farm farm = this.farmFactory("findbyid", "findbyid, 123");
        Cattle cattle = this.cattleFactory(102L, 300f, specie, farm, Gender.female, null, null, LocalDateTime.now(), true);
        Cattle cattle2 = this.cattleService.findById(cattle.getId()).get();

        Assertions.assertEquals(cattle, cattle2);
    }

    @Test
    public void findAllTest() {
        Specie specie = this.specieFactory("findall");
        Farm farm = this.farmFactory("findall", "findall, 123");
        Cattle cattle = this.cattleFactory(103L, 300f, specie, farm, Gender.female, null, null, LocalDateTime.now(), true);
        Integer count = this.cattleService.findAll().size();

        Assertions.assertTrue(count >= 1);
    }

    @Test
    public void disableTest() {
        Specie specie = this.specieFactory("disable");
        Farm farm = this.farmFactory("disable", "disable, 123");
        Cattle cattle = this.cattleFactory(104L, 300f, specie, farm, Gender.male, null, null, LocalDateTime.now(), true);
        this.cattleService.disable(cattle.getEarring(), cattle);
        cattle = this.cattleService.findById(cattle.getId()).get();

        Assertions.assertTrue(cattle.isInactive());
    }

    @Test
    public void findByEarringTest() {
        Specie specie = this.specieFactory("earring");
        Farm farm = this.farmFactory("earring", "earring, 123");
        Cattle cattle = this.cattleFactory(105L, 300f, specie, farm, Gender.male, null, null, LocalDateTime.now(), true);
        Cattle cattle2 = this.cattleService.findByEarring(cattle.getEarring()).get();

        Assertions.assertEquals(cattle, cattle2);
    }

    @Test
    public void findByEarringOrNewTest() {
        Cattle cattle = this.cattleService.findByEarringOrNew(1000L);
        Assertions.assertEquals(cattle.getEarring(), 1000L);
    }

    @Test
    public void findBySpecieTest() {
        Specie specie = this.specieFactory("specie");
        Farm farm = this.farmFactory("specie", "specie, 123");
        Cattle cattle = this.cattleFactory(106L, 300f, specie, farm, Gender.male, null, null, LocalDateTime.now(), true);
        ArrayList<Cattle> cattles = this.cattleService.findBySpecie(cattle.getSpecie().getId());

        Assertions.assertFalse(cattles.isEmpty());
    }

    @Test
    public void findByFarmTest() {
        Specie specie = this.specieFactory("farm");
        Farm farm = this.farmFactory("farm", "farm, 123");
        Cattle cattle = this.cattleFactory(107L, 300f, specie, farm, Gender.male, null, null, LocalDateTime.now(), true);
        ArrayList<Cattle> cattles = this.cattleService.findByFarm(cattle.getFarm().getId());

        Assertions.assertFalse(cattles.isEmpty());
    }

    @Test
    public void findChildrenTest() {
        Specie specie = this.specieFactory("children");
        Farm farm = this.farmFactory("children", "children, 123");
        Cattle cattleFather = this.cattleFactory(108L, 300f, specie, farm, Gender.male, null, null, LocalDateTime.now(), false);
        this.cattleFactory(109L, 300f, specie, farm, Gender.female, null, null, LocalDateTime.now(), false);
        this.cattleFactory(110L, 300f, specie, farm, Gender.male, 108L, 109L, LocalDateTime.now(), false);
        this.cattleFactory(111L, 300f, specie, farm, Gender.female, 108L, 109L, LocalDateTime.now(), false);
        ResultFindChildren cattles = this.cattleService.findChildren(cattleFather.getEarring());

        Assertions.assertEquals(cattles.getChildren().get(0).getFather(), cattleFather.getEarring());
        Assertions.assertEquals(cattles.getChildren().get(1).getFather(), cattleFather.getEarring());
    }

    @Test
    public void findParentsTest() {
        Specie specie = this.specieFactory("parents");
        Farm farm = this.farmFactory("parents", "parents, 123");
        Cattle cattleFather = this.cattleFactory(112L, 300f, specie, farm, Gender.male, null, null, LocalDateTime.now(), true);
        Cattle cattleMother = this.cattleFactory(113L, 300f, specie, farm, Gender.female, null, null, LocalDateTime.now(), true);
        Cattle cattleSon = this.cattleFactory(114L, 300f, specie, farm, Gender.male, 112L, 113L, LocalDateTime.now(), true);
        ResultFindParents cattles = this.cattleService.findParents(cattleSon.getEarring());

        Assertions.assertEquals(cattles.getSon(), cattleSon);
        Assertions.assertEquals(cattles.getFather(), cattleFather);
        Assertions.assertEquals(cattles.getMother(), cattleMother);

    }
}