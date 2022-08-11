package br.com.guerin.Entity;

import br.com.guerin.Repository.Specie.SpecieRepository;
import br.com.guerin.Service.SpecieService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpecieTest {
    @Autowired
    SpecieService specieService;

    @Autowired
    SpecieRepository specieRepository;

    @Test
    public void insertSpecie(){
        Specie specie = new Specie();
        specie.setName("Gabriel");
        specieService.save(specie);
        Integer count = specieRepository.findAll().size();
        Assertions.assertEquals(1, count);
    }
    @Test
    public  void update(){
        Specie specie = new Specie();
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
        Assertions.assertTrue(specieRepository.findAll() != null);
    }



   







}
