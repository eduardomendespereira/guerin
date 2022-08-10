package br.com.guerin.Entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class CattleTest {
    public Cattle cattleFactory() {
        Cattle cattle = new Cattle();
        cattle.setEarring(1L);
        cattle.setGender(Gender.male);
        cattle.setWeight(300.0f);
        return cattle;
    }

    @Test
    public void testIfCattleIsNotInactiveByDefault() {
        Cattle cattle = this.cattleFactory();
        Assertions.assertFalse(cattle.isInactive());
    }

    @Test
    public void testIfIdEarringIsCorrect() {
        Cattle cattle = this.cattleFactory();
        Assertions.assertEquals(cattle.getEarring(), 1L);
    }

    @Test
    public void testIfGenderIsMale() {
        Cattle cattle = this.cattleFactory();
        Assertions.assertEquals(cattle.getGender(), Gender.male);
    }

}
