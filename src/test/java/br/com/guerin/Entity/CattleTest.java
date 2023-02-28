package br.com.guerin.Entity;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class CattleTest {
    private Cattle cattleFactory() {
        Cattle cattle = new Cattle();
        cattle.setEarring(1L);
        cattle.setGender(Gender.male);
        cattle.setWeight(300.0f);
        cattle.setFather(100L);
        cattle.setMother(101L);
        cattle.setBornAt(LocalDate.now());
        cattle.setBreastFeeding(false);
        return cattle;
    }

    private final Cattle  cattle = this.cattleFactory();

    @Test
    public void testIfCattleIsNotInactiveByDefault() {
        Assertions.assertFalse(this.cattle.isInactive());
    }

    @Test
    public void testIfCattleIdEarringIsCorrect() {
        Assertions.assertEquals(this.cattle.getEarring(), 1L);
    }

    @Test
    public void testIfCattleGenderIsMale() {
        Assertions.assertEquals(this.cattle.getGender(), Gender.male);
    }

    @Test
    public void testIfCattleWeightIsCorrect() {
        Assertions.assertEquals(this.cattle.getWeight(), 300.0f);
    }

    @Test
    public void testIfCattleSpecieIsCorrect() {
        Specie specie = new Specie();
        specie.setName("Test Specie");
        this.cattle.setSpecie(specie);
        Assertions.assertEquals(this.cattle.getSpecie(), specie);
    }

    @Test
    public void testIfCattleFarmIsCorrect() {
        Farm farm = new Farm();
        farm.setName("Test Farm");
        this.cattle.setFarm(farm);
        Assertions.assertEquals(this.cattle.getFarm(), farm);
    }

    @Test
    public void testIfCattleMotherIdIsCorrect() {
        Assertions.assertEquals(this.cattle.getFather(), 100L);
    }

    @Test
    public void testIfCattleFatherIdIsCorrect() {
        Assertions.assertEquals(this.cattle.getMother(), 101L);
    }
}
