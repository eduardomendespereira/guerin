package br.com.guerin.Service;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Entity.Farm;
import br.com.guerin.Entity.Gender;
import br.com.guerin.Entity.Specie;
import br.com.guerin.Service.IService.ICattleService;
import br.com.guerin.Service.IService.IFarmService;
import br.com.guerin.Service.IService.ISpecieService;
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
        generateSpecieAndFarm();

        son = new Cattle(123L, 300F,
                specieService.findByName("Nelore").get(), farmService.findByName("Fazenda Generica").get(),
                Gender.male, 124L, 125L);

        father = new Cattle(124L, 350F,
                specieService.findByName("Nelore").get(), farmService.findByName("Fazenda Generica").get(),
                Gender.male, null, null);


        mother = new Cattle(125L, 400F,
                specieService.findByName("Nelore").get(), farmService.findByName("Fazenda Generica").get(),
                Gender.male, null, null);
    }

    public void generateSpecieAndFarm() {
        if (farmService.findByName("Fazenda Generica") == null)
            farmService.save(new Farm("Fazenda Generica", "Meio do mato"));
        if (specieService.findByName("Nelore") == null)
            specieService.save(new Specie("Nelore"));
    }

    @Test
    public void save() {
        generateCattles();
        var i_son = cattleService.save(son);
        var i_father = cattleService.save(father);
        var i_mother = cattleService.save(mother);

        if (i_son != null)
            Assertions.assertEquals(son.getEarring(), i_son.getEarring());
        else {
            if (cattleService.findByEarring(son.getEarring()) != null)
                Assertions.assertEquals(cattleService.findByEarring(son.getEarring()).get().getEarring(), son.getEarring());
        }

        if (i_father != null)
            Assertions.assertEquals(father.getEarring(), i_father.getEarring());
        else {
            if (cattleService.findByEarring(father.getEarring()) != null)
                Assertions.assertEquals(cattleService.findByEarring(father.getEarring()).get().getEarring(), father.getEarring());
        }

        if (i_mother != null)
            Assertions.assertEquals(mother.getEarring(), i_mother.getEarring());
        else {
            if (cattleService.findByEarring(mother.getEarring()) != null)
                Assertions.assertEquals(cattleService.findByEarring(mother.getEarring()).get().getEarring(), mother.getEarring());
        }
    }

    @Test
    public void findFathers() {
        if (son == null)
            generateCattles();

        var i_son = cattleService.save(son);
        var i_father = cattleService.save(father);
        var i_mother = cattleService.save(mother);

        var fathers = cattleService.findFathers(son.getEarring());
        Assertions.assertEquals(fathers.father.getEarring(), father.getEarring());
        Assertions.assertEquals(fathers.mother.getEarring(), mother.getEarring());
    }

    @Test
    public void findSons() {
        if (father == null)
            generateCattles();

        var i_son = cattleService.save(son);
        var i_father = cattleService.save(father);
        var i_mother = cattleService.save(mother);

        var sons_father = cattleService.findSons(father.getEarring());
        var exists_father = sons_father.getSons().stream().anyMatch(t -> t.getEarring() == son.getEarring());
        Assertions.assertEquals(true, exists_father);

        var sons_mother = cattleService.findSons(mother.getEarring());
        var exists_mother = sons_mother.getSons().stream().anyMatch(t -> t.getEarring() == son.getEarring());
        Assertions.assertEquals(true, exists_mother);
    }
}
