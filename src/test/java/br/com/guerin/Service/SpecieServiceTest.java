package br.com.guerin.Service;

import br.com.guerin.Entity.Specie;
import br.com.guerin.Repository.Specie.SpecieRepository;
import br.com.guerin.Service.IService.ISpecieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

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
        for(Specie specie1 : specieService.listAll(Pageable.unpaged())){
            if(specie.getName().contains("Test1")){
                specieList.add(specie);
            }
        }
        Assertions.assertEquals("Test1", specieList.get(0).getName());
    }
    @Test
    public  void update(){
        Specie specie = specieRepository.findById(1L).orElse(new Specie("Tst1"));
        Assertions.assertNotNull(specie);
        specie.setName("Ty4");
        specieService.save(specie);
        Specie specie1 = specieService.findById(1L);
        Assertions.assertEquals("Ty4", specie1.getName());
    }

    @Test
    public void inactivateSpecie(){
        Specie specie =specieRepository.findById(1L).orElse(new Specie("Jk4"));
        Assertions.assertFalse(specie.isInactive());
        specie.setInactive(true);
        specieService.save(specie);
        Specie specie1 = specieService.findById(1L);
        Assertions.assertTrue(specie1.isInactive());
    }
    @Test
    public void listAllSpecie(){
        Assertions.assertNotNull(specieService.listAll(Pageable.unpaged()));
    }
    @Test
    public void findById(){
        Specie specie = specieService.findById(1L);
        Assertions.assertNotNull(specie);
    }
}
