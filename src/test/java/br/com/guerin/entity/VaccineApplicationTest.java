package br.com.guerin.entity;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Entity.VaccineApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class VaccineApplicationTest {

    private VaccineApplication vaccineApplicationFactory(){
       Vaccine vaccine = new Vaccine();
       vaccine.setName("Raiva");
       vaccine.setDate(LocalDateTime.now());
       vaccine.setRequired(true);
       VaccineApplication vaccineApplication = new VaccineApplication();
       vaccineApplication.setName("Aplicação de vacina para raiva");
       vaccineApplication.setDate(LocalDateTime.now());
       vaccineApplication.setVaccine(vaccine);
       return vaccineApplication;
    }

    private final VaccineApplication vaccineApplication = vaccineApplicationFactory();

    @Test
    public void testDateIsNotFuture(){
        Assertions.assertFalse(vaccineApplication.dateIsFuture());
    }

    @Test
    public void testIsNotInactiveByDefault(){
        Assertions.assertFalse(this.vaccineApplication.isInactive());
    }
}
