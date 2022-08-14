package br.com.guerin.Repository;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Repository.User.VaccineApplicationRepository;
import br.com.guerin.Service.VaccineService;
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

    @Autowired
    private VaccineService vaccineService;

    @Test
    @DisplayName("inserindo 1 Aplicação de vacina e verificando se cadastrou")
    public void checkInsert(){
        Vaccine vac = new Vaccine();
        vac.setName("Raiva");
        vac.setDate(LocalDateTime.now());
        vac.setRequired(true);
        VaccineApplication vaccineApp = new VaccineApplication();
        vaccineApp.setName("aplicação de vacina para raiva");
        vaccineService.insert(vac);
        vaccineApp.setVaccine(vac);
        vaccineApp.setDate(LocalDateTime.now());
        vaccineApplicationRepository.save(vaccineApp);
        Integer contVaccine = vaccineApplicationRepository.findAll().size();
        assertEquals(2, contVaccine);
    }

    @Test
    @DisplayName("teste de listagem das aplicações de vacina")
    public void checkListAll(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Carbunculo");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        VaccineApplication vaccineApplication = new VaccineApplication();
        vaccineApplication.setName("aplicação de vacina para carbunculo");
        vaccineApplication.setDate(LocalDateTime.now());
        vaccineService.insert(vaccine);
        vaccineApplication.setVaccine(vaccine);
        vaccineApplicationRepository.save(vaccineApplication);
        Integer count = vaccineApplicationRepository.findAll().size();
        Assertions.assertEquals(1, count);
    }

    @Test
    @DisplayName("teste findById")
    public void checkFindById(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("febre B");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        VaccineApplication vaccineApplication = new VaccineApplication();
        vaccineApplication.setName("aplicação de vacina para febre boi");
        vaccineApplication.setDate(LocalDateTime.now());
        vaccineService.insert(vaccine);
        vaccineApplication.setVaccine(vaccine);
        vaccineApplicationRepository.save(vaccineApplication);
        Optional<VaccineApplication> vaccineAppTest = vaccineApplicationRepository.findById(vaccineApplication.getId());
        Assertions.assertEquals("aplicação de vacina para febre boi", vaccineAppTest.get().getName());
    }

    @Test
    @Transactional
    public void checkDisable(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Raiva3e");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        VaccineApplication vaccineApplication = new VaccineApplication();
        vaccineApplication.setName("aplicação de vacina para febre boi");
        vaccineApplication.setDate(LocalDateTime.now());
        vaccineService.insert(vaccine);
        vaccineApplication.setVaccine(vaccine);
        vaccineApplicationRepository.save(vaccineApplication);
        Optional<VaccineApplication> vaccineAppTest = vaccineApplicationRepository.findById(vaccineApplication.getId());
        vaccineApplicationRepository.disable(vaccineAppTest.get().getId());
        Assertions.assertTrue(vaccineAppTest.get().isInactive());
    }
}
