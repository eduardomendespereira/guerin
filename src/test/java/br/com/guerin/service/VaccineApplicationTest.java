package br.com.guerin.service;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Service.VaccineApplicationService;
import br.com.guerin.Service.VaccineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class VaccineApplicationTest {

    @Autowired
    VaccineApplicationService vaccineApplicationService;

    @Autowired
    private VaccineService vaccineService;

    @Test
    public void checkUpdate(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("raiva");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        vaccineService.insert(vaccine);
        VaccineApplication vaccineApplication = new VaccineApplication();
        vaccineApplication.setName("aplicação de vacina para raiva");
        vaccineApplication.setVaccine(vaccine);
        vaccineApplication.setDate(LocalDateTime.now());
        vaccineApplicationService.insert(vaccineApplication);
        VaccineApplication vaccineApp1 = new VaccineApplication();
        vaccineApp1.setName("aplicação de vacina para carbunculo");
        vaccineApp1.setVaccine(vaccine);
        vaccineApp1.setDate(LocalDateTime.now());
        vaccineApplicationService.update(vaccineApplication.getId(), vaccineApp1);
        Optional<VaccineApplication> vaccineApp2 = vaccineApplicationService.findById(vaccineApp1.getId());
        Assertions.assertEquals(vaccineApp2.get().getName(), "aplicação de vacina para carbunculo");
    }
}
