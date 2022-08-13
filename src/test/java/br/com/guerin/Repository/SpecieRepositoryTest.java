package br.com.guerin.Repository;


import br.com.guerin.Entity.Specie;
import br.com.guerin.Repository.Specie.SpecieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SpecieRepositoryTest {
    @Autowired
    SpecieRepository specieRepository;


    @Test
    public void insertSpecie(){
        Specie specie = new Specie("Fidelis");
        specieRepository.save(specie);
        Integer count = specieRepository.findAll().size();
        Assertions.assertEquals(1, count);
    }
    @Test
    public void findByName(){
        Specie specie1 = new Specie("Gabriel");
        specieRepository.save(specie1);
        Specie specie = specieRepository.findByName("Gabriel");
        Assertions.assertNotNull(specie);

    }
    @Test
    public void findById(){
        Specie specie = new Specie("Fidelis");
        specieRepository.save(specie);
        Optional<Specie> specie1 = specieRepository.findById(1L);
        Assertions.assertEquals("Fidelis", specie1.get().getName());
    }

    @Test
    public void updateSpecie(){
        Specie specie = new Specie("Fidelis");
        specieRepository.save(specie);
        Specie specie1 = specieRepository.findByName("Fidelis");
        specie1.setInactive(true);
        specieRepository.save(specie1);
        Integer count = specieRepository.findAll().size();
        Assertions.assertEquals(1, count);
        Assertions.assertTrue(specie1.isInactive());
    }
    @Test
    public void listAllSpecie(){
        for(int i = 0; i < 5; i++){
            Specie specie = new Specie();
            specie.setName("A" + i);
            specieRepository.save(specie);
        }
        Integer count = specieRepository.findAll().size();
        Assertions.assertEquals(5, count);
    }

}
