package br.com.guerin.Service;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Service.IService.IVaccineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VaccineServiceTest {

    @Autowired
    IVaccineService vaccineService;

    @Test
    @DisplayName("inserindo 1 vaccina e verificando se cadastrou")
    public void checkInsert(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("raiva vacitec");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        vaccineService.save(vaccine);
        Optional<Vaccine> getVaccine = vaccineService.findById(vaccine.getId());
        assertEquals(getVaccine.get().getName(), vaccine.getName());
    }

    @Test
    @DisplayName("teste de listagem das vacinas")
    public void checkListAll(){
        Assertions.assertNotNull(vaccineService.findAll(Pageable.unpaged()));
    }

    @Test
    @DisplayName("teste findById")
    public void checkFindById(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("carbunculo vacitec");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(false);
        vaccineService.save(vaccine);
        Optional<Vaccine> vaccineTest = vaccineService.findById(vaccine.getId());
        Assertions.assertEquals(vaccine.getName(), vaccineTest.get().getName());
    }

    @Test
    @Transactional
    public void checkDisable(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("vermicida");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(false);
        vaccineService.save(vaccine);
        vaccineService.disable(vaccine.getId());
        Optional<Vaccine> vaccineTest = vaccineService.findById(vaccine.getId());
        Assertions.assertEquals(true ,vaccineTest.get().isInactive());
    }

    @Test
    public void checkUpdate(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("raiva");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        vaccineService.save(vaccine);
        Vaccine vaccineTest = new Vaccine();
        vaccineTest.setName("carbunculo");
        vaccineTest.setDate(LocalDateTime.now());
        vaccineTest.setRequired(true);
        vaccineService.update(vaccine.getId(), vaccineTest);
        Optional<Vaccine> vaccineComparation = vaccineService.findById(vaccineTest.getId());
        Assertions.assertEquals(vaccineComparation.get().getName(), "carbunculo");
    }
}