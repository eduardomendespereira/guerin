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

    @Test
    public void createSpecie(){
        Specie specie = new Specie();
        specie.setName("Fidelao");
        specie.setInactive(false);
        Assertions.assertEquals("Fidelao", specie.getName());
        Assertions.assertFalse( specie.isInactive());
    }

    @Test
    public void inactiveSpecie(){
        Specie specie = new Specie();
        specie.setName("Fidelao");
        specie.setInactive(false);
        Assertions.assertEquals("Fidelao", specie.getName());
        Assertions.assertFalse( specie.isInactive());
        specie.setInactive(true);
        Assertions.assertTrue(specie.isInactive());
    }
    @Test
    public void compareSpecies(){
        Specie specie = new Specie("Fidelao");
        specie.setInactive(false);
        Specie specie1 = new Specie("Fidelin");
        specie1.setInactive(false);
        Assertions.assertFalse(specie.equals(specie1));
        specie1.setName("Fidelao");
        Assertions.assertTrue(specie.equals(specie1));
    }









}
