package br.com.guerin.Service;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Service.IService.IVaccineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VaccineServiceTest {

    @Autowired
    IVaccineService vaccineService;

    private Vaccine vaccineFactory(){
        Vaccine vaccine = new Vaccine(
                "raiva vacitec",
                LocalDateTime.now(),
                true
        );
        return vaccine;
    }

    @Test
    @DisplayName("inserindo 1 vaccina e verificando se cadastrou")
    public void checkInsert(){
        Vaccine vaccine = this.vaccineFactory();
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
        Vaccine vaccine = this.vaccineFactory();
        vaccine.setName("vacina raiv");
        vaccine = vaccineService.save(vaccine);
        Optional<Vaccine> vaccineTest = vaccineService.findById(vaccine.getId());
        Assertions.assertEquals(vaccine.getName(), vaccineTest.get().getName());
    }

    @Test
    public void checkDisable(){
        Vaccine vaccine = this.vaccineFactory();
        vaccine.setName("micose vect");
        vaccine = vaccineService.save(vaccine);
        vaccineService.disable(vaccine.getId());
        Optional<Vaccine> vaccineTest = vaccineService.findById(vaccine.getId());
        Assertions.assertTrue(vaccineTest.get().isInactive());
    }

    @Test
    public void checkUpdate(){
        Vaccine vaccine = this.vaccineFactory();
        vaccine.setName("carbunc b");
        vaccine = vaccineService.save(vaccine);
        vaccineService.update(vaccine.getId(), vaccine);
        Optional<Vaccine> vaccineComparation = vaccineService.findById(vaccine.getId());
        Assertions.assertEquals(vaccineComparation.get().getName(), "carbunc b");
    }
}