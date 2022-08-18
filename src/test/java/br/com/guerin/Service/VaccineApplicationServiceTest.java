package br.com.guerin.Service;

import br.com.guerin.Entity.*;
import br.com.guerin.Service.IService.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class VaccineApplicationServiceTest {

    @Autowired
    IVaccineApplicationService vaccineApplicationService;

    @Autowired
    private IVaccineService vaccineService;

    @Autowired
    private ISpecieService specieService;

    @Autowired
    private IFarmService farmService;

    @Autowired
    private ICattleService cattleService;

    private Cattle cattle;
    private Vaccine vaccine;
    private Vaccine vaccine2;

    public void generateCattlesAndVaccine() {
        this.generateSpecieAndFarm();
        this.cattle = new Cattle(
                123L,
                300F,
                specieService.findByName("Nelore").get(),
                farmService.findByName("Fazenda Generica").get(),
                Gender.male,
                null,
                null
        );
        this.vaccine = new Vaccine(
                "carbunculo",
                LocalDateTime.now(),
                true
        );
        this.vaccine2 = new Vaccine(
                "micose vacitec",
                LocalDateTime.now(),
                true
        );
    }

    public void generateSpecieAndFarm() {
        if (!farmService.findByName("Fazenda Generica").isPresent())
            farmService.save(new Farm("Fazenda Generica", "Meio do mato"));
        if (!specieService.findByName("Nelore").isPresent())
            specieService.save(new Specie("Nelore"));
    }

    @Test
    @Transactional
    public void checkInsert(){
        this.generateCattlesAndVaccine();
        vaccineService.save(vaccine);
        cattleService.save(cattle);
        VaccineApplication vaccineApplication = new VaccineApplication();
        vaccineApplication.setNote("Aplicacao de vacina para carbunculo");
        vaccineApplication.setDate(LocalDateTime.now());
        vaccineApplication.setVaccine(vaccine);
        vaccineApplication.setCattle(cattle);
        vaccineApplicationService.save(vaccineApplication);
        Optional<VaccineApplication> va2 = vaccineApplicationService.findById(vaccineApplication.getId());
        Assertions.assertEquals(vaccineApplication.getNote(), va2.get().getNote());
    }

    @Test
    public void checkFindByAll(){
        Assertions.assertNotNull(vaccineApplicationService.findAll(Pageable.unpaged()));
    }

    @Test
    @Transactional
    public void checkUpdate() {
        this.generateCattlesAndVaccine();
        cattleService.save(cattle);
        vaccineService.save(vaccine);
        vaccineService.save(vaccine2);
        VaccineApplication vaccineApplication = new VaccineApplication();
        vaccineApplication.setNote("Aplicacao de vacina para raiva");
        vaccineApplication.setDate(LocalDateTime.now());
        vaccineApplication.setVaccine(vaccine);
        vaccineApplication.setCattle(cattle);
        vaccineApplicationService.save(vaccineApplication);
        VaccineApplication updateVaccineApp = vaccineApplication;
        updateVaccineApp.setVaccine(vaccine2);
        this.vaccineApplicationService.update(updateVaccineApp.getId(), updateVaccineApp);
        Optional<VaccineApplication> getVaccine = vaccineApplicationService.findById(updateVaccineApp.getId());
        Assertions.assertEquals(getVaccine.get().getVaccine().getName(), "micose vacitec");
    }

    @Test
    @Transactional
    public void checkDisable(){
        this.generateCattlesAndVaccine();
        cattleService.save(cattle);
        vaccineService.save(vaccine);
        VaccineApplication vaccineApplication = new VaccineApplication();
        vaccineApplication.setNote("Aplicacao de vacina para raiva");
        vaccineApplication.setDate(LocalDateTime.now());
        vaccineApplication.setVaccine(vaccine);
        vaccineApplication.setCattle(cattle);
        vaccineApplicationService.save(vaccineApplication);
        vaccineApplicationService.disable(vaccineApplication.getId(), vaccineApplication);
        Optional<VaccineApplication> getVaccineApp = vaccineApplicationService.findById(vaccineApplication.getId());
        Assertions.assertTrue(getVaccineApp.get().isInactive());
    }
}