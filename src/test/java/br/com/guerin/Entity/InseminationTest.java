package br.com.guerin.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
public class InseminationTest {
    Specie specie = new Specie(
            "brandus"
    );
    Farm farm = new Farm("Guerin2", "rondonia");
    Cattle cattle = new Cattle(
            123L,
            300F,
            specie,
            farm,
            Gender.male,
            124L,
            125L
    );
    private Insemination inseminationFactory(){
        Insemination insemination = new Insemination();
        insemination.setCattle(cattle);
        insemination.setDate(LocalDateTime.now());
        insemination.setInactive(false);
        return insemination;
    }
    private final Insemination insemination = this.inseminationFactory();

    @Test
    public void testIfInseminationIsNotInactiveByDefault(){
        Assertions.assertFalse(this.insemination.isInactive());
    }

    @Test
    public void testIfCattleIsCorrect() {
        Assertions.assertEquals(this.cattle.getEarring(), 123L);
    }

    @Test
    public void testIfCattleSpecieIsCorrect() {
        Specie specie = new Specie();
        specie.setName("Test Specie");
        this.cattle.setSpecie(specie);
        Assertions.assertEquals(this.cattle.getSpecie(), specie);
    }
}
