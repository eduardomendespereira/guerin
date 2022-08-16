package br.com.guerin.Repository;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Repository.Vaccine.VaccineRepository;
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
public class VaccineRepositoryTest {

    @Autowired
    private VaccineRepository vaccineRepository;

    @Test
    @DisplayName("inserindo 1 vaccina e verificando se cadastrou")
    public void checkInsert(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("raiva");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        vaccineRepository.save(vaccine);
        Integer contVaccine = vaccineRepository.findAll().size();
        assertEquals(2, contVaccine);
    }

    @Test
    @DisplayName("teste de listagem das vacinas")
    public void checkListAll(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("testListVaccine");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        vaccineRepository.save(vaccine);
        Integer count = vaccineRepository.findAll().size();
        Assertions.assertEquals(1, count);
    }

    @Test
    @DisplayName("teste findById")
    public void checkFindById(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("carbunculo");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(false);
        vaccineRepository.save(vaccine);
        Optional<Vaccine> vaccineTest = vaccineRepository.findById(vaccine.getId());
        Assertions.assertEquals("carbunculo", vaccineTest.get().getName());
    }

    @Test
    @Transactional
    public void checkDisable(){
        vaccineRepository.disable(1L);
        Optional<Vaccine> vaccineTest = vaccineRepository.findById(1L);
        Assertions.assertTrue(vaccineTest.get().isInactive());
    }
}