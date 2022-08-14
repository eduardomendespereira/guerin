package br.com.guerin.Entity;

import br.com.guerin.Entity.Vaccine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class VaccineTest {
    private Vaccine vaccineFactory() {
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Raiva");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        return vaccine;
    }

    private final Vaccine vaccine = this.vaccineFactory();

    @Test
    public void testIfVaccineIsNotInactiveByDefault() {
        Assertions.assertFalse(this.vaccine.isInactive());
    }

    @Test
    public void testIfDateIsNotFuture(){
        Assertions.assertFalse(vaccine.dateIsFuture());
    }
}
