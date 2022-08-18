package br.com.guerin.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class CattleEventTest {
    private Cattle cattle;
    private Vaccine vaccine;
    private VaccineApplication vaccineApplication;
    private EventType eventType;
    private Weighing weighing;

    private Farm farm;
    private Specie specie;

    public void generateEventFactory() {
        this.cattle = new Cattle(
                123L,
                300F,
                specie,
                farm,
                Gender.male,
                124L,
                125L
        );
        this.vaccine = new Vaccine(
                "carbunculo",
                LocalDateTime.now(),
                true
        );
        this.vaccineApplication = new VaccineApplication(
                "Aplicação de vacina para carbunculo",
                vaccine,
                LocalDateTime.now(),
                cattle
        );
        this.eventType = new EventType(
                "Vacinação"
        );

        this.weighing = new Weighing(
                cattle,
                LocalDateTime.now(),
                150F
        );
        this.specie = new Specie(
                "brandus"
        );
        this.farm = new Farm("Guerin2", "rondonia");
    }

    public CattleEvent eventCattleVaccinationFactory(){
        generateEventFactory();
        CattleEvent cattleEvent = new CattleEvent(
                cattle,
                eventType,
                LocalDateTime.now(),
                "Aplicacao de vacina",
                vaccineApplication
        );
        return cattleEvent;
    }

    public CattleEvent eventCattleWeighing(){
        generateEventFactory();
        CattleEvent cattleEvent = new CattleEvent(
                cattle,
                eventType,
                LocalDateTime.now(),
                "Pesagem do gado",
                weighing
        );
        return cattleEvent;
    }

    private CattleEvent eventoDePesagem = this.eventCattleWeighing();
    private CattleEvent eventoDeVacinacao = this.eventCattleVaccinationFactory();
    @Test
    @DisplayName("Verifica se o evento nao e inativo por default")
    public void testIfCattleEventIsNotInactiveByDefault(){
        Assertions.assertFalse(eventoDeVacinacao.isInactive());
    }

    @Test
    @DisplayName("Verifica se o gado no evento nao e nulo")
    public void testIfCattleNotIsNull(){
        Assertions.assertNotNull(eventoDeVacinacao.getCattle());
    }

    @Test
    @DisplayName("Verifica se o tipo do evento nao e nulo")
    public void testIfTypeEventNotIsNull(){
        Assertions.assertNotNull(eventoDeVacinacao.getEventType());
    }

    @Test
    @DisplayName("Verifica se a data do evento nao é futura")
    public void testIfDateNotIsFuture(){
        Assertions.assertFalse(eventoDeVacinacao.getDate().compareTo(LocalDateTime.now()) > 0);
    }

    @Test
    @DisplayName("Verifica se a aplicacao de vacina nao e nula")
    public void testIfVaccinationNotIsNull(){
        Assertions.assertNotNull(eventoDeVacinacao.getVaccineApplication());
    }

    @Test
    @DisplayName("Verifica se a pesagem nao e nula")
    public void testIfWeighingNotIsNull(){
        Assertions.assertNotNull(eventoDePesagem.getWeighing());
    }
}
