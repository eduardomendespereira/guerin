package br.com.guerin.Service;

import br.com.guerin.Entity.Specie;
import br.com.guerin.Repository.Specie.SpecieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
@SpringBootTest
public class SpecieServiceTests {
    @Autowired
    SpecieService specieService;


    @Test
    public void insertSpecie(){
        Specie specie = new Specie();
        specie.setName("Fidelao");
        specieService.save(specie);
        Integer count = specieService.listAll(Pageable.unpaged()).getSize();
        Assertions.assertEquals(1, count);
    }
    @Test
    public  void update(){
        Specie specie = new Specie();
        specie.setName("Fidelao");
        specieService.save(specie);
        specie = specieService.findById(1L);
        specie.setName("Fidelis");
        specieService.update(specie.getId(), specie);
        Assertions.assertEquals("Fidelis", specie.getName());

    }

    @Test
    public void inactiveSpecie(){
        Specie specie = new Specie();
        specie.setInactive(true);
        specie.setId(1L);
        specieService.inactivate(1L, specie);
        Specie specie1 = specieService.findById(1L);
        Assertions.assertEquals(true, specie1.isInactive());
    }
    @Test
    public void listSpecie(){
        Specie specie = new Specie("Fidelis");
        specieService.save(specie);
        Assertions.assertTrue(specieService.listAll(Pageable.unpaged()) != null);
    }
}
