package br.com.guerin.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class VaccineApplicationTest {

    private VaccineApplication vaccineApplicationFactory(){
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
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Raiva");

        vaccine.setRequired(true);
        VaccineApplication vaccineApplication = new VaccineApplication();
        vaccineApplication.setNote("Aplicação de vacina para raiva");
        vaccineApplication.setDate(LocalDateTime.now());
        vaccineApplication.setVaccine(vaccine);
        vaccineApplication.setCattle(cattle);
        return vaccineApplication;
    }

    private final VaccineApplication vaccineApplication = vaccineApplicationFactory();

    @Test
    @DisplayName("Verifica se a data da aplicacao nao e futura")
    public void testDateIsNotFuture(){
        Assertions.assertFalse(vaccineApplication.dateIsFuture());
    }

    @Test
    @DisplayName("Verifica se nao e inativo por default")
    public void testIsNotInactiveByDefault(){
        Assertions.assertFalse(this.vaccineApplication.isInactive());
    }

    @Test
    @DisplayName("Verifica se o gado da aplicacao nao e nulo")
    public void testIfCattleIsNotNull(){
        Assertions.assertNotNull(vaccineApplication.getCattle());
    }

    @Test
    @DisplayName("Verifica se a vacina da aplicacao nao e nula")
    public void testIfVaccineIsNotNull(){
        Assertions.assertNotNull(vaccineApplication.getVaccine());
    }
}