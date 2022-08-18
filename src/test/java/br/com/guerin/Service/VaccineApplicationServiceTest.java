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
    private Farm farm;
    private Specie specie;


    public VaccineApplication generateVaccineApplication() {
        this.specie = new Specie(
                "brac"
        );
        this.farm = new Farm("Guerin223", "parana");
        farmService.save(farm);
        specieService.save(specie);
        this.cattle = new Cattle(
                173L,
                700F,
                specie,
                farm,
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
        vaccineService.save(vaccine);
        cattleService.save(cattle);
        VaccineApplication vaccineApplication = new VaccineApplication(
                "aplicacao de vacina pra raiva",
                vaccine,
                LocalDateTime.now(),
                cattle
        );
        return vaccineApplication;
    }


    @Test
    @Transactional
    public void checkInsert(){
        VaccineApplication vaccineApplication = this.generateVaccineApplication();
        vaccineApplicationService.save(vaccineApplication);
        var vaApp = vaccineApplicationService.findById(vaccineApplication.getId());
        Assertions.assertEquals(vaccineApplication.getNote(), vaApp.get().getNote());
    }

    @Test
    public void checkFindByAll(){
        Assertions.assertNotNull(vaccineApplicationService.findAll(Pageable.unpaged()));
    }

    @Test
    @Transactional
    public void checkUpdate() {

        VaccineApplication vaccineApplication = this.generateVaccineApplication();
        vaccineApplicationService.save(vaccineApplication);
        vaccineApplication.setVaccine(vaccine2);
        this.vaccineApplicationService.update(vaccineApplication.getId(), vaccineApplication);
        Optional<VaccineApplication> getVaccine = vaccineApplicationService.findById(vaccineApplication.getId());
        Assertions.assertEquals(getVaccine.get().getVaccine().getName(), "micose vacitec");
    }

    @Test
    public void checkDisable(){
        VaccineApplication vaccineApplication = this.generateVaccineApplication();
        vaccineApplicationService.save(vaccineApplication);
        vaccineApplicationService.disable(vaccineApplication.getId(), vaccineApplication);
        Optional<VaccineApplication> getVaccineApp = vaccineApplicationService.findById(vaccineApplication.getId());
        Assertions.assertTrue(getVaccineApp.get().isInactive());
    }
}