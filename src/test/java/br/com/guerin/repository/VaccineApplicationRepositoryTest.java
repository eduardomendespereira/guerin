package br.com.guerin.repository;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Repository.User.VaccineApplicationRepository;
import br.com.guerin.Repository.User.VaccineRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VaccineApplicationRepositoryTest {
    @Autowired
    private VaccineApplicationRepository vaccineApplicationRepository;

    private Vaccine vaccineFactory() {
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Raiva");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        this.vaccineRepository.save(vaccine);
        return vaccine;
    }

    private final Vaccine vaccine = this.vaccineFactory();

    @Test
    @DisplayName("inserindo 1 Aplicação de vacina e listando")
    public void checkInsert(){
        VaccineApplication vaccineApp = new VaccineApplication();
        vaccineApp.setName("aplicação de vacina para raiva");
        vaccineApp.setVaccine(vaccine);
        vaccineApp.setDate(LocalDateTime.now());
        vaccineApplicationRepository.save(vaccineApp);
        Integer contVaccine = vaccineApplicationRepository.findAll().size();
        assertEquals(1, contVaccine);
    }

//    @Test
//    @DisplayName("teste de listagem das vacinas")
//    public void checkListAll(){
//        Vaccine vaccine = new Vaccine();
//        vaccine.setName("testListVaccine");
//        vaccine.setDate(LocalDateTime.now());
//        vaccine.setRequired(true);
//        vaccineRepository.save(vaccine);
//        Integer count = vaccineRepository.findAll().size();
//        Assertions.assertEquals(1, count);
//    }

//    @Test
//    @DisplayName("teste findById")
//    public void checkFindById(){
//        Vaccine vaccine = new Vaccine();
//        vaccine.setName("carbunculo");
//        vaccine.setDate(LocalDateTime.now());
//        vaccine.setRequired(false);
//        vaccineRepository.save(vaccine);
//        Optional<Vaccine> vaccineTest = vaccineRepository.findById(vaccine.getId());
//        Assertions.assertEquals("carbunculo", vaccineTest.get().getName());
//    }

//    @Test
//    @Transactional
//    public void checkDisable(){
//        vaccineRepository.disable(1L);
//        Optional<Vaccine> vaccineTest = vaccineRepository.findById(1L);
//        Assertions.assertTrue(vaccineTest.get().isInactive());
//    }
}
