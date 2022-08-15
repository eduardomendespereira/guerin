package br.com.guerin.Repository;


import br.com.guerin.Entity.EventType;
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
        Specie specie = new Specie("Test");
        specieRepository.save(specie);
        List<Specie> specieList = new ArrayList<Specie>();
        for(Specie specie1 : specieRepository.findAll()){
            if(specie.getName().contains("Test")){
                specieList.add(specie);
            }
        }
        Assertions.assertEquals("Test", specieList.get(0).getName());
    }
    @Test
    public void updateSpecie(){
        Specie specie = specieRepository.findById(1L).orElse(new Specie("Tst"));
        Assertions.assertNotNull(specie);
        specie.setName("Ty");
        specieRepository.save(specie);
        Specie specie1 = specieRepository.findById(1L).orElse(new Specie());
        Assertions.assertEquals("Ty", specie1.getName());
    }
    @Test
    public void inactivateSpecie(){
        Specie specie =specieRepository.findById(1L).orElse(new Specie("Jk"));
        Assertions.assertFalse(specie.isInactive());
        specie.setInactive(true);
        specieRepository.save(specie);
        Specie specie1 = specieRepository.findById(1L).orElse(new Specie());
        Assertions.assertTrue(specie1.isInactive());
    }

    @Test
    public void findById(){
        Specie specie = specieRepository.findById(1L).orElse(new Specie("Jo"));
        Assertions.assertNotNull(specie);
    }


    @Test
    public void listAllSpecie() {
        Assertions.assertNotNull(specieRepository.findAll());
    }
}
