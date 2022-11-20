package br.com.guerin.Service;

import br.com.guerin.Entity.Specie;
import br.com.guerin.Repository.Specie.SpecieRepository;
import br.com.guerin.Service.IService.ISpecieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SpecieServiceTest {
    @Autowired
    ISpecieService specieService;

    @Autowired
    SpecieRepository specieRepository;
    @Test
    public void insertSpecie(){
        Specie specie = new Specie("Test1");
        specieService.save(specie);
        List<Specie> specieList = new ArrayList<Specie>();
        for(Specie specie1 : specieService.listAll()){
            if(specie.getName().contains("Test1")){
                specieList.add(specie);
            }
        }
        Assertions.assertEquals("Test1", specieList.get(0).getName());
    }
    @Test
    public  void update(){
        if(specieService.listAll().isEmpty()){
            specieService.save(new Specie("Fidelis"));
        }
        Specie specie = specieService.findById(1L).get();
        Assertions.assertNotNull(specie);
        String temp = specie.getName();
        specie.setName("Dragon");
        specieService.update(1L, specie);
        Specie specie1 = specieService.findById(1L).get();
        Assertions.assertNotEquals(temp, specie1.getName());
    }

    @Test
    public void inactivateSpecie(){
        if(specieService.listAll().isEmpty()){
            specieService.save(new Specie("Jk4"));
        }
        Specie specie =specieService.findById(1L).get();
        Assertions.assertFalse(specie.isInactive());
        specieService.disable(1L, specie);
        Specie specie1 = specieService.findById(1L).get();
        Assertions.assertTrue(specie1.isInactive());
    }
    @Test
    public void listAllSpecie(){
        if(specieService.listAll().isEmpty()){
            specieService.save(new Specie("Fidelao"));
        }
        Assertions.assertNotNull(specieService.listAll());
    }
    @Test
    public void findSpecieById(){
        if(specieService.listAll().isEmpty()){
            specieService.save(new Specie("Fidelao"));
        }
        Specie specie = specieService.findById(1L).get();
        Assertions.assertNotNull(specie);
    }

    @Test
    public void findSpecieByName(){
        Specie specie = specieService.save(new Specie("Bobao"));
        Specie specie1 = specieService.findByName(specie.getName()).get();
        Assertions.assertEquals("Bobao", specie1.getName());

    }
}
