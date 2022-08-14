package br.com.guerin.Service;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Repository.User.VaccineRepository;
import br.com.guerin.Service.IService.IVaccineService;
import br.com.guerin.Service.VaccineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.BooleanSupplier;

@SpringBootTest
public class VaccineServiceTest {

    @Autowired
    IVaccineService vaccineService;

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
