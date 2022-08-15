package br.com.guerin.Service;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Service.IService.IVaccineApplicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class VaccineApplicationServiceTest {

    @Autowired
    IVaccineApplicationService vaccineApplicationService;

    @Autowired
    private VaccineService vaccineService;

    @Test
    public void checkUpdate(){
        Vaccine vaccine = new Vaccine();
        vaccine.setName("raiva");
        vaccine.setDate(LocalDateTime.now());
        vaccine.setRequired(true);
        vaccineService.save(vaccine);
        VaccineApplication vaccineApplication = new VaccineApplication();
        vaccineApplication.setNote("aplicação de vacina para raiva");
        vaccineApplication.setVaccine(vaccine);
        vaccineApplication.setDate(LocalDateTime.now());
        vaccineApplicationService.save(vaccineApplication);
        VaccineApplication vaccineApp1 = new VaccineApplication();
        vaccineApp1.setNote("aplicação de vacina para carbunculo");
        vaccineApp1.setVaccine(vaccine);
        vaccineApp1.setDate(LocalDateTime.now());
        vaccineApplicationService.update(vaccineApplication.getId(), vaccineApp1);
        Optional<VaccineApplication> vaccineApp2 = vaccineApplicationService.findById(vaccineApp1.getId());
        Assertions.assertEquals(vaccineApp2.get().getNote(), "aplicação de vacina para carbunculo");
    }
}