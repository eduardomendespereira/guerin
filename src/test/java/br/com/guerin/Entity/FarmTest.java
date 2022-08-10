package br.com.guerin.Entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class FarmTest {
    private Farm farmFactory() {
        Farm farm = new Farm();
        farm.setName("Guerin");
        farm.setAddress("Estrada Rural, 123");
        return farm;
    }

    private final Farm farm = this.farmFactory();


    @Test
    public void testIfFarmIsNotInactiveByDefault() {
        Assertions.assertFalse(this.farm.isInactive());
    }

    @Test
    public void testIfFarmNameIsCorrect() {
        Assertions.assertEquals(this.farm.getName(), "Guerin");
    }

    @Test
    public void testIfFarmAddressIsCorrect() {
        Assertions.assertEquals(this.farm.getAddress(), "Estrada Rural, 123");
    }
}
