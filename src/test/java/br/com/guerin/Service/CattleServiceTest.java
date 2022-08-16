package br.com.guerin.Service;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.IService.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CattleServiceTest {
    @Autowired
    ICattleService cattleService;

    @Autowired
    ISpecieService specieService;

    @Autowired
    IFarmService farmService;

    private Cattle son = null;
    private Cattle mother = null;
    private Cattle father = null;

    public void generateCattles() {
        this.generateSpecieAndFarm();

        this.son = new Cattle(
                123L,
                300F,
                specieService.findByName("Nelore").get(),
                farmService.findByName("Fazenda Generica").get(),
                Gender.male,
                124L,
                125L
        );

        this.father = new Cattle(
                124L,
                350F,
                specieService.findByName("Nelore").get(),
                farmService.findByName("Fazenda Generica").get(),
                Gender.male,
                null,
                null
        );


        this.mother = new Cattle(
                125L,
                400F,
                specieService.findByName("Nelore").get(),
                farmService.findByName("Fazenda Generica").get(),
                Gender.male,
                null,
                null
        );
    }

    public void generateSpecieAndFarm() {
        if (!farmService.findByName("Fazenda Generica").isPresent())
            farmService.save(new Farm("Fazenda Generica", "Meio do mato"));
        if (!specieService.findByName("Nelore").isPresent())
            specieService.save(new Specie("Nelore"));
    }

    @Test
    public void save() {
        this.generateCattles();
        var i_son = this.cattleService.save(this.son);
        var i_father = this.cattleService.save(this.father);
        var i_mother = this.cattleService.save(this.mother);

        if (i_son != null)
            Assertions.assertEquals(son.getEarring(), i_son.getEarring());
        else {
            if (this.cattleService.findByEarring(this.son.getEarring()) != null)
                Assertions.assertEquals(this.cattleService.findByEarring(this.son.getEarring()).get().getEarring(), this.son.getEarring());
        }

        if (i_father != null)
            Assertions.assertEquals(this.father.getEarring(), i_father.getEarring());
        else {
            if (this.cattleService.findByEarring(this.father.getEarring()) != null)
                Assertions.assertEquals(this.cattleService.findByEarring(this.father.getEarring()).get().getEarring(), this.father.getEarring());
        }

        if (i_mother != null)
            Assertions.assertEquals(mother.getEarring(), i_mother.getEarring());
        else {
            if (this.cattleService.findByEarring(this.mother.getEarring()) != null)
                Assertions.assertEquals(this.cattleService.findByEarring(this.mother.getEarring()).get().getEarring(), this.mother.getEarring());
        }
    }

    @Test
    public void findFathers() {
        if (this.son == null)
            this.generateCattles();

        var i_son = this.cattleService.save(this.son);
        var i_father = this.cattleService.save(this.father);
        var i_mother = this.cattleService.save(this.mother);

        var fathers = this.cattleService.findParents(this.son.getEarring());
        Assertions.assertEquals(fathers.father.getEarring(), this.father.getEarring());
        Assertions.assertEquals(fathers.mother.getEarring(), this.mother.getEarring());
    }

    @Test
    public void findSons() {
        if (father == null)
            this.generateCattles();

        var i_son = this.cattleService.save(son);
        var i_father = this.cattleService.save(father);
        var i_mother = this.cattleService.save(mother);

        var sons_father = this.cattleService.findChildren(this.father.getEarring());
        var exists_father = sons_father.getSons().stream().anyMatch(t -> t.getEarring() == this.son.getEarring());
        Assertions.assertEquals(true, exists_father);

        var sons_mother = this.cattleService.findChildren(mother.getEarring());
        var exists_mother = sons_mother.getSons().stream().anyMatch(t -> t.getEarring() == this.son.getEarring());
        Assertions.assertEquals(true, exists_mother);
    }
}