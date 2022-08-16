package br.com.guerin.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class WeighingTest {

    private Weighing weighingFactory() {
        Weighing weighing = new Weighing();
        weighing.setDate(LOCAL_DATE_TIME);
        weighing.setWeight(50f);
        weighing.setCattle(this.cattle);
        return weighing;
    }

    private Cattle cattleFactory() {
        Cattle cattle = new Cattle();
        cattle.setEarring(500L);
        return cattle;
    }

    private final Cattle cattle = cattleFactory();
    private final Weighing weighing = weighingFactory();
    private final static LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2022, 8, 10, 22, 10, 22);

    @Test
    @DisplayName("Verificando se os valores de peso são iguais")
    public void weightValueWeightTest(){
        Assertions.assertEquals(this.weighing.getWeight(), 50);
    }

    @Test
    @DisplayName("Verificando se os valores do gado são iguais")
    public void weightValueCattleTest(){
        Assertions.assertEquals(this.weighing.getCattle().getEarring(), 500);
    }

    @Test
    @DisplayName("Verificando se os valores de data são iguais")
    public void weightValueDateTest(){
        Assertions.assertEquals(this.weighing.getDate(), LOCAL_DATE_TIME);
    }

    @Test
    @DisplayName("Verificando se os valores de peso não são nulos")
    public void valueWeightIsNotNull(){
        Assertions.assertNotNull(this.weighing.getWeight());
    }

    @Test
    @DisplayName("Verificando se os valores de data não são nulos")
    public void valueDateIsNotNull(){
        Assertions.assertNotNull(this.weighing.getDate());
    }

    @Test
    @DisplayName("Verificando se os valores do gado não são nulos")
    public void valueCattleIsNotNull(){
        Assertions.assertNotNull(this.weighing.getCattle().getEarring());
    }

    @Test
    @DisplayName("Verificando Inactive")
    public void WeighinhIsNotInactiveByDefault() {
        Assertions.assertFalse(this.weighing.isInactive());
    }

}
